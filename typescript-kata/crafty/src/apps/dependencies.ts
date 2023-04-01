import {PrismaClient} from "@prisma/client";
import {PrismaMessageRepository} from "../infrastructure/prisma-message-repository";
import {PrismaFolloweesRepository} from "../infrastructure/prisma-followees-repository";
import {RealDateProvider} from "../infrastructure/real-date-provider";
import {PostMessageUseCase} from "../application/use-cases/post-message.use-case";
import {EditMessageUseCase} from "../application/use-cases/edit-message.use-case";
import {FollowUserUseCase} from "../application/use-cases/follow-user.use-case";
import {ViewTimelineUseCase} from "../application/use-cases/view-timeline.use-case";
import {ViewWallUseCase} from "../application/use-cases/view-wall.use-case";

export const prismaClient = new PrismaClient();
const messageRepository = new PrismaMessageRepository(prismaClient);
const followeesRepository = new PrismaFolloweesRepository(prismaClient);
const dateProvider = new RealDateProvider();
export const postMessageUseCase = new PostMessageUseCase(
    messageRepository, dateProvider
);
export const editMessageUseCase = new EditMessageUseCase(messageRepository);
export const followUserUseCase = new FollowUserUseCase(
    followeesRepository
);
export const viewTimelineUseCase = new ViewTimelineUseCase(
    messageRepository, dateProvider
);
export const viewWallUseCase = new ViewWallUseCase(
    messageRepository, followeesRepository, dateProvider
);