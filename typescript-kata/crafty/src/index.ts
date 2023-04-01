#!/usr/bin/env node
import {Argument, Command} from 'commander';
import {PostMessageCommand, PostMessageUseCase} from "./application/use-cases/post-message.use-case";
import {RealDateProvider} from "./infrastructure/real-date-provider";
import {FileSystemMessageRepository} from "./infrastructure/file-system-message-repository";
import {ViewTimelineUseCase} from "./application/use-cases/view-timeline.use-case";
import {EditMessageUseCase} from "./application/use-cases/edit-message.use-case";
import {EditMessageCommand} from "./application/use-cases/commands/edit-message.command";
import {FollowUserUseCase} from "./application/use-cases/follow-user.use-case";
import {FileSystemFolloweesRepository} from "./infrastructure/file-system-followees-repository";
import {FollowUserCommand} from "./application/use-cases/commands/follow-user.command";
import {ViewWallUseCase} from "./application/use-cases/view-wall.use-case";

const messageRepository = new FileSystemMessageRepository();
const followeesRepository = new FileSystemFolloweesRepository();
const dateProvider = new RealDateProvider();
const postMessageUseCase = new PostMessageUseCase(
    messageRepository, dateProvider
);
const editMessageUseCase = new EditMessageUseCase(messageRepository);
const followUserUseCase = new FollowUserUseCase(
    followeesRepository
);
const viewTimelineUseCase = new ViewTimelineUseCase(
    messageRepository, dateProvider
);
const viewWallUseCase = new ViewWallUseCase(
    messageRepository, followeesRepository, dateProvider
)
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
        new Command("follow")
            .addArgument(new Argument("<user>", "the current user"))
            .addArgument(new Argument("<user-to-follow>", "the user to follow"))
            .description("Follow a user")
            .action(async (user, userToFollow) => {
                const followUserCommand: FollowUserCommand = {
                    user,
                    userToFollow
                }
                try {
                    await followUserUseCase.handle(followUserCommand);
                    console.log(`User ${user} follows ${userToFollow}`);
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
    .addCommand(
        new Command("wall")
            .addArgument(new Argument("<user>", "the user to view the wall"))
            .description("View wall of a user")
            .action(async (user) => {
                try {
                    const wall = await viewWallUseCase.handle({user});
                    console.table(wall);
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