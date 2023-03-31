import {createMessagingFixture, MessagingFixture} from "./messaging.fixture";
import {messageBuilder} from "./message.builder";
import {EmptyMessageError, MessageTooLongError} from "../domain/message";

describe("Feature: Edit Message", () => {
    let fixture: MessagingFixture;
    beforeEach(() => {
        fixture = createMessagingFixture();
    });

    describe("Rule: The edited text should be not exceed 280 characters", () => {
        const aliceMessageBuilder = messageBuilder()
            .withId("message-id-1")
            .writtenBy("Alice")
            .withText("Hello wrdl!");

        test("Alice can edit her message to with a text up to 280 characters", async () => {
            fixture.givenTheFollowingMessagesWerePublished([
                aliceMessageBuilder.build()
            ]);

            await fixture.whenUserEditsMessage({
                messageId: "message-id-1",
                text: "Hello world!"
            });

            await fixture.thenMessageShouldBe(
                aliceMessageBuilder.withText("Hello world!").build()
            );
        });

        test("Alice cannot edit her message to with a text exceeding 280 characters", async () => {
            const textWith281Characters = "a".repeat(281);
            fixture.givenTheFollowingMessagesWerePublished([
                aliceMessageBuilder
                    .withText("Hello world!")
                    .build()
            ]);

            await fixture.whenUserEditsMessage({
                messageId: "message-id-1",
                text: textWith281Characters
            });

            await fixture.thenMessageShouldBe(
                aliceMessageBuilder.withText("Hello world!").build()
            );
            fixture.thenErrorShouldBe(MessageTooLongError);
        });

        test("Alice cannot edit her message to with an empty text", async () => {
            fixture.givenTheFollowingMessagesWerePublished([
                aliceMessageBuilder
                    .withText("Hello world!")
                    .build()
            ]);

            await fixture.whenUserEditsMessage({
                messageId: "message-id-1",
                text: ""
            });

            await fixture.thenMessageShouldBe(
                aliceMessageBuilder.withText("Hello world!").build()
            );
            fixture.thenErrorShouldBe(EmptyMessageError);
        });

        test("Alice cannot edit her message to with a text containing only spaces", async () => {
            fixture.givenTheFollowingMessagesWerePublished([
                aliceMessageBuilder
                    .withText("Hello world!")
                    .build()
            ]);

            await fixture.whenUserEditsMessage({
                messageId: "message-id-1",
                text: "   "
            });

            await fixture.thenMessageShouldBe(
                aliceMessageBuilder.withText("Hello world!").build()
            );
            fixture.thenErrorShouldBe(EmptyMessageError);
        });
    });
});