import {DateProvider} from "./date-time-provider";

export class RealDateProvider implements DateProvider {
    getNow(): Date {
        return new Date();
    }
}