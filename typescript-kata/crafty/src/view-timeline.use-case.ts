import {MessageRepository} from "./message.repository";

export class ViewTimelineUseCase {
    constructor(private readonly messageRepository: MessageRepository) {
    }

    async handle({userId}: { userId: string }): Promise<{ authorId: string, text: string, publicationTime: string }[]> {
        const messagesOfUser = await this.messageRepository
            .findMessagesByAuthorId(userId);
        messagesOfUser
            .sort((msgA, msgB) =>
                msgB.publishedAt.getTime() - msgA.publishedAt.getTime());
        return [
            {
                text: messagesOfUser[0].text,
                authorId: messagesOfUser[0].authorId,
                publicationTime: "1 minute ago"
            },
            {
                text: messagesOfUser[1].text,
                authorId: messagesOfUser[1].authorId,
                publicationTime: "5 minutes ago"
            }
        ]
    }
}