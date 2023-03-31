import {MessageRepository} from "./message.repository";
import {DateProvider} from "./date-time-provider";
import {MessageText} from "./message";

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
        const messageText = MessageText.of(command.text);
        await this.messageRepository.save({
            id: command.id,
            text: messageText,
            authorId: command.authorId,
            publishedAt: this.dateProvider.getNow(),
        });
    }
}