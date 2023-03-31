import {EditMessageCommand} from "./edit-message.command";
import {MessageRepository} from "./message.repository";
import {MessageText} from "./message";

export class EditMessageUseCase {
    constructor(private readonly messageRepository: MessageRepository) {
    }

    async handle(command: EditMessageCommand): Promise<void> {
        const messageText = MessageText.of(command.text);
        const message = await this.messageRepository
            .findMessageById(command.messageId);
        message.text = messageText;
        await this.messageRepository.save(message);
    }
}