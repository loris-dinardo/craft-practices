import {MessageRepository} from "../gateways/message.repository";
import {Timeline} from "../../domain/timeline";
import {TimelinePresenter} from "./presenters/timeline.presenter";

export class ViewTimelineUseCase {
    constructor(
        private readonly messageRepository: MessageRepository
    ) {
    }

    async handle({userId}: { userId: string }, presenter: TimelinePresenter): Promise<void> {
        const messagesOfUser = await this.messageRepository
            .findMessagesByAuthorId(userId);
        presenter.showTimeline(new Timeline(messagesOfUser));
    }
}