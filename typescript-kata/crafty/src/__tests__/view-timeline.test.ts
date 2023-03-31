import {createMessagingFixture, MessagingFixture} from "./messaging.fixture";
import {messageBuilder} from "./message.builder";

describe("Feature : Viewing a personal timeline", () => {
    let fixture: MessagingFixture;

    beforeEach(() => {
        fixture = createMessagingFixture();
    });

    describe("Rule : Messages are shown in reverse chronological order", () => {
        test("Alice can view the 3 messages she published in her timeline", async () => {
            const aliceMessageBuilder = messageBuilder().writtenBy("Alice");
            const bobMessageBuilder = messageBuilder().writtenBy("Bob");

            fixture.givenTheFollowingMessagesWerePublished([
                aliceMessageBuilder.withId("id-message-1").withText("My first message")
                    .beingPublishedAt(new Date("2023-01-21T15:26:00.000Z")).build(),
                bobMessageBuilder.withId("id-message-2").withText("Hi! I'm bob")
                    .beingPublishedAt(new Date("2023-01-21T15:28:00.000Z")).build(),
                aliceMessageBuilder.withId("id-message-3").withText("My second message")
                    .beingPublishedAt(new Date("2023-01-21T15:30:00.000Z")).build(),
                aliceMessageBuilder.withId("id-message-4").withText("My last message")
                    .beingPublishedAt(new Date("2023-01-21T15:30:30.000Z")).build(),
            ]);
            fixture.givenNowIs(new Date("2023-01-21T15:31:00.000Z"));

            await fixture.whenUserViewsTimelineOf("Alice");

            fixture.thenTimelineShouldBe([
                {
                    message: "My last message",
                    user: "Alice",
                    publicationTime: "less than a minute ago"
                },
                {
                    message: "My second message",
                    user: "Alice",
                    publicationTime: "1 minute ago"
                },
                {
                    message: "My first message",
                    user: "Alice",
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