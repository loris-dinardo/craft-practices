#!/usr/bin/env node
import {Argument, Command} from 'commander';
import {PostMessageCommand, PostMessageUseCase} from "./application/use-cases/post-message.use-case";
import {RealDateProvider} from "./infrastructure/real-date-provider";
import {FileSystemMessageRepository} from "./infrastructure/file-system-message-repository";
import {ViewTimelineUseCase} from "./application/use-cases/view-timeline.use-case";
import {EditMessageUseCase} from "./application/use-cases/edit-message.use-case";
import {EditMessageCommand} from "./application/use-cases/commands/edit-message.command";

const messageRepository = new FileSystemMessageRepository();
const dateProvider = new RealDateProvider();
const postMessageUseCase = new PostMessageUseCase(
    messageRepository, dateProvider
);

const editMessageUseCase = new EditMessageUseCase(messageRepository);

const viewTimelineUseCase = new ViewTimelineUseCase(
    messageRepository, dateProvider
);
const program = new Command();

program.version("0.0.1").description("Crafty CLI")
    .addCommand(
        new Command("post")
            .addArgument(new Argument("<user>", "the user to post the message"))
            .addArgument(new Argument("<message>", "the message to post"))
            .description("Post a new message")
            .action(async (user, message) => {
                const postMessageCommand: PostMessageCommand = {
                    id: `${Math.floor(Math.random() * 1000000)})}`,
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
    )
    .addCommand(
        new Command("edit")
            .addArgument(new Argument("<message-id>", "the message-id if the message to edit"))
            .addArgument(new Argument("<message>", "the new message text"))
            .description("Edit an existing message")
            .action(async (messageId, message) => {
                const editMessageCommand: EditMessageCommand = {
                    messageId,
                    text: message,
                }
                try {
                    await editMessageUseCase.handle(editMessageCommand);
                    console.log("Message edited");
                } catch (err) {
                    console.error("X", err);
                }
            })
    )
    .addCommand(
        new Command("view")
            .addArgument(new Argument("<user>", "the user to view the messages"))
            .description("View timeline of a user")
            .action(async (userId) => {
                try {
                    const timeline = await viewTimelineUseCase.handle({userId});
                    console.table(timeline);
                    process.exit(0);
                } catch (err) {
                    console.error(err);
                    process.exit(1);
                }
            })
    )
;

(async () => {
    await program.parseAsync(process.argv);
})();