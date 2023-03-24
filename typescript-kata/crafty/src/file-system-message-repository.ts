import * as fs from "fs";
import * as path from "path";
import {MessageRepository} from "./message.repository";
import {Message} from "./message";

export class FileSystemMessageRepository implements MessageRepository {
    public message?: Message = undefined;

    async save(msg: Message): Promise<void> {
        return fs.promises.writeFile(
            path.join(__dirname, "message.json"),
            JSON.stringify(msg)
        );
    }

    findMessagesByAuthorId(userId: string): Promise<Message[]> {
        return Promise.resolve([]);
    }
}