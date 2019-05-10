import agents.HeuristicAgent;
import controls.CommandParser;
import controls.ConsoleCommandParser;
import render.ConsoleRenderer;
import wumpus.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class UserControlled {

    private static final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    private static final CommandParser<String> commandParser = ConsoleCommandParser.INSTANCE;

    public static void main(String[] args) throws InterruptedException {
        // Create a 4x4 world
        World world = new World(4, 4);

        // Print the game title
        System.out.println("Hunt the Wumpus!");

        Agent agent = new HeuristicAgent(world.getWidth(), world.getHeight()) {

            @Override
            public Environment.Action getAction(Player player) {
                Optional<Environment.Action> action = Optional.empty();
                try {
                    do {
                        System.out.println("What to do?");
                        String command = reader.readLine();
                        action = commandParser.getAction(command);
                    }
                    while(!action.isPresent());

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Exception during reading command. Exiting game");
                    action = Optional.of(Environment.Action.EXIT);
                }
                return action.get();
            }
        };

        world.reset();
        Runner runner = new Runner(world);
        runner.run(agent);

        // Print the board and score table
        System.out.println("Board:");
        System.out.println(ConsoleRenderer.renderAll(world));

        System.out.format("Results for %s:%n", agent.getClass().getName());
        System.out.println(ConsoleRenderer.renderScore(world));
    }
}
