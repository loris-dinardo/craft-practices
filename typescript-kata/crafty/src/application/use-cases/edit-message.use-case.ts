import {EditMessageCommand} from "./commands/edit-message.command";
import {MessageRepository} from "../gateways/message.repository";

export class EditMessageUseCase {
    constructor(private readonly messageRepository: MessageRepository) {
    }

    async handle(command: EditMessageCommand): Promise<void> {
        const message = await this.messageRepository
            .findMessageById(command.messageId);
        message.editText(command.text);
        await this.messageRepository.save(message);
    }
}