import {PostMessageCommand} from "../application/use-cases/post-message.use-case";
import Fastify, {FastifyInstance} from "fastify";
import * as httpErrors from "http-errors";
import {EditMessageCommand} from "../application/use-cases/commands/edit-message.command";
import {FollowUserCommand} from "../application/use-cases/commands/follow-user.command";
import {
    editMessageUseCase,
    followUserUseCase,
    postMessageUseCase,
    prismaClient,
    viewTimelineUseCase,
    viewWallUseCase
} from "./dependencies";

const fastify = Fastify({logger: true});
const routes = async (fastifyInstance: FastifyInstance) => {
    fastifyInstance.post<{ Body: { user: string, message: string } }>('/post',
        async (request, reply) => {
            const {user, message} = request.body;
            const postMessageCommand: PostMessageCommand = {
                id: `${Math.floor(Math.random() * 1000000)})}`,
                text: message,
                authorId: user,
            }
            try {
                await postMessageUseCase.handle(postMessageCommand);
                reply.status(201).send();
            } catch (err) {
                reply.send(httpErrors[500](err as string));
            }
        });
    fastifyInstance.post<{ Body: { messageId: string, message: string } }>('/edit',
        async (request, reply) => {
            const {messageId, message} = request.body;
            const editMessageCommand: EditMessageCommand = {
                messageId,
                text: message,
            }
            try {
                await editMessageUseCase.handle(editMessageCommand);
                reply.status(200).send();
            } catch (err) {
                reply.send(httpErrors[500](err as string));
            }
        });
    fastifyInstance.post<{ Body: { user: string, userToFollow: string } }>('/follow',
        async (request, reply) => {
            const {user, userToFollow} = request.body;
            const followUserCommand: FollowUserCommand = {
                user,
                userToFollow
            }
            try {
                await followUserUseCase.handle(followUserCommand);
                reply.status(201).send();
            } catch (err) {
                reply.send(httpErrors[500](err as string));
            }
        });
    fastifyInstance.get<{ Querystring: { userId: string } }>('/timeline',
        async (request, reply) => {
            const {userId} = request.query;
            try {
                const timeline = await viewTimelineUseCase.handle({userId});
                reply.status(200).send(timeline);
            } catch (err) {
                reply.send(httpErrors[500](err as string));
            }
        });
    fastifyInstance.get<{ Querystring: { user: string } }>('/wall',
        async (request, reply) => {
            const {user} = request.query;
            try {
                const wall = await viewWallUseCase.handle({user});
                reply.status(200).send(wall);
            } catch (err) {
                reply.send(httpErrors[500](err as string));
            }
        });
}

fastify.register(routes);
fastify.addHook('onClose', async () => {
    await prismaClient.$disconnect();
});

(async () => {
    try {
        await prismaClient.$connect();
        await fastify.listen({port: 3000});
    } catch (err) {
        fastify.log.error(err);
        process.exit(1);
    }
})();