package render;

import wumpus.Environment;
import wumpus.Player;
import wumpus.Tile;
import wumpus.World;

/**
 * Wszystkie metody w tej klasi zostały wydzielone z klasy World
 * Wydzielając metody rozdzielamy to w jaki sposób mapa jest prezentowana (w konsoli) od tego jak jest zaimplementowana
 */
public class ConsoleRenderer {

    private ConsoleRenderer() {
    }

    /**
     * Renders a simplified version of the game board as an ASCII string.
     * Each block is has only the hunter:
     * <pre>
     *     +---+
     *     | H |
     *     +---+
     * </pre>
     *
     * @return The board representation
     */
    public static String render(World world, Player player) {
        StringBuilder render = new StringBuilder();

        for(int y = 0; y < world.getHeight(); y++) {
            for(int z = 0; z < 2; z++) {
                for (int x = 0; x < world.getWidth(); x++) {
                    switch (z) {
                        case 0:
                            if (x == 0) render.append("+");
                            render.append("---+");
                            break;
                        default:
                            Tile tile = world.getPosition(x, y);
                            String line = " 1 |";
                            if (tile.contains(Environment.Element.HUNTER)) {
                                line = line.replace("1", Environment.getIcon(player));
                            }
                            // Erase any non-replaced items
                            line = line.replace("1", " ");
                            // Draw
                            if (x == 0) render.append("|");
                            render.append(line);
                    }
                }
                render.append("\n");
            }
        }
        for (int i = 0; i < world.getWidth(); i++) {
            if (i == 0) render.append("+");
            render.append("---+");
        }
        return render.toString();
    }

    /**
     * Renders the full game board as an ASCII string.
     * Each block is composed by:
     * <pre>
     *     +-----+
     *     |   D |
     *     | H P |
     *     +-----+
     *     D = Danger, P = Perception, H = Hunter
     * </pre>
     *
     * @return The board representation
     */
    public static String renderAll(World world, Player player) {
        StringBuilder render = new StringBuilder();

        for(int y = 0; y < world.getHeight(); y++) {
            for(int z = 0; z < 3; z++) {
                for (int x = 0; x < world.getWidth(); x++) {
                    switch (z) {
                        case 0:
                            if (x == 0) render.append("+");
                            render.append("-----+");
                            break;
                        default:
                            Tile tile = world.getPosition(x, y);
                            String line = " 1 2 |";
                            if (z == 1) {
                                // Renders the second line
                                if (tile.contains(Environment.Element.WUMPUS)) {
                                    line = line.replace("2", Environment.getIcon(Environment.Element.WUMPUS));
                                }
                                if (tile.contains(Environment.Element.PIT)) {
                                    line = line.replace("2", Environment.getIcon(Environment.Element.PIT));
                                }
                                if (tile.contains(Environment.Element.GOLD)) {
                                    line = line.replace("2", Environment.getIcon(Environment.Element.GOLD));
                                }
                            } else {
                                if (tile.contains(Environment.Element.HUNTER)) {
                                    line = line.replace("1", Environment.getIcon(player));
                                }
                                if (tile.contains(Environment.Element.GOLD)) {
                                    line = line.replace("2",
                                            Environment.getIcon(Environment.Perception.GLITTER));
                                }
                                // Mark this tile if some of their neighbor has some danger
                                int[] neighbors = tile.getNeighbors();
                                for (int s = 0; s < neighbors.length; s++) {
                                    if (neighbors[s] == -1) continue;
                                    Tile neighbor = world.getPosition(neighbors[s]);
                                    if (neighbor.contains(Environment.Element.WUMPUS)) {
                                        line = line.replace("2",
                                                Environment.getIcon(Environment.Perception.STENCH));
                                    }
                                    if (neighbor.contains(Environment.Element.PIT)) {
                                        line = line.replace("2",
                                                Environment.getIcon(Environment.Perception.BREEZE));
                                    }
                                }
                            }
                            // Erase any non-replaced items
                            line = line.replace("1", " ").replace("2", " ");
                            // Draw
                            if (x == 0) render.append("|");
                            render.append(line);
                    }
                }
                render.append("\n");
            }
        }
        for (int i = 0; i < world.getWidth(); i++) {
            if (i == 0) render.append("+");
            render.append("-----+");
        }
        return render.toString();
    }

    /**
     * Renders the score table as a ASCII string.
     * @return The score table
     */
    public static String renderScore(Player player) {
        String scoreTable = String.format(
                "+----------------------------+%n" +
                        "| Outcome | Score    | Steps |%n" +
                        "| ------- | -------- | ----- |%n" +
                        "| %-7s | %8d | %5d |%n" +
                        "+----------------------------+%n",
                player.toString(), player.getScore(), player.getActions().size()
        );

        return scoreTable;
    }
}
