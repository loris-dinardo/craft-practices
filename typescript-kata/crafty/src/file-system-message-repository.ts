import * as fs from "fs";
import * as path from "path";
import {MessageRepository} from "./message.repository";
import {Message} from "./message";

export class FileSystemMessageRepository implements MessageRepository {
    private readonly messagePath = path.join(__dirname, "messages.json");

    async save(msg: Message): Promise<void> {
        const messages = await this.getMessages();
        messages.push(msg);
        return fs.promises.writeFile(
            this.messagePath,
            JSON.stringify(messages)
        );
    }

    async findMessagesByAuthorId(userId: string): Promise<Message[]> {
        const messages = await this.getMessages();
        return messages.filter(msg => msg.authorId === userId);
    }

    private async getMessages(): Promise<Message[]> {
        const data = await fs.promises.readFile(
            this.messagePath
        );
        const messages = JSON.parse(data.toString()) as {
            id: string,
            authorId: string,
            text: string,
            publishedAt: string
        }[];

        return messages.map(msg => ({
            id: msg.id,
            authorId: msg.authorId,
            text: msg.text,
            publishedAt: new Date(msg.publishedAt)
        }));
    }
}