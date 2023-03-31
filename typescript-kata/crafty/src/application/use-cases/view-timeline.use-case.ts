import {MessageRepository} from "../gateways/message.repository";
import {DateProvider} from "../gateways/date-time-provider";
import {Timeline, TimelineMessage} from "../../domain/timeline";

export class ViewTimelineUseCase {
    constructor(
        private readonly messageRepository: MessageRepository,
        private readonly dateProvider: DateProvider
    ) {
    }

    async handle({userId}: { userId: string }): Promise<TimelineMessage[]> {
        const messagesOfUser = await this.messageRepository
            .findMessagesByAuthorId(userId);

        return new Timeline(messagesOfUser, this.dateProvider.getNow()).data;
    }
}