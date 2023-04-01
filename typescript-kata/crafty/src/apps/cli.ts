#!/usr/bin/env node
import {Argument, Command} from 'commander';
import {PostMessageCommand} from "../application/use-cases/post-message.use-case";
import {EditMessageCommand} from "../application/use-cases/commands/edit-message.command";
import {FollowUserCommand} from "../application/use-cases/commands/follow-user.command";
import {
    defaultTimelinePresenter,
    editMessageUseCase,
    followUserUseCase,
    postMessageUseCase,
    prismaClient,
    viewTimelineUseCase,
    viewWallUseCase
} from "./dependencies";
import {CliTimelinePresenter} from "./cli-timeline.presenter";

const cliPresenter = new CliTimelinePresenter(defaultTimelinePresenter);
const program = new Command();
program.version("0.0.1").description("Crafty CLI")
    .addCommand(
        new Command("post")
            .addArgument(new Argument("<user>", "the user to post the message"))
            .addArgument(new Argument("<message>", "the message to post"))
            .description("Post a new message")
            .action(async (user, message) => {
                const postMessageCommand: PostMessageCommand = {
                    id: `${Math.floor(Math.random() * 1000000)}`,
                    text: message,
                    authorId: user,
                }
                try {
                    const result = await postMessageUseCase.handle(postMessageCommand);
                    if (result.isOk()){
                        console.log("Message posted");
                        process.exit(0);
                    }
                    console.error("X", result.error);
                    process.exit(1);
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
                    const result = await editMessageUseCase.handle(editMessageCommand);
                    if (result.isOk()){
                        console.log("Message edited");
                        process.exit(0);
                    }
                    console.error("X", result.error);
                    process.exit(1);
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
                    const timeline = await viewTimelineUseCase.handle({userId}, cliPresenter);
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
                    const wall = await viewWallUseCase.handle({user}, cliPresenter);
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
    await prismaClient.$connect();
    await program.parseAsync(process.argv);
    await prismaClient.$disconnect();
})();