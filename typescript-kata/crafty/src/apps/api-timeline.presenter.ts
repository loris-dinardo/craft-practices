import {Timeline} from "../domain/timeline";
import {TimelinePresenter} from "../application/use-cases/presenters/timeline.presenter";
import {FastifyReply} from "fastify";

export class ApiTimelinePresenter implements TimelinePresenter {
    constructor(private readonly reply: FastifyReply) {
    }

    showTimeline(timeline: Timeline): void {
        this.reply.status(200).send(timeline.data);
    }
}