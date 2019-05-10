package wumpus;

import java.util.HashMap;
import java.util.Random;

/**
 * The World is a representation of the game board, it handles the position of the peers and the
 * render of it.
 */
public class World {

    private final int startPosition;
    private Tile[] tiles;
    private int width;
    private int height;

    World(Tile[] tiles, int width, int height, int startPosition) {
        this.tiles = tiles;
        this.width = width;
        this.height = height;
        this.startPosition = startPosition;
    }

    /**
     * Returns the board block at given linear position.
     * @param index The block position
     * @return The block instance
     */
    public Tile getPosition(int index) {
        return tiles[index];
    }

    /**
     * Returns the index from a given 2D position.
     * @param x The horizontal position
     * @param y The vertical position
     * @return The index
     */
    private int getIndex(int x, int y) {
        return (x + y * width);
    }

    /**
     * Returns the board block at given 2D position.
     * @param x The horizontal position
     * @param y The vertical position
     * @return The block instance
     */
    public Tile getPosition(int x, int y) {
        int i = getIndex(x, y);
        return tiles[i];
    }

    /**
     * Returns the board width.
     * @return The width
     */
    public int getWidth() { return width; }

    /**
     * Returns the board height.
     * @return The height
     */
    public int getHeight() { return height; }

    public int getStartPosition() {
        return startPosition;
    }

    public static class WorldBuilder {
        private static final int RANDOM_MAX_TRIES = 20;
        private static final int DEFAULT_GOLD = 1;
        private static final int DEFAULT_WUMPUS = 1;
        private static final int DEFAULT_PITS = 2;

        private int width;
        private int height;
        private int startPosition;

        private int gold = DEFAULT_GOLD;
        private int pits = DEFAULT_PITS;
        private int wumpus = DEFAULT_WUMPUS;

        private boolean randomize = true;
        private HashMap<Integer, Environment.Element> items = new HashMap<Integer, Environment.Element>();

        public WorldBuilder() {
        }

        /**
         * Set a new world dimensions.
         * @param width The horizontal constraint of the board
         * @param height The vertical constraint of the board
         * @throws InterruptedException
         * @throws InternalError
         */
        public WorldBuilder size(int width, int height) throws InterruptedException {
            if (width == 1 && height == 1) {
                throw new InternalError("The world WorldBuilder must be greater than 1x1.");
            }
            this.width = width;
            this.height = height;
            return this;
        }

        /**
         * Set the number of pits on the board.
         * @param value
         */
        public WorldBuilder setPits(int value) {
            pits = value;
            return this;
        }

        /**
         * Sets a pit at given coordinate.
         * @param x The horizontal coordinate
         * @param y The vertical coordinate
         */
        public WorldBuilder setPit(int x, int y) {
            setItem(Environment.Element.PIT, x, y);
            return this;
        }

        /**
         * Set the number of Wumpus on the board.
         * @param value
         */
        public WorldBuilder setWumpus(int value) {
            wumpus = value;
            return this;
        }

        /**
         * Sets a Wumpus at given coordinate.
         * @param x The horizontal position
         * @param y The vertical position
         */
        public WorldBuilder setWumpus(int x, int y) {
            setItem(Environment.Element.WUMPUS, x, y);
            return this;
        }

        /**
         * Sets the Gold at given coordinate.
         * @param x The horizontal position
         * @param y The vertical position
         */
        public WorldBuilder setGold(int x, int y) {
            setItem(Environment.Element.GOLD, x, y);
            return this;
        }

        /**
         * Sets the element at given coordinates and saves it for later retrieval.
         *
         * @param element The element to plate
         * @param x       The horizontal position
         * @param y       The vertical position
         */
        private void setItem(Environment.Element element, int x, int y) {
            int idx = getIndex(x, y); //get index of position in array
            if (items.containsKey(idx)) { //check if it not used already
                throw new InternalError("Tile is not empty!");
            }
            // Saves the items position for later retrieval
            items.put(idx, element);
            // Turn off randomization
            randomize = false;
        }

        /**
         * Sets a random position for the a set of items respecting safe blocks.
         *
         * @param tiles
         * @param startTile
         * @param element The element to be place
         * @param times How many items to be placed.
         * @throws InterruptedException When reaches too many tries
         */
        private void setRandom(Tile[] tiles, Tile startTile, Environment.Element element, int times) throws InterruptedException {
            Random random = new Random();
            int tries = 0;
            // Set the starting point neighbors as safe
            int[] safeBlocks = startTile.getNeighbors();

            for(int i = 0; i < times; i++) {
                Tile position;
                // Find an empty block to set the element
                while (true) {
                    int z = random.nextInt(width * height - 1);
                    position = tiles[z];
                    if(position.isEmpty() &&
                            z != safeBlocks[0] && z != safeBlocks[1]  && z != safeBlocks[2]  &&
                            z != safeBlocks[3]) {
                        position.setItem(element);
                        break;
                    }
                    // Do not loop forever
                    if (tries >= RANDOM_MAX_TRIES) {
                        throw new InterruptedException("Cannot set a random position for element after " +
                                "many tries, increase the world dimensions.");
                    } else {
                        tries++;
                    }
                }
            }
        }

        /**
         * Returns the index from a given 2D position.
         * @param x The horizontal position
         * @param y The vertical position
         * @return The index
         */
        private int getIndex(int x, int y) {
            return (x + y * width);
        }

        /**
         * Resets the board.
         * @throws InterruptedException
         */
        public World build() throws InterruptedException {

            // Generate the board matrix (WxH)
            Tile[] tiles = new Tile[width * height];
            for (int i = 0; i < width * height; i++) {
                tiles[i] = new Tile(i, width, height);
            }
            // Saves the start position to check the objective
            startPosition = getIndex(0, height - 1);

            Tile startTile = tiles[startPosition];
            // Set the player

            // Reset all blocks
            for (int i = 0; i < tiles.length; i++) {
                tiles[i].clear();
            }

            // Set the dangers
            if (randomize) {
                setRandom(tiles, startTile, Environment.Element.WUMPUS, wumpus);
                setRandom(tiles, startTile, Environment.Element.PIT, pits);
                // Set the objective
                setRandom(tiles, startTile, Environment.Element.GOLD, gold);
            } else {
                for (int index : items.keySet()) {
                    Tile tile = tiles[index];
                    tile.setItem(items.get(index));
                }
            }

            World world = new World(tiles, width, height, startPosition);
            return world;
        }

    }
}
