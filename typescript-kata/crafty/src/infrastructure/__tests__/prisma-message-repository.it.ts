import {PrismaClient} from "@prisma/client";
import {PostgreSqlContainer, StartedPostgreSqlContainer} from "testcontainers";
import {promisify} from "util";
import {exec} from "child_process";
import {PrismaMessageRepository} from "../prisma-message-repository";
import {messageBuilder} from "../../__tests__/message.builder";

describe('PrismaMessageRepository', () => {
    let container: StartedPostgreSqlContainer;
    let prisma: PrismaClient;

    const asyncExec = promisify(exec);

    beforeAll(async () => {
        container = await new PostgreSqlContainer()
            .withDatabase("crafty-db")
            .withUsername("crafty-test")
            .withPassword("crafty-test")
            .withExposedPorts(5432).start();
        const databaseUrl = `postgresql://${"crafty-test"}:${"crafty-test"}` +
            `@${container.getHost()}:${container.getPort()}/${container.getDatabase()}`;
        prisma = new PrismaClient({
            datasources: {
                db: {
                    url: databaseUrl
                }
            }
        });
        const {stderr/*, stdout */} = await asyncExec(`DATABASE_URL=${databaseUrl} npx prisma migrate deploy`);
        if (stderr) {
            console.error(stderr);
        }

        return prisma.$connect();
    }, 30000);

    afterAll(async () => {
        await container.stop({timeout: 10000});
        return prisma.$disconnect();
    });

    beforeEach(async () => {
        await prisma.message.deleteMany();
        await prisma.$executeRawUnsafe('DELETE FROM "User" CASCADE');
    });

    test("Should save a new message", async () => {
        const messageRepository = new PrismaMessageRepository(prisma);

        await messageRepository.save(
            messageBuilder()
                .withId("message-1")
                .writtenBy("user-1")
                .withText("Hello world!")
                .beingPublishedAt(new Date("2020-01-01T00:00:00Z"))
                .build());

        const expectedMessage = await prisma.message.findUnique({
            where: {id: "message-1"},
        });
        expect(expectedMessage).toEqual({
                id: "message-1",
                text: "Hello world!",
                publishedAt: new Date("2020-01-01T00:00:00Z"),
                authorId: "user-1"
            }
        );
    }, 30000);

    test("Should save an update of existing message", async () => {
        // arrange
        const messageRepository = new PrismaMessageRepository(prisma);
        const userOneMessageBuilder = messageBuilder()
            .withId("message-1")
            .writtenBy("user-1")
            .withText("Hello world!")
            .beingPublishedAt(new Date("2020-01-01T00:00:00Z"));
        await messageRepository.save(userOneMessageBuilder.build());

        // act
        await messageRepository.save(
            userOneMessageBuilder
                .withText("My new message!")
                .build());

        // assert
        const expectedMessage = await prisma.message.findUnique({
            where: {id: "message-1"},
        });
        expect(expectedMessage).toEqual({
                id: "message-1",
                text: "My new message!",
                publishedAt: new Date("2020-01-01T00:00:00Z"),
                authorId: "user-1"
            }
        );
    }, 30000);

    test("Should return a message by its id", async () => {
        // arrange
        const messageRepository = new PrismaMessageRepository(prisma);
        const userOneMessage = messageBuilder()
            .withId("message-1")
            .writtenBy("user-1")
            .withText("Hello world!")
            .beingPublishedAt(new Date("2020-01-01T00:00:00Z"))
            .build();
        await messageRepository.save(userOneMessage);

        // act
        const retrievedMessage = await messageRepository.findMessageById("message-1");

        // assert
        expect(retrievedMessage).toEqual(userOneMessage);
    }, 30000);

    test("Should return all messages by author id", async () => {
        // arrange
        const messageRepository = new PrismaMessageRepository(prisma);
        const userOneMessageBuilder = messageBuilder()
            .withId("message-1")
            .writtenBy("user-1")
            .withText("Hello world!")
            .beingPublishedAt(new Date("2020-01-01T00:00:00Z"));

        await messageRepository.save(userOneMessageBuilder
            .withId("message-1")
            .withText("Hello world!")
            .build());
        await messageRepository.save(userOneMessageBuilder
            .withId("message-2")
            .withText("Hello world 2!")
            .build());
        await messageRepository.save(userOneMessageBuilder
            .withId("message-3")
            .writtenBy("user-2")
            .withText("Hello world from user 2!")
            .build());

        // act
        const retrievedMessages = await messageRepository.findMessagesByAuthorId("user-1");

        // assert
        expect(retrievedMessages).toHaveLength(2);
        expect(retrievedMessages).toEqual(expect.arrayContaining([
            userOneMessageBuilder
                .withId("message-1")
                .withText("Hello world!")
                .build(),
            userOneMessageBuilder
                .withId("message-2")
                .withText("Hello world 2!")
                .build()
        ]));
    }, 30000);
});