import {FolloweesRepository} from "../application/gateways/followees.repository";
import {Followee} from "../domain/followee";

export class FileSystemFolloweesRepository implements FolloweesRepository {
    getFolloweesOf(userId: string): Promise<string[]> {
        return Promise.resolve([]);
    }

    saveFollowee(followee: Followee): Promise<void> {
        return Promise.resolve(undefined);
    }
}