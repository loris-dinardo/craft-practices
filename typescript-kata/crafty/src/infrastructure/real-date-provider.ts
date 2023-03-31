import {DateProvider} from "../application/gateways/date-time-provider";

export class RealDateProvider implements DateProvider {
    getNow(): Date {
        return new Date();
    }
}