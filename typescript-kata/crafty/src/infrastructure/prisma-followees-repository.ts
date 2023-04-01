import {FolloweesRepository} from "../application/gateways/followees.repository";
import {Followee} from "../domain/followee";
import {PrismaClient} from "@prisma/client";

export class PrismaFolloweesRepository implements FolloweesRepository {
    constructor(private readonly prisma: PrismaClient) {
    }

    async getFolloweesOf(user: string): Promise<string[]> {
        const theUser = await this.prisma.user.findFirstOrThrow({
            where: {name: user},
            include: {following: true}
        });
        return theUser.following.map(followee => followee.name);
    }

    async saveFollowee(followee: Followee): Promise<void> {
        await this.upsertUser(followee.user);
        await this.upsertUser(followee.followedUser);
        await this.prisma.user.update({
            where: {name: followee.user},
            data: {
                following: {
                    connectOrCreate: [
                        {
                            where: {name: followee.followedUser},
                            create: {name: followee.followedUser}
                        }
                    ]
                }
            }
        });
    }

    private async upsertUser(user: string): Promise<void> {
        await this.prisma.user.upsert({
            where: {name: user},
            update: {name: user},
            create: {name: user}
        });
    }
}