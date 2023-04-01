import {Message} from "./message";

export interface TimelineMessage {
    user: string;
    message: string;
    publicationTime: string;
}

export class Timeline {

    constructor(
        private readonly messages: Message[]
    ) {
    }

    get data() {
        this.messages
            .sort((msgA, msgB) =>
                msgB.publishedAt.getTime() - msgA.publishedAt.getTime());

        return this.messages.map(message => message.data);
    }
}