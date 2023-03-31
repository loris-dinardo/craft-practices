import {createFollowingFixture, FollowingFixture} from "./following.fixture";

describe("Feature: Follow User", () => {
    let fixture: FollowingFixture;

    beforeEach(() => {
        fixture = createFollowingFixture();
    });

    test("Alice can follow Bob", async () => {
        fixture.givenUserAlreadyFollows({
            user: "Alice",
            followees: ["Charlie"]
        });

        await fixture.whenUserFollows({
            user: "Alice",
            userToFollow: "Bob"
        });

        await fixture.thenUserFollows({
            user: "Alice",
            followees: ["Charlie", "Bob"]
        });
    });
});
