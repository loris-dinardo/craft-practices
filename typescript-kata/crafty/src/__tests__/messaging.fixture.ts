import {StubDateProvider} from "../stub-date-provider";
import {InMemoryMessageRepository} from "../in-memory-message-repository";
import {PostMessageCommand, PostMessageUseCase} from "../post-message.use-case";
import {Message} from "../message";
import {ViewTimelineUseCase} from "../view-timeline.use-case";

export const createMessagingFixture = () => {
    let timeline: { authorId: string, text: string, publicationTime: string }[] = [];
    const dateProvider = new StubDateProvider();
    const messageRepository = new InMemoryMessageRepository();
    const postMessageCommandUseCase = new PostMessageUseCase(
        messageRepository,
        dateProvider
    );
    const viewTimelineUseCase = new ViewTimelineUseCase(messageRepository, dateProvider);
    let thrownError: Error;

    return {
        givenTheFollowingMessagesWerePublished(messages: Message[]) {
            messageRepository.givenExistingMessages(messages);
        },
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
        async whenUserViewsTimelineOf(userId: string) {
            timeline = await viewTimelineUseCase.handle({userId});
        },
        async whenUserEditsMessage(editMessageCommand: { messageId: string; text: string }) {

        },
        thenMessageShouldBe(expectedMessage: Message) {
            expect(expectedMessage).toEqual(messageRepository.getMessageById(expectedMessage.id));
        },
        thenTimelineShouldBe(expectedTimeline: { authorId: string, text: string, publicationTime: string }[]) {
            expect(timeline).toEqual(expectedTimeline);
        },
        thenErrorShouldBe(expectedErrorClass: new () => Error) {
            expect(thrownError).toBeInstanceOf(expectedErrorClass);
        }
    }
}

export type MessagingFixture = ReturnType<typeof createMessagingFixture>