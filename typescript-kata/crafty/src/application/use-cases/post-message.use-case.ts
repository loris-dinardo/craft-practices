import {MessageRepository} from "../gateways/message.repository";
import {DateProvider} from "../gateways/date-time-provider";
import {Message} from "../../domain/message";

export type PostMessageCommand = {
    id: string;
    text: string;
    authorId: string;
}

export class PostMessageUseCase {
    constructor(private readonly messageRepository: MessageRepository,
                private readonly dateProvider: DateProvider) {
    }

    async handle(command: PostMessageCommand) {
        await this.messageRepository.save(Message.fromData({
            id: command.id,
            text: command.text,
            authorId: command.authorId,
            publishedAt: this.dateProvider.getNow(),
        }));
    }
}