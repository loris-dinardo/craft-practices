import {FolloweesRepository} from "../application/gateways/followees.repository";
import {Followee} from "../domain/followee";

export class InMemoryFolloweesRepository implements FolloweesRepository {
    followeesByUser = new Map<string, string[]>();

    saveFollowee(followee: Followee): Promise<void> {
        this.addFollowee(followee);
        return Promise.resolve();
    }

    existingFollowees(followees: Followee[]) {
        followees.forEach(f => this.addFollowee(f));
    }

    private addFollowee(followee: Followee) {
        const existing = this.followeesByUser.get(followee.user) ?? [];
        this.followeesByUser.set(followee.user, [...existing, followee.followedUser]);
    }

    async getFolloweesOf(user: string): Promise<string[]> {
        return Promise.resolve(this.followeesByUser.get(user) ?? []);
    }
}