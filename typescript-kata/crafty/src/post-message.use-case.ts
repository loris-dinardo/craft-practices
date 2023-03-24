import {MessageRepository} from "./message.repository";
import {DateProvider} from "./date-time-provider";

export type PostMessageCommand = {
    id: string;
    text: string;
    authorId: string;
}

export class MessageTooLongError extends Error {
}

export class EmptyMessageError extends Error {
}

export class PostMessageUseCase {
    constructor(private readonly messageRepository: MessageRepository,
                private readonly dateProvider: DateProvider) {
    }

    async handle(command: PostMessageCommand) {
        if (command.text.length > 280) {
            throw new MessageTooLongError();
        }
        if (command.text.trim().length === 0) {
            throw new EmptyMessageError();
        }
        await this.messageRepository.save({
            id: command.id,
            text: command.text,
            authorId: command.authorId,
            publishedAt: this.dateProvider.getNow(),
        });
    }
}