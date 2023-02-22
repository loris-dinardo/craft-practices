import {
    DateProvider,
    Message,
    MessageRepository,
    PostMessageCommand,
    PostMessageCommandUseCase
} from "../post-message.use-case";

describe("Feature: Posting a message", () => {
    describe("Rule: A message can contain a maximum of 280 characters", () => {
        test("Alice can post a message on her timeline", async () => {
            givenNowIs(new Date("2023-01-19T19:00:00.000Z"));

            whenUserPostsMessage({
                id: "id-message-1",
                text: "Hello World!",
                authorId: "Alice",
            });

            thenPostedMessageShouldBe({
                id: "id-message-1",
                text: "Hello World!",
                authorId: "Alice",
                publishedAt: new Date("2023-01-19T19:00:00.000Z"),
            });
        });
    });
});

class InMemoryMessageRepository implements MessageRepository {
    save(msg: Message): void {
        message = msg;
    }
}

class StubDateProvider implements DateProvider {
    now: Date = new Date()

    getNow(): Date {
        return this.now;
    }
}

const messageRepository = new InMemoryMessageRepository();
const dateProvider = new StubDateProvider();

const postMessageCommandUseCase = new PostMessageCommandUseCase(
    messageRepository,
    dateProvider
);

let message: Message;

const givenNowIs = (_now: Date) => {
    dateProvider.now = _now;
};
const whenUserPostsMessage = (postMessageCommand: PostMessageCommand) => {
    postMessageCommandUseCase.handle(postMessageCommand);
};
const thenPostedMessageShouldBe = (expectedMessage: Message) => {
    expect(expectedMessage).toEqual(message);
};