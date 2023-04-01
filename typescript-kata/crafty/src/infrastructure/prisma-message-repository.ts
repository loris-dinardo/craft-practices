import {PrismaClient} from "@prisma/client";
import {MessageRepository} from "../application/gateways/message.repository";
import {Message} from "../domain/message";

export class PrismaMessageRepository implements MessageRepository {
    constructor(private readonly prisma: PrismaClient) {
    }

    async save(msg: Message): Promise<void> {
        const messageData = msg.data;
        await this.prisma.user.upsert({
            where: {name: messageData.authorId},
            update: {name: messageData.authorId},
            create: {name: messageData.authorId}
        });
        await this.prisma.message.upsert({
            where: {id: messageData.id},
            update: {
                id: messageData.id,
                authorId: messageData.authorId,
                text: messageData.text,
                publishedAt: msg.publishedAt
            },
            create: {
                id: messageData.id,
                authorId: msg.authorId,
                text: messageData.text,
                publishedAt: msg.publishedAt
            }
        });
    }

    async findMessagesByAuthorId(userId: string): Promise<Message[]> {
        const messageDataList = await this.prisma.message.findMany({
            where: {authorId: userId},
            // include: {author: true} => to include author data retrieved from user table as foreign key
        });

        return messageDataList.map(messageData => Message.fromData({
            id: messageData.id,
            authorId: messageData.authorId,
            text: messageData.text,
            publishedAt: messageData.publishedAt
        }))
    }

    async findMessageById(messageId: string): Promise<Message> {
        const messageData = await this.prisma.message.findFirstOrThrow({
            where: {id: messageId},
            // include: {author: true} => to include author data retrieved from user table as foreign key
        });

        return Message.fromData({
            id: messageData.id,
            authorId: messageData.authorId,
            text: messageData.text,
            publishedAt: messageData.publishedAt
        })
    }
}