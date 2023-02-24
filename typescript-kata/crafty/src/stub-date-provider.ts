import {DateProvider} from "./post-message.use-case";

export class StubDateProvider implements DateProvider {
    now: Date = new Date()

    getNow(): Date {
        return this.now;
    }
}