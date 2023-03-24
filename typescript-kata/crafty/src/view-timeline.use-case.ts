import {MessageRepository} from "./message.repository";
import {DateProvider} from "./date-time-provider";

export class ViewTimelineUseCase {
    private readonly ONE_MINUTE = 60000;
    constructor(
        private readonly messageRepository: MessageRepository,
        private readonly dateProvider: DateProvider
    ) {
    }

    async handle({userId}: { userId: string }): Promise<{ authorId: string, text: string, publicationTime: string }[]> {
        const messagesOfUser = await this.messageRepository
            .findMessagesByAuthorId(userId);
        messagesOfUser
            .sort((msgA, msgB) =>
                msgB.publishedAt.getTime() - msgA.publishedAt.getTime());

        return messagesOfUser.map(message => ({
            authorId: message.authorId,
            text: message.text,
            publicationTime: this.publicationTime(message.publishedAt)
        }));
    }

    private publicationTime(publishedAt: Date): string {
        const now = this.dateProvider.getNow();
        const diff = now.getTime() - publishedAt.getTime();
        const diffInMinutes = diff / this.ONE_MINUTE;
        if (diffInMinutes < 1) {
            return "less than a minute ago";
        }
        if (diffInMinutes < 2) {
            return "1 minute ago";
        }
        return Math.floor(diffInMinutes) + " minutes ago";
    }
}