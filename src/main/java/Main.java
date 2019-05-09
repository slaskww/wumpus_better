import agents.HeuristicAgent;
import wumpus.Agent;
import wumpus.Runner;
import wumpus.World;

/**
 * Entry point for the application.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        try {
            // Create a 4x4 world
            World world = new World(4, 4);

            // Print the game title
            System.out.println("Hunt the Wumpus!");

            // Start and run the AI agent
            Agent agent = new HeuristicAgent(world.getWidth(), world.getHeight());
            world.reset();
            Runner runner = new Runner(world);
            runner.run(agent);

            // Print the board and score table
            System.out.println("Board:");
            System.out.println(world.renderAll());

            System.out.format("Results for %s:%n", world.getAgentName());
            System.out.println(world.renderScore());
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
