import * as fs from "fs";
import * as path from "path";
import {MessageRepository} from "../application/gateways/message.repository";
import {Message} from "../domain/message";

export class FileSystemMessageRepository implements MessageRepository {
    constructor(
        private readonly messagePath = path.join(__dirname, "messages.json")
    ) {
        if (!fs.existsSync(this.messagePath)) {
            fs.writeFileSync(this.messagePath, "[]");
        }
    }

    async save(msg: Message): Promise<void> {
        const messages = await this.getMessages();
        const index = messages.findIndex(m => m.id === msg.id);
        if (index === -1) {
            messages.push(msg);
        } else {
            messages[index] = msg;
        }

        return fs.promises.writeFile(
            this.messagePath,
            JSON.stringify(messages.map(msg => msg.data))
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

        return messages.map(msg => Message.fromData({
            id: msg.id,
            authorId: msg.authorId,
            text: msg.text,
            publishedAt: new Date(msg.publishedAt)
        }));
    }

    async findMessageById(messageId: string): Promise<Message> {
        const messages = await this.getMessages();
        return messages.filter(msg => msg.id === messageId)[0];
    }
}