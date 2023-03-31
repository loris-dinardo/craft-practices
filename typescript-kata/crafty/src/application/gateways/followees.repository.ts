import {Followee} from "../../domain/followee";

export interface FolloweesRepository {
    saveFollowee(followee: Followee): Promise<void>;
    getFolloweesOf(userId: string): Promise<string[]>;
}