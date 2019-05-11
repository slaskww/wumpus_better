package wumpus;

import org.junit.jupiter.api.Test;
import render.ConsoleRenderer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static wumpus.Environment.Action.*;
import static wumpus.Environment.Perception.GLITTER;

public class WorldTest {

    @Test
    public void testSuccessPath() throws InterruptedException {

        World worldStub = new World(4, 4);
        // autor kodu zrobił metodę nieintuicyjnie odwrotnie niż układ znany z metematyki
        // jeśli chcemy ustwiać złoto w prawym dolnym rogu musimy ustawić x na 3
        // i y na 3 (y jest liczony od góry układu, nie od dołu)
        worldStub.setGold(3,3);
        worldStub.initialize();

        System.out.println(ConsoleRenderer.renderAll(worldStub)); // kod niepotrzebny w teście, służy tylko dla naszej pewności

        Runner runner = new Runner(worldStub);//obiekt,który  uruchomi nasza grę


        List<Environment.Action> actions = Arrays.asList(GO_FORWARD, GO_FORWARD, GO_FORWARD, GRAB, TURN_LEFT, TURN_LEFT, GO_FORWARD, GO_FORWARD, GO_FORWARD);
        Iterator<Environment.Action> actionIterator = actions.iterator();

        Agent agent = new Agent() {
            @Override
            public Environment.Action getAction(Player player) {

                return actionIterator.next();
            }

            @Override
            public void beforeAction(Player player) {
                //noop
            }

            @Override
            public void afterAction(Player player) {
                System.out.println("----------------------"); // kod niepotrzebny w teście, służy tylko do podejrzenia przebiegu testu
                System.out.println(ConsoleRenderer.renderAll(worldStub)); // kod niepotrzebny w teście, służy tylko do podejrzenia przebiegu testu
            }
        };

        runner.run(agent);

        assertThat(worldStub.getResult()).isEqualTo(Environment.Result.WIN);
        assertThat(worldStub.getPlayer().isAlive()).isEqualTo(true);
        assertThat(worldStub.getPlayer().hasGold()).isEqualTo(true);

    }

    @Test
    public void testPitPath() throws InterruptedException {

        World worldStub = new World(4, 4);

        worldStub.setPit(3,3);
        worldStub.initialize();

        System.out.println(ConsoleRenderer.renderAll(worldStub)); // kod niepotrzebny w teście, służy tylko dla naszej pewności

        Runner runner = new Runner(worldStub);//obiekt,który  uruchomi nasza grę


        List<Environment.Action> actions = Arrays.asList(GO_FORWARD, GO_FORWARD, GO_FORWARD);
        Iterator<Environment.Action> actionIterator = actions.iterator();

        Agent agent = new Agent() {
            @Override
            public Environment.Action getAction(Player player) {

                return actionIterator.next();
            }

            @Override
            public void beforeAction(Player player) {
                //noop
            }

            @Override
            public void afterAction(Player player) {
                System.out.println("----------------------");
                System.out.println(ConsoleRenderer.renderAll(worldStub));
            }
        };

        runner.run(agent);

        assertThat(worldStub.getResult()).isEqualTo(Environment.Result.LOOSE);
        assertThat(worldStub.getPlayer().isDead()).isEqualTo(true);

    }

    @Test
    public void testWumpusPath() throws InterruptedException {

        World worldStub = new World(4, 4);

        worldStub.setWumpus(3,3);
        worldStub.initialize();

        System.out.println(ConsoleRenderer.renderAll(worldStub));

        Runner runner = new Runner(worldStub);


        List<Environment.Action> actions = Arrays.asList(GO_FORWARD, GO_FORWARD, GO_FORWARD);
        Iterator<Environment.Action> actionIterator = actions.iterator();

        Agent agent = new Agent() {
            @Override
            public Environment.Action getAction(Player player) {

                return actionIterator.next();
            }

            @Override
            public void beforeAction(Player player) {
                //noop
            }

            @Override
            public void afterAction(Player player) {
                System.out.println("----------------------");
                System.out.println(ConsoleRenderer.renderAll(worldStub));
            }
        };

        runner.run(agent);

        assertThat(worldStub.getResult()).isEqualTo(Environment.Result.LOOSE);
        assertThat(worldStub.getPlayer().isDead()).isEqualTo(true);
    }

    @Test
    public void testFeelStenchPath() throws InterruptedException {

        World worldStub = new World(4, 4);

        worldStub.setWumpus(3,3);
        worldStub.initialize();

        System.out.println(ConsoleRenderer.renderAll(worldStub));

        Runner runner = new Runner(worldStub);


        List<Environment.Action> actions = Arrays.asList(GO_FORWARD, GO_FORWARD);
        Iterator<Environment.Action> actionIterator = actions.iterator();

        Agent agent = new Agent() {
            @Override
            public Environment.Action getAction(Player player) {

                return actionIterator.next();
            }

            @Override
            public void beforeAction(Player player) {
                //noop
            }

            @Override
            public void afterAction(Player player) {
                System.out.println("----------------------");
                System.out.println(ConsoleRenderer.renderAll(worldStub));
            }
        };

        runner.run(agent);

      //  assertThat(worldStub.getResult()).isEqualTo(Environment.Result.IN_GAME);
      //  assertThat(worldStub.getPlayer().isAlive()).isEqualTo(true);
        assertThat(worldStub.getPlayer().getPerceptions().get(2)).isEqualTo(true);
    }


}
