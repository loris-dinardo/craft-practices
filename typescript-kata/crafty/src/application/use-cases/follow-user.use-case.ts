import {FollowUserCommand} from "./commands/follow-user.command";
import {FolloweesRepository} from "../gateways/followees.repository";

export class FollowUserUseCase {
    constructor(
        private readonly followeesRepository: FolloweesRepository
    ) {
    }

    async handle(command: FollowUserCommand): Promise<void> {
        await this.followeesRepository.saveFollowee({
            user: command.user,
            followedUser: command.userToFollow
        });
    }
}