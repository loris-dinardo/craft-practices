#!/usr/bin/env node
import {Argument, Command} from 'commander';
import {PostMessageCommand, PostMessageUseCase} from "./post-message.use-case";
import {RealDateProvider} from "./real-date-provider";
import {FileSystemMessageRepository} from "./file-system-message-repository";

const messageRepository = new FileSystemMessageRepository();
const dateProvider = new RealDateProvider();
const postMessageUseCase = new PostMessageUseCase(
    messageRepository, dateProvider
);
const program = new Command();

program.version("0.0.1").description("Crafty CLI")
    .addCommand(
        new Command("post")
            .addArgument(new Argument("<user>", "the user to post the message"))
            .addArgument(new Argument("<message>", "the message to post"))
            .description("Create a new Crafty project")
            .action(async (user, message) => {
                const postMessageCommand: PostMessageCommand = {
                    id: "id-message-1",
                    text: message,
                    authorId: user,
                }
                try {
                    await postMessageUseCase.handle(postMessageCommand);
                    console.log("Message posted");
                } catch (err) {
                    console.error("X", err);
                }
            })
    );

(async () => {
    await program.parseAsync(process.argv);
})();