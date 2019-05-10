package wumpus;

import org.junit.jupiter.api.Test;
import render.ConsoleRenderer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static wumpus.Environment.Action.*;

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

        Runner runner = new Runner(worldStub);

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
    public void testKillByPit() throws InterruptedException {
        World worldStub = new World(4, 4);
        worldStub.setPit(3,3);
        worldStub.initialize();

        Runner runner = new Runner(worldStub);

        List<Environment.Action> actions = Arrays.asList(GO_FORWARD, GO_FORWARD, GO_FORWARD);

        Agent agent = new Agent() {
            @Override
            public Environment.Action getAction(Player player) {
                Iterator<Environment.Action> actionIterator = actions.iterator();
                return actionIterator.next();
            }

            @Override
            public void beforeAction(Player player) {
                //noop
            }

            @Override
            public void afterAction(Player player) {
                //noop
            }
        };

        runner.run(agent);

        assertThat(worldStub.getResult()).isEqualTo(Environment.Result.LOOSE);
        assertThat(worldStub.getPlayer().isAlive()).isEqualTo(false);
        assertThat(worldStub.getPlayer().isDead()).isEqualTo(true);

    }

    // Implementacja agenta do testów jest bardzo podobna w każdym teście. Ponieważ korzystamy z niej kilkukrotnie
    // warto wydzielić ją do osobnej klasy, od teraz korzystamy z implementacji TestAgent
    // starsze testy zostawiam ze starą implementacją w celach pokazowych
    @Test
    public void testKillByWumpus() throws InterruptedException {

        World worldStub = new World(4, 4);
        worldStub.setWumpus(3,3);
        worldStub.initialize();

        Runner runner = new Runner(worldStub);

        List<Environment.Action> actions = Arrays.asList(GO_FORWARD, GO_FORWARD, GO_FORWARD);

        Agent agent = new TestAgent(actions);

        runner.run(agent);

        assertThat(worldStub.getResult()).isEqualTo(Environment.Result.LOOSE);
        assertThat(worldStub.getPlayer().isAlive()).isEqualTo(false);
        assertThat(worldStub.getPlayer().isDead()).isEqualTo(true);

    }

    // W momencie gdy na polu obok jest wróg (tylko w liniach prostych, nie działa po przekątnych) w obiekcie gracza
    // dodawane są jego odczucia - liste percpetion w klasie Player
    // celem tego testu jest sprawdzenie czy odczucia są odpowiednie do pól
    // co ważne perception jest zapamiętywane tylko dla ostatniego pola na kórym byliśmy!
    @Test
    public void testMessageBreeze() throws InterruptedException {

        // w tym teście będziemy chcieli wykonać tylko 3 kroki
        // nie potrzebujemy wygrać/przegrać
        // po 3 krokach będziemy w stanie sprawdzić wszystko co nas interesuje czyli odczucia gracza
        int steps = 1;

        World worldStub = new World(4, 4);
        worldStub.setPit(1,2);
        worldStub.initialize();

        System.out.println(ConsoleRenderer.renderAll(worldStub)); // kod niepotrzebny w teście, służy tylko dla naszej pewności

        Runner runner = new Runner(worldStub, steps);

        List<Environment.Action> actions = Collections.singletonList(GO_FORWARD);
        Agent agent = new TestAgent(actions);

        runner.run(agent);

        Player player = worldStub.getPlayer();
        List<Environment.Perception> perceptions = player.getPerceptions();

        assertThat(perceptions.get(0)).isEqualTo(Environment.Perception.BREEZE);

        System.out.println(ConsoleRenderer.renderAll(worldStub)); // kod niepotrzebny w teście, służy tylko dla naszej pewności

    }

    @Test
    public void testMessageStench() throws InterruptedException {

        // w tym teście będziemy chcieli wykonać tylko 3 kroki
        // nie potrzebujemy wygrać/przegrać
        // po 3 krokach będziemy w stanie sprawdzić wszystko co nas interesuje czyli odczucia gracza
        int steps = 2;

        World worldStub = new World(4, 4);
        worldStub.setWumpus(2,2);
        worldStub.initialize();

        System.out.println(ConsoleRenderer.renderAll(worldStub)); // kod niepotrzebny w teście, służy tylko dla naszej pewności

        Runner runner = new Runner(worldStub, steps);

        List<Environment.Action> actions = Arrays.asList(GO_FORWARD, GO_FORWARD);
        Agent agent = new TestAgent(actions);

        runner.run(agent);

        Player player = worldStub.getPlayer();
        List<Environment.Perception> perceptions = player.getPerceptions();

        assertThat(perceptions.get(0)).isEqualTo(Environment.Perception.STENCH);

        System.out.println(ConsoleRenderer.renderAll(worldStub)); // kod niepotrzebny w teście, służy tylko dla naszej pewności

    }
}
