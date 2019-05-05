import agents.HeuristicAgent;
import controls.CommandParser;
import controls.ConsoleCommandParser;
import wumpus.Agent;
import wumpus.Environment;
import wumpus.Player;
import wumpus.World;

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

        world.execute(agent);

        // Print the board and score table
        System.out.println("Board:");
        System.out.println(world.renderAll());

        System.out.format("Results for %s:%n", world.getAgentName());
        System.out.println(world.renderScore());
    }
}
