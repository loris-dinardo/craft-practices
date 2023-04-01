import {MessageRepository} from "../gateways/message.repository";
import {DateProvider} from "../gateways/date-time-provider";
import {EmptyMessageError, Message, MessageTooLongError} from "../../domain/message";
import {Err, Ok, Result} from "./result";

export type PostMessageCommand = {
    id: string;
    text: string;
    authorId: string;
}

export class PostMessageUseCase {
    constructor(private readonly messageRepository: MessageRepository,
                private readonly dateProvider: DateProvider) {
    }

    async handle(command: PostMessageCommand): Promise<Result<void, EmptyMessageError | MessageTooLongError>> {
        let message: Message;
        try {
            message = Message.fromData({
                id: command.id,
                text: command.text,
                authorId: command.authorId,
                publishedAt: this.dateProvider.getNow(),
            })
        } catch (e) {
            return Err.of(e as Error);
        }
        await this.messageRepository.save(message);
        return Ok.of(undefined)
    }
}