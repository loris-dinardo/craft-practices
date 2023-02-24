import {Message, MessageRepository} from "./post-message.use-case";

export class InMemoryMessageRepository implements MessageRepository {
    public message?: Message = undefined;

    async save(msg: Message): Promise<void> {
        this.message = msg;
        return Promise.resolve();
    }
}