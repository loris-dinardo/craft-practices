import {InMemoryMessageRepository} from "../in-memory-message-repository";
import {ViewTimelineUseCase} from "../view-timeline.use-case";
import {Message} from "../message";
import {StubDateProvider} from "../stub-date-provider";

describe("Feature : Viewing a personal timeline", () => {
    let fixture: Fixture;

    beforeEach(() => {
        fixture = createFixture();
    });

    describe("Rule : Messages are shown in reverse chronological order", () => {
        test("Alice can view the 3 messages she published in her timeline", async () => {
            fixture.givenTheFollowingMessagesWerePublished([
                {
                    id: "id-message-1",
                    text: "My first message",
                    authorId: "Alice",
                    publishedAt: new Date("2023-01-21T15:26:00.000Z")
                },
                {
                    id: "id-message-2",
                    text: "Hi! I'm bob",
                    authorId: "Bob",
                    publishedAt: new Date("2023-01-21T15:28:00.000Z")
                },
                {
                    id: "id-message-3",
                    text: "My second message",
                    authorId: "Alice",
                    publishedAt: new Date("2023-01-21T15:30:00.000Z")
                },
                {
                    id: "id-message-4",
                    text: "My last message",
                    authorId: "Alice",
                    publishedAt: new Date("2023-01-21T15:30:30.000Z")
                },
            ]);
            fixture.givenNowIs(new Date("2023-01-21T15:31:00.000Z"));

            await fixture.whenUserViewsTimelineOf("Alice");

            fixture.thenTimelineShouldBe([
                {
                    text: "My last message",
                    authorId: "Alice",
                    publicationTime: "less than a minute ago"
                },
                {
                    text: "My second message",
                    authorId: "Alice",
                    publicationTime: "1 minute ago"
                },
                {
                    text: "My first message",
                    authorId: "Alice",
                    publicationTime: "5 minutes ago"
                }
            ]);
        });
    });

    /**
     * Should be removed after the feature works but kept for documentation purpose (publicationsTime method moved to use case)
     */
    const publicationTime = (now: Date, publishedAt: Date) => {
        const diff = now.getTime() - publishedAt.getTime();
        const diffInMinutes = diff / 60000;
        if (diffInMinutes < 1) {
            return "less than a minute ago";
        }
        if (diffInMinutes < 2) {
            return "1 minute ago";
        }
        return Math.floor(diffInMinutes) + " minutes ago";
    }
    describe("Shift gear down concept : Publication time", () => {
        test("It should return 'less than a minute ago' when the message was published less than a minute ago", async () => {
            const now = new Date("2023-01-21T15:31:00.000Z");
            const publishedAt = new Date("2023-01-21T15:30:30.000Z");

            const text = publicationTime(now, publishedAt);

            expect(text).toEqual("less than a minute ago");
        });
        test("It should return '1 minute ago' when the message was published exactly 1 minute ago", async () => {
            const now = new Date("2023-01-21T15:31:00.000Z");
            const publishedAt = new Date("2023-01-21T15:30:00.000Z");

            const text = publicationTime(now, publishedAt);

            expect(text).toEqual("1 minute ago");
        });
        test("It should return '1 minute ago' when the message was published under 2 minute ago", async () => {
            const now = new Date("2023-01-21T15:31:00.000Z");
            const publishedAt = new Date("2023-01-21T15:29:01.000Z");

            const text = publicationTime(now, publishedAt);

            expect(text).toEqual("1 minute ago");
        });
        test("It should return '2 minutes ago' when the message was published between 2 minutes and 2:59 minutes ago", async () => {
            const now = new Date("2023-01-21T15:31:00.000Z");
            const publishedAt = new Date("2023-01-21T15:28:30.000Z");

            const text = publicationTime(now, publishedAt);

            expect(text).toEqual("2 minutes ago");
        });
        test("It should return 'X minutes ago' when the message was published between X minutes and X:59 minutes ago", async () => {
            const now = new Date("2023-01-21T15:31:00.000Z");
            const publishedAt = new Date("2023-01-21T15:25:30.000Z");

            const text = publicationTime(now, publishedAt);

            expect(text).toEqual("5 minutes ago");
        });
    });
});

const createFixture = () => {
    let timeline: { authorId: string, text: string, publicationTime: string }[] = [];
    const messageRepository = new InMemoryMessageRepository();
    const dateProvider = new StubDateProvider();
    const viewTimelineUseCase = new ViewTimelineUseCase(messageRepository, dateProvider);
    return {
        givenTheFollowingMessagesWerePublished(messages: Message[]) {
            messageRepository.givenExistingMessages(messages);
        },
        givenNowIs(now: Date) {
            dateProvider.now = now;
        },
        async whenUserViewsTimelineOf(userId: string) {
            timeline = await viewTimelineUseCase.handle({userId});
        },
        thenTimelineShouldBe(expectedTimeline: { authorId: string, text: string, publicationTime: string }[]) {
            expect(timeline).toEqual(expectedTimeline);
        }
    }
}

type Fixture = ReturnType<typeof createFixture>;