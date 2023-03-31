import {DateProvider} from "../gateways/date-time-provider";
import {MessageRepository} from "../gateways/message.repository";
import {FolloweesRepository} from "../gateways/followees.repository";
import {ViewWallCommand} from "./commands/view-wall.command";
import {Timeline, TimelineMessage} from "../../domain/timeline";

export class ViewWallUseCase {
    constructor(
        private readonly messageRepository: MessageRepository,
        private readonly followeesRepository: FolloweesRepository,
        private readonly dateProvider: DateProvider,
    ) {
    }

    async handle({user}: ViewWallCommand): Promise<TimelineMessage[]> {
        const followees = await this.followeesRepository.getFolloweesOf(user);
        const messages = (await Promise.all(
            [user, ...followees].map(u =>
                this.messageRepository.findMessagesByAuthorId(u))
        )).flat();

        return new Timeline(messages, this.dateProvider.getNow()).data;
    }
}