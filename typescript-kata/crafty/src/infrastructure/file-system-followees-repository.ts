import {FolloweesRepository} from "../application/gateways/followees.repository";
import {Followee} from "../domain/followee";
import path from "path";
import fs from "fs";

export class FileSystemFolloweesRepository implements FolloweesRepository {
    constructor(
        private readonly followeesPath = path.join(__dirname, "followees.json")
    ) {
        if (!fs.existsSync(this.followeesPath)) {
            fs.writeFileSync(this.followeesPath, "{}");
        }
    }

    async getFolloweesOf(user: string): Promise<string[]> {
        const allFollowees = await this.getFollowees();
        return allFollowees[user] || [];
    }

    async saveFollowee(followee: Followee): Promise<void> {
        const followees = await this.getFollowees();
        const followeesOfUser = followees[followee.user] || [];
        followees[followee.user] = [...followeesOfUser, followee.followedUser];

        return fs.promises.writeFile(this.followeesPath, JSON.stringify(followees));
    }

    private async getFollowees(): Promise<Record<string, string[]>> {
        const followeesData =
            await fs.promises.readFile(this.followeesPath, "utf-8");
        return JSON.parse(followeesData);
    }
}