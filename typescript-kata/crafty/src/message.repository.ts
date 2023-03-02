import {Message} from "./message";

export interface MessageRepository {
    save(msg: Message): Promise<void>;

    findMessagesByAuthorId(userId: string): Promise<Message[]>;
}