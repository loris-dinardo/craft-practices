import {InMemoryMessageRepository} from "../in-memory-message-repository";
import {ViewTimelineUseCase} from "../view-timeline.use-case";
import {Message} from "../message";

describe("Feature : Viewing a personal timeline", () => {
    let fixture: Fixture;

    beforeEach(() => {
        fixture = createFixture();
    });

    describe("Rule : Messages are shown in reverse chronological order", () => {
        test("Alice can view the 2 messages she published in her timeline", async () => {
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
            ]);
            fixture.givenNowIs(new Date("2023-01-21T15:31:00.000Z"));

            await fixture.whenUserViewsTimelineOf("Alice");

            fixture.thenTimelineShouldBe([
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
});

const createFixture = () => {
    let timeline: { authorId: string, text: string, publicationTime: string}[] = [];
    const messageRepository = new InMemoryMessageRepository();
    const viewTimelineUseCase = new ViewTimelineUseCase(messageRepository);
    return {
        givenTheFollowingMessagesWerePublished(messages: Message[]) {
            messageRepository.givenExistingMessages(messages);
        },
        givenNowIs(now: Date){
            // TODO
        },
        async whenUserViewsTimelineOf(userId: string){
            timeline = await viewTimelineUseCase.handle({userId});
        },
        thenTimelineShouldBe(expectedTimeline: { authorId: string, text: string, publicationTime: string}[]){
            expect(timeline).toEqual(expectedTimeline);
        }
    }
}

type Fixture = ReturnType<typeof createFixture>;