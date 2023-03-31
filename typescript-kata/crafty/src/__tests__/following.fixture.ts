import {InMemoryFolloweesRepository} from "../infrastructure/in-memory-followees-repository";
import {FollowUserUseCase} from "../application/use-cases/follow-user.use-case";
import {FollowUserCommand} from "../application/use-cases/commands/follow-user.command";

export const createFollowingFixture = () => {
    const followeesRepository = new InMemoryFolloweesRepository();
    const followUserUseCase = new FollowUserUseCase(followeesRepository);
    return {
        givenUserAlreadyFollows({user, followees}: { user: string; followees: string[] }) {
            followeesRepository.existingFollowees(
                followees.map(f => ({
                    user,
                    followedUser: f
                })));
        },
        async whenUserFollows(command: FollowUserCommand) {
            await followUserUseCase.handle(command);
        },
        async thenUserFollows({user, followees}: { user: string; followees: string[] }) {
            const actualFollowees = await followeesRepository.getFolloweesOf(user);
            expect(actualFollowees).toEqual(followees);
        },
        followeesRepository,
    }
}

export type FollowingFixture = ReturnType<typeof createFollowingFixture>;