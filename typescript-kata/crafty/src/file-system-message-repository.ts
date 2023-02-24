import {Message, MessageRepository} from "./post-message.use-case";
import * as fs from "fs";
import * as path from "path";

export class FileSystemMessageRepository implements MessageRepository {
    public message?: Message = undefined;

    async save(msg: Message): Promise<void> {
        return fs.promises.writeFile(
            path.join(__dirname, "message.json"),
            JSON.stringify(msg)
        );
    }
}