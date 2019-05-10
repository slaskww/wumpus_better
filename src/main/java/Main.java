import agents.HeuristicAgent;
import render.ConsoleRenderer;
import wumpus.Agent;
import wumpus.Player;
import wumpus.Runner;
import wumpus.World;

/**
 * Entry point for the application.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        try {
            // Create a 4x4 world
            World world = new World
                    .WorldBuilder().size(4,4).build();

            // Print the game title
            System.out.println("Hunt the Wumpus!");

            // Start and run the AI agent
            Agent agent = new HeuristicAgent(world.getWidth(), world.getHeight());
            Player player = new Player(world);
            player.setTile(world.getStartPosition());
            player.initialize();

            Runner runner = new Runner(world);
            runner.run(agent, player);

            // Print the board and score table
            System.out.println("Board:");
            System.out.println(ConsoleRenderer.renderAll(world, player));

            System.out.format("Results for %s:%n", agent.getClass().getName());
            System.out.println(ConsoleRenderer.renderScore(player));
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
