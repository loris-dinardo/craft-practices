import {
    EmptyMessageError,
    MessageTooLongError,
    PostMessageCommand,
    PostMessageUseCase
} from "../post-message.use-case";
import {InMemoryMessageRepository} from "../in-memory-message-repository";
import {StubDateProvider} from "../stub-date-provider";
import {Message} from "../message";

describe("Feature: Posting a message", () => {
    let fixture: Fixture;

    beforeEach(() => {
        fixture = createFixture();
    });

    describe("Rule: A message can contain a maximum of 280 characters", () => {
        test("Alice can post a message on her timeline", async () => {
            fixture.givenNowIs(new Date("2023-01-19T19:00:00.000Z"));

            await fixture.whenUserPostsMessage({
                id: "id-message-1",
                text: "Hello World!",
                authorId: "Alice",
            });

            fixture.thenPostedMessageShouldBe({
                id: "id-message-1",
                text: "Hello World!",
                authorId: "Alice",
                publishedAt: new Date("2023-01-19T19:00:00.000Z"),
            });
        });
        test("Alice cannot post a message with more than 280 characters", async () => {
            const textWith281Characters = "a".repeat(281);
            fixture.givenNowIs(new Date("2023-01-19T19:00:00.000Z"));

            await fixture.whenUserPostsMessage({
                id: "id-message-1",
                text: textWith281Characters,
                authorId: "Alice",
            });

            fixture.thenErrorShouldBe(MessageTooLongError);
        });
    });
    describe("Rule: A message cannot be empty", () => {
        test("Alice cannot post an empty message", async () => {
            fixture.givenNowIs(new Date("2023-01-19T19:00:00.000Z"));

            await fixture.whenUserPostsMessage({
                id: "id-message-1",
                text: "",
                authorId: "Alice",
            });

            fixture.thenErrorShouldBe(EmptyMessageError);
        });
        test("Alice cannot post a message with only spaces", async () => {
            fixture.givenNowIs(new Date("2023-01-19T19:00:00.000Z"));

            await fixture.whenUserPostsMessage({
                id: "id-message-1",
                text: "   ",
                authorId: "Alice",
            });

            fixture.thenErrorShouldBe(EmptyMessageError);
        });
    });
});

const createFixture = () => {
    const dateProvider = new StubDateProvider();
    const messageRepository = new InMemoryMessageRepository();
    const postMessageCommandUseCase = new PostMessageUseCase(
        messageRepository,
        dateProvider
    );
    let thrownError: Error;

    return {
        givenNowIs(now: Date) {
            dateProvider.now = now;
        },
        async whenUserPostsMessage(postMessageCommand: PostMessageCommand) {
            try {
                await postMessageCommandUseCase.handle(postMessageCommand);
            } catch (e: unknown) {
                thrownError = e as Error
            }
        },
        thenPostedMessageShouldBe(expectedMessage: Message) {
            expect(expectedMessage).toEqual(messageRepository.getMessageById(expectedMessage.id));
        },
        thenErrorShouldBe(expectedErrorClass: new () => Error) {
            expect(thrownError).toBeInstanceOf(expectedErrorClass);
        }
    }
}

type Fixture = ReturnType<typeof createFixture>;