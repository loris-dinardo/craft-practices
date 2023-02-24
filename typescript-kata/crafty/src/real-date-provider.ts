import {DateProvider} from "./post-message.use-case";

export class RealDateProvider implements DateProvider {
    getNow(): Date {
        return new Date();
    }
}