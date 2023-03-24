import {Message} from "../message";

interface MessageBuilderProps {
    id?: string;
    authorId?: string;
    text?: string;
    publishedAt?: Date;
}

export const messageBuilder = ({
                                   id = 'message-id',
                                   authorId = 'author-id',
                                   text = 'message-text',
                                   publishedAt = new Date('2020-01-01T00:00:00Z')
                               }: MessageBuilderProps = {}) => {
    const props = {id, authorId, text, publishedAt};

    return {
        withId(_id: string) {
            return messageBuilder({
                ...props,
                id: _id
            });
        },
        writtenBy(_authorId: string) {
            return messageBuilder({
                ...props,
                authorId: _authorId
            });
        },
        withText(_text: string) {
            return messageBuilder({
                ...props,
                text: _text
            });
        },
        beingPublishedAt(_publishedAt: Date) {
            return messageBuilder({
                ...props,
                publishedAt: _publishedAt
            });
        },
        build(): Message {
            return {
                id: props.id,
                authorId: props.authorId,
                text: props.text,
                publishedAt: props.publishedAt
            }
        }
    }
}