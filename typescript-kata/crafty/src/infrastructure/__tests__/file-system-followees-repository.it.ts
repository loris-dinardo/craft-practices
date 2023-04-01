import * as path from "path";
import * as fs from "fs";
import {FileSystemFolloweesRepository} from "../file-system-followees-repository";

describe("File system followees repository", () => {
    const testFolloweesPath = path.join(__dirname, "test-followees.json");

    beforeEach(async () => {
        await fs.promises.writeFile(testFolloweesPath, JSON.stringify({}));
    });

    test("Should save a first followee", async () => {
        const followeesRepository = new FileSystemFolloweesRepository(testFolloweesPath);
        await fs.promises.writeFile(testFolloweesPath, JSON.stringify({
            Bob: ["Charlie"],
        }));

        await followeesRepository.saveFollowee({user: "Alice", followedUser: "Bob"});

        const followeesData =
            await fs.promises.readFile(testFolloweesPath, "utf-8");
        const followeesJson = JSON.parse(followeesData);
        expect(followeesJson).toEqual({
            Alice: ["Bob"],
            Bob: ["Charlie"],
        });
    });

    test("Should save an additional followee", async () => {
        const followeesRepository = new FileSystemFolloweesRepository(testFolloweesPath);
        await fs.promises.writeFile(testFolloweesPath, JSON.stringify({
            Alice: ["Bob"],
            Bob: ["Charlie"],
        }));

        await followeesRepository.saveFollowee({user: "Alice", followedUser: "Charlie"});

        const followeesData =
            await fs.promises.readFile(testFolloweesPath, "utf-8");
        const followeesJson = JSON.parse(followeesData);
        expect(followeesJson).toEqual({
            Alice: ["Bob", "Charlie"],
            Bob: ["Charlie"],
        });
    });

    test("Should return the user's followees", async () => {
        const followeesRepository = new FileSystemFolloweesRepository(testFolloweesPath);
        await fs.promises.writeFile(testFolloweesPath, JSON.stringify({
            Alice: ["Charlie", "Bob"],
            Bob: ["Charlie"],
        }));

        const [aliceFollowees, bobFollowees, charlieFollowees] = await Promise.all([
            followeesRepository.getFolloweesOf("Alice"),
            followeesRepository.getFolloweesOf("Bob"),
            followeesRepository.getFolloweesOf("Charlie"),
        ]);

        expect(aliceFollowees).toEqual(expect.arrayContaining(["Bob", "Charlie"]));
        expect(bobFollowees).toEqual(["Charlie"]);
        expect(charlieFollowees).toEqual([]);
    });
});