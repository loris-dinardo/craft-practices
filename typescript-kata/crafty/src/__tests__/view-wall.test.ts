import {createFollowingFixture, FollowingFixture} from "./following.fixture";
import {createMessagingFixture, MessagingFixture} from "./messaging.fixture";
import {messageBuilder} from "./message.builder";
import {StubDateProvider} from "../infrastructure/stub-date-provider";
import {ViewWallUseCase} from "../application/use-cases/view-wall.use-case";
import {MessageRepository} from "../application/gateways/message.repository";
import {FolloweesRepository} from "../application/gateways/followees.repository";
import {DefaultTimelinePresenter} from "../apps/default-timeline.presenter";
import {TimelinePresenter} from "../application/use-cases/presenters/timeline.presenter";
import {Timeline, TimelineMessage} from "../domain/timeline";

describe("Feature: Viewing user wall", () => {
    let fixture: Fixture;
    let followingFixture: FollowingFixture;
    let messagingFixture: MessagingFixture;

    beforeEach(() => {
        followingFixture = createFollowingFixture();
        messagingFixture = createMessagingFixture();
        fixture = createFixture({
            messageRepository: messagingFixture.messageRepository,
            followeesRepository: followingFixture.followeesRepository,
        });
    });

    describe("Rule: All the messages from Charlie and his followees should appear in reverse " +
        "chronological order", () => {
        test("Charlie has subscribed to Alice's timeline," +
            "and thus can view an aggregated list of all subscriptions", async () => {
            fixture.givenNowIs(new Date("2023-01-21T15:15:30.000Z"));
            messagingFixture.givenTheFollowingMessagesWerePublished([
                messageBuilder()
                    .withId("1")
                    .writtenBy("Alice").withText("I love the weather today")
                    .beingPublishedAt(new Date("2023-01-21T15:00:30.000Z")).build(),
                messageBuilder()
                    .withId("2")
                    .writtenBy("Bob").withText("Damn! We lost!")
                    .beingPublishedAt(new Date("2023-01-21T15:01:00.000Z")).build(),
                messageBuilder()
                    .withId("3")
                    .writtenBy("Charlie").withText("I'm in New York today! Anyone wants to have a coffee?")
                    .beingPublishedAt(new Date("2023-01-21T15:15:00.000Z")).build(),
            ])

            followingFixture.givenUserAlreadyFollows({
                user: "Charlie",
                followees: ["Alice"]
            });

            await fixture.whenUserViewsWallOf("Charlie");

            fixture.thenShouldSee([
                {
                    user: "Charlie",
                    message: "I'm in New York today! Anyone wants to have a coffee?",
                    publicationTime: "less than a minute ago"
                },
                {
                    user: "Alice",
                    message: "I love the weather today",
                    publicationTime: "15 minutes ago"
                }
            ]);
        });
    });
});

const createFixture = (
    {
        messageRepository,
        followeesRepository,
    }: {
        messageRepository: MessageRepository,
        followeesRepository: FolloweesRepository,
    }) => {
    let wall: TimelineMessage[] = [];
    const dateProvider = new StubDateProvider();
    const viewWallUseCase = new ViewWallUseCase(
        messageRepository,
        followeesRepository
    );
    const defaultTimelinePresenter = new DefaultTimelinePresenter(dateProvider);
    const timelinePresenter: TimelinePresenter = {
        showTimeline(timeline: Timeline) {
            wall = defaultTimelinePresenter.showTimeline(timeline);
        }
    }
    return {
        givenNowIs(date: Date) {
            dateProvider.now = date;
        },
        async whenUserViewsWallOf(user: string) {
            await viewWallUseCase.handle({user}, timelinePresenter);
        },
        thenShouldSee(expectedWall: { user: string, message: string, publicationTime: string }[]) {
            expect(wall).toEqual(expectedWall);
        }
    }
}

type Fixture = ReturnType<typeof createFixture>