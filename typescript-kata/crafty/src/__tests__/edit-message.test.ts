import {createMessagingFixture, MessagingFixture} from "./messaging.fixture";
import {messageBuilder} from "./message.builder";

describe("Feature: Edit Message", () => {
    let fixture: MessagingFixture;
    beforeEach(() => {
        fixture = createMessagingFixture();
    });

    describe("Rule: The edited text should be not exceed 280 characters", () => {
        test.skip("Alice can edit her message to with a text up to 280 characters", async () => {
            const aliceMessageBuilder = messageBuilder()
                .withId("message-id-1")
                .writtenBy("Alice")
                .withText("Hello wrdl!");

            fixture.givenTheFollowingMessagesWerePublished([
                aliceMessageBuilder.build()
            ]);

            await fixture.whenUserEditsMessage({
                messageId: "message-id-1",
                text: "Hello world!"
            });

            fixture.thenMessageShouldBe(
                aliceMessageBuilder.withText("Hello world!").build()
            );
        });
    });
});