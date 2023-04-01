import {Timeline} from "../domain/timeline";
import {TimelinePresenter} from "../application/use-cases/presenters/timeline.presenter";
import {DefaultTimelinePresenter} from "./default-timeline.presenter";

export class CliTimelinePresenter implements TimelinePresenter {
    constructor(private readonly defaultPresenter: DefaultTimelinePresenter) {
    }

    showTimeline(timeline: Timeline): void {
        console.table(this.defaultPresenter.showTimeline(timeline));
    }
}