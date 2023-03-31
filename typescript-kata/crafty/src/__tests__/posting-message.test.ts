import {createMessagingFixture, MessagingFixture} from "./messaging.fixture";
import {messageBuilder} from "./message.builder";
import {EmptyMessageError, MessageTooLongError} from "../domain/message";

describe("Feature: Posting a message", () => {
    let fixture: MessagingFixture;

    beforeEach(() => {
        fixture = createMessagingFixture();
    });

    describe("Rule: A message can contain a maximum of 280 characters", () => {
        test("Alice can post a message on her timeline", async () => {
            fixture.givenNowIs(new Date("2023-01-19T19:00:00.000Z"));

            await fixture.whenUserPostsMessage({
                id: "id-message-1",
                text: "Hello World!",
                authorId: "Alice",
            });

            await fixture.thenMessageShouldBe(
                messageBuilder()
                    .withId("id-message-1")
                    .withText("Hello World!")
                    .writtenBy("Alice")
                    .beingPublishedAt(new Date("2023-01-19T19:00:00.000Z"))
                    .build());
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