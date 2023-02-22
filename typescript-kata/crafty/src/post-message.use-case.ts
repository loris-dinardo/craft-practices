export type Message = {
    id: string;
    text: string;
    authorId: string;
    publishedAt: Date;
}

export type PostMessageCommand = {
    id: string;
    text: string;
    authorId: string;
}

export interface MessageRepository {
    save(msg: Message): void;
}

export interface DateProvider {
    getNow(): Date;
}

export class PostMessageCommandUseCase {
    constructor(private readonly messageRepository: MessageRepository,
                private readonly dateProvider: DateProvider) {
    }
    handle(command: PostMessageCommand) {
        this.messageRepository.save({
            id: command.id,
            text: command.text,
            authorId: command.authorId,
            publishedAt: this.dateProvider.getNow(),
        });
    }
}