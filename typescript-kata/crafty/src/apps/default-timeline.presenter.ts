import {Timeline, TimelineMessage} from "../domain/timeline";
import {TimelinePresenter} from "../application/use-cases/presenters/timeline.presenter";
import {DateProvider} from "../application/gateways/date-time-provider";

export class DefaultTimelinePresenter implements TimelinePresenter {
    private readonly ONE_MINUTE = 60000;

    constructor(private readonly dateProvider: DateProvider) {
    }

    showTimeline(timeline: Timeline): TimelineMessage[] {
        const messages = timeline.data;
        return messages.map(message => ({
            user: message.authorId,
            message: message.text,
            publicationTime: this.publicationTime(message.publishedAt)
        }));
    }

    private publicationTime(publishedAt: Date): string {
        const diff = this.dateProvider.getNow().getTime() - publishedAt.getTime();
        const diffInMinutes = diff / this.ONE_MINUTE;
        if (diffInMinutes < 1) {
            return "less than a minute ago";
        }
        if (diffInMinutes < 2) {
            return "1 minute ago";
        }
        return Math.floor(diffInMinutes) + " minutes ago";
    }
}