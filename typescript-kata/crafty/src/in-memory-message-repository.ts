import {MessageRepository} from "./message.repository";
import {Message} from "./message";

export class InMemoryMessageRepository implements MessageRepository {
    public messages = new Map<string, Message>();

    async save(msg: Message): Promise<void> {
        this._save(msg);
        return Promise.resolve();
    }

    givenExistingMessages(messages: Message[]) {
        messages.forEach(this._save.bind(this)); // is equal to messages.forEach(msg => this._save(msg));
    }

    getMessageById(id: string): Message | undefined {
        return this.messages.get(id);
    }

    private _save(msg: Message): void {
        this.messages.set(msg.id, msg);
    }

    async findMessagesByAuthorId(userId: string): Promise<Message[]> {
        return Promise.resolve([
            ...this.messages.values()
        ].filter(msg => msg.authorId === userId)
            // To be sure that message modified outside of repository will not be modified in repository
            .map(msg => Message.fromData({...msg.data})))
    }

    findMessageById(messageId: string): Promise<Message> {
        return Promise.resolve(this.getMessageById(messageId)!);
    }
}