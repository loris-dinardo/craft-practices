import {Timeline} from "../../../domain/timeline";

export interface TimelinePresenter {
    showTimeline(timeline: Timeline): void;
}