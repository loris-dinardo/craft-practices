import {DateProvider} from "./date-time-provider";

export class StubDateProvider implements DateProvider {
    now: Date = new Date()

    getNow(): Date {
        return this.now;
    }
}