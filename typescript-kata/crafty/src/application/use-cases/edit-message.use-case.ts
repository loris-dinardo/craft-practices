import {EditMessageCommand} from "./commands/edit-message.command";
import {MessageRepository} from "../gateways/message.repository";
import {EmptyMessageError, MessageTooLongError} from "../../domain/message";
import {Err, Ok, Result} from "./result";

export class EditMessageUseCase {
    constructor(private readonly messageRepository: MessageRepository) {
    }

    async handle(command: EditMessageCommand): Promise<Result<void, EmptyMessageError | MessageTooLongError>> {
        const message = await this.messageRepository
            .findMessageById(command.messageId);
        try {
            message.editText(command.text);
        } catch (e) {
            return Err.of(e as Error)
        }

        await this.messageRepository.save(message);

        return Ok.of(undefined);
    }
}