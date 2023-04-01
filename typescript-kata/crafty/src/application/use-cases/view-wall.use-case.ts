import {MessageRepository} from "../gateways/message.repository";
import {FolloweesRepository} from "../gateways/followees.repository";
import {ViewWallCommand} from "./commands/view-wall.command";
import {Timeline} from "../../domain/timeline";
import {TimelinePresenter} from "./presenters/timeline.presenter";

export class ViewWallUseCase {
    constructor(
        private readonly messageRepository: MessageRepository,
        private readonly followeesRepository: FolloweesRepository
    ) {
    }

    async handle({user}: ViewWallCommand, presenter: TimelinePresenter): Promise<void> {
        const followees = await this.followeesRepository.getFolloweesOf(user);
        const messages = (await Promise.all(
            [user, ...followees].map(u =>
                this.messageRepository.findMessagesByAuthorId(u))
        )).flat();

        presenter.showTimeline(new Timeline(messages));
    }
}