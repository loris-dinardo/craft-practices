import * as path from "path";
import {FileSystemMessageRepository} from "../file-system-message-repository";
import * as fs from "fs";
import {messageBuilder} from "../../__tests__/message.builder";

describe("File system message repository", () => {
    const testMessagePath = path.join(__dirname, "test-messages.json");

    beforeEach(async () => {
        await fs.promises.writeFile(testMessagePath, JSON.stringify([]));
    });

    test("Can save a message in the file system", async () => {
        const messageRepository = new FileSystemMessageRepository(testMessagePath);

        await messageRepository.save(
            messageBuilder()
                .withId("message-id-1")
                .writtenBy("Alice")
                .withText("Hello world!")
                .beingPublishedAt(new Date("2023-01-19T19:00:00.000Z"))
                .build()
        );

        const messages = await fs.promises.readFile(testMessagePath, "utf-8");
        const messagesAsJson = JSON.parse(messages);
        expect(messagesAsJson).toEqual([
            {
                id: "message-id-1",
                authorId: "Alice",
                text: "Hello world!",
                publishedAt: "2023-01-19T19:00:00.000Z"
            }
        ]);
    });

    test("Can update an existing message in the file system", async () => {
        const messageRepository = new FileSystemMessageRepository(testMessagePath);
        await fs.promises.writeFile(testMessagePath, JSON.stringify([
            {
                id: "message-id-1",
                authorId: "Alice",
                text: "Hello world!",
                publishedAt: "2023-01-19T19:00:00.000Z"
            }]));

        await messageRepository.save(
            messageBuilder()
                .withId("message-id-1")
                .writtenBy("Alice")
                .withText("New Message")
                .beingPublishedAt(new Date("2023-01-19T19:00:00.000Z"))
                .build()
        );

        const messages = await fs.promises.readFile(testMessagePath, "utf-8");
        const messagesAsJson = JSON.parse(messages);
        expect(messagesAsJson).toEqual([
            {
                id: "message-id-1",
                authorId: "Alice",
                text: "New Message",
                publishedAt: "2023-01-19T19:00:00.000Z"
            }
        ]);
    });

    test("Can find message by id", async () => {
        const messageRepository = new FileSystemMessageRepository(testMessagePath);
        await fs.promises.writeFile(testMessagePath, JSON.stringify([
            {
                id: "message-id-1",
                authorId: "Alice",
                text: "Hello world!",
                publishedAt: "2023-01-19T19:00:00.000Z"
            },
            {
                id: "message-id-2",
                authorId: "Alice",
                text: "You can find me!",
                publishedAt: "2023-01-19T19:00:00.000Z"
            }
        ]));

        const message = await messageRepository.findMessageById("message-id-2");

        expect(message).toEqual(
            messageBuilder()
                .withId("message-id-2")
                .writtenBy("Alice")
                .withText("You can find me!")
                .beingPublishedAt(new Date("2023-01-19T19:00:00.000Z"))
                .build()
        );
    });

    test("Can get all messages by user", async () => {
        const messageRepository = new FileSystemMessageRepository(testMessagePath);
        await fs.promises.writeFile(testMessagePath, JSON.stringify([
            {
                id: "message-id-1",
                authorId: "Alice",
                text: "Hello world!",
                publishedAt: "2023-01-19T19:00:00.000Z"
            },
            {
                id: "message-id-3",
                authorId: "Bob",
                text: "My name is Bob",
                publishedAt: "2023-01-19T19:10:00.000Z"
            },
            {
                id: "message-id-2",
                authorId: "Alice",
                text: "You can find me!",
                publishedAt: "2023-01-19T19:50:00.000Z"
            }
        ]));

        const messages = await messageRepository.findMessagesByAuthorId("Alice");

        expect(messages).toHaveLength(2)
        expect(messages).toEqual(
            expect.arrayContaining([
                messageBuilder()
                    .withId("message-id-1")
                    .writtenBy("Alice")
                    .withText("Hello world!")
                    .beingPublishedAt(new Date("2023-01-19T19:00:00.000Z"))
                    .build(),
                messageBuilder()
                    .withId("message-id-2")
                    .writtenBy("Alice")
                    .withText("You can find me!")
                    .beingPublishedAt(new Date("2023-01-19T19:50:00.000Z"))
                    .build()
            ]));
    });
});