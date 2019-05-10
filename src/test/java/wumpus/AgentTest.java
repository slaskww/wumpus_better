package wumpus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class AgentTest {

    private int maximumSteps = 10;

    private Agent agentMock;

    private World worldMock;

    private Player playerMock;

    @BeforeEach
    public void setUp(){
        agentMock = Mockito.mock(Agent.class);

        when(agentMock.getAction(any())).thenReturn(Environment.Action.GO_FORWARD);

        playerMock = Mockito.mock(Player.class);
        // jakie metody powinien byc zamokowane do tego testu w obiekcie Player?

        worldMock = Mockito.mock(World.class);
        // jakie jeszcze metoda przydza nam sie w worldMock?
    }

    @Test
    public void testNumberOfMethodCalls() throws InterruptedException {
        Runner runner = new Runner(worldMock, maximumSteps);
        runner.run(agentMock, playerMock);

        Mockito.verify(agentMock, times(maximumSteps)).getAction(any());
    }
}
