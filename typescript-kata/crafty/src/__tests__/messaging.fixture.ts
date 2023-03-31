import {StubDateProvider} from "../infrastructure/stub-date-provider";
import {InMemoryMessageRepository} from "../infrastructure/in-memory-message-repository";
import {PostMessageCommand, PostMessageUseCase} from "../application/use-cases/post-message.use-case";
import {Message} from "../domain/message";
import {ViewTimelineUseCase} from "../application/use-cases/view-timeline.use-case";
import {EditMessageCommand} from "../application/use-cases/commands/edit-message.command";
import {EditMessageUseCase} from "../application/use-cases/edit-message.use-case";
import {TimelineMessage} from "../domain/timeline";

export const createMessagingFixture = () => {
    let timelineMessages: TimelineMessage[] = [];
    const dateProvider = new StubDateProvider();
    const messageRepository = new InMemoryMessageRepository();
    const postMessageCommandUseCase = new PostMessageUseCase(
        messageRepository,
        dateProvider
    );
    const editMessageUseCase = new EditMessageUseCase(messageRepository);
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
            } catch (err) {
                thrownError = err as Error;
            }
        },
        async whenUserViewsTimelineOf(userId: string) {
            timelineMessages = await viewTimelineUseCase.handle({userId});
        },
        async whenUserEditsMessage(editMessageCommand: EditMessageCommand) {
            try {
                await editMessageUseCase.handle(editMessageCommand);
            } catch (err) {
                thrownError = err as Error;
            }
        },
        async thenMessageShouldBe(expectedMessage: Message) {
            expect(expectedMessage).toEqual(await messageRepository.findMessageById(expectedMessage.id));
        },
        thenTimelineShouldBe(expectedTimeline: TimelineMessage[]) {
            expect(timelineMessages).toEqual(expectedTimeline);
        },
        thenErrorShouldBe(expectedErrorClass: new () => Error) {
            expect(thrownError).toBeInstanceOf(expectedErrorClass);
        },
        messageRepository,
    }
}

export type MessagingFixture = ReturnType<typeof createMessagingFixture>