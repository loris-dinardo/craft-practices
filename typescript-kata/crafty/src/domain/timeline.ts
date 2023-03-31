import {Message} from "./message";

export interface TimelineMessage {
    user: string;
    message: string;
    publicationTime: string;
}

export class Timeline {
    private readonly ONE_MINUTE = 60000;

    constructor(
        private readonly messages: Message[],
        private readonly now: Date) {
    }

    get data(): TimelineMessage[] {
        this.messages
            .sort((msgA, msgB) =>
                msgB.publishedAt.getTime() - msgA.publishedAt.getTime());

        return this.messages.map(message => ({
            user: message.authorId,
            message: message.text.value,
            publicationTime: this.publicationTime(message.publishedAt)
        }));
    }

    private publicationTime(publishedAt: Date): string {
        const diff = this.now.getTime() - publishedAt.getTime();
        const diffInMinutes = diff / this.ONE_MINUTE;
        if (diffInMinutes < 1) {
            return "less than a minute ago";
        }
        if (diffInMinutes < 2) {
            return "1 minute ago";
        }
        return Math.floor(diffInMinutes) + " minutes ago";
    }
}