export class MessageTooLongError extends Error {
}

export class EmptyMessageError extends Error {
}

export class Message {
    constructor(
        private readonly _id: string,
        private _text: MessageText,
        private readonly _authorId: string,
        private readonly _publishedAt: Date
    ) {
    }

    get id(): string {
        return this._id;
    }

    get authorId(): string {
        return this._authorId;
    }

    get text(): MessageText {
        return this._text;
    }

    get publishedAt(): Date {
        return this._publishedAt;
    }

    get data() {
        return {
            id: this._id,
            authorId: this._authorId,
            text: this._text.value,
            publishedAt: this._publishedAt
        }
    }

    static fromData(data: Message["data"]): Message {
        return new Message(
            data.id,
            MessageText.of(data.text),
            data.authorId,
            new Date(data.publishedAt)
        );
    }

    editText(text: string) {
        this._text = MessageText.of(text);
    }
}

export class MessageText {
    private constructor(readonly value: string) {
    }

    static of(text: string): MessageText {
        if (text.length > 280) {
            throw new MessageTooLongError();
        }
        if (text.trim().length === 0) {
            throw new EmptyMessageError();
        }
        return new MessageText(text);
    }
}