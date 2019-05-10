package wumpus;

import java.util.Iterator;
import java.util.NoSuchElementException;

import wumpus.Environment.Action;
import wumpus.Environment.Result;

/**
 * The iteration of plays that the player can take until reaches its end.
 */
public class Runner {
    private static final int DEFAULT_MAX_STEPS = 200;

    private final World world;
    private int iterations = 0;
    private int maxIterations;

    /**
     * The runner constructor.
     * @param world The world instance.
     */
    public Runner(World world) {
        this(world, DEFAULT_MAX_STEPS);
    }

    /**
     * The runner constructor.
     * @param world The world instance.
     * @param maxIterations maximum number of moves in game.
     */
    public Runner(World world, int maxIterations) {
        this.world = world;
        this.maxIterations = maxIterations;
    }

    /**
     * Execute an agent that plays the game automatically.
     * @param agent The agent instance
     * @throws InterruptedException
     */
    public void run(Agent agent) throws InterruptedException {
        Player player = world.getPlayer();

        while (canMove()) {
            agent.beforeAction(player);
            Action actions = agent.getAction(player);
            player.setAction(actions);
            agent.afterAction(player);
            iterations++;
        }
    }

    /**
     * Check if the game has ended.
     * @return
     */
    public boolean canMove() {
        Player player = world.getPlayer();
        return iterations < maxIterations && world.getResult() != Result.WIN &&
                player.isAlive() && player.getLastAction() != Action.EXIT;
    }

}
