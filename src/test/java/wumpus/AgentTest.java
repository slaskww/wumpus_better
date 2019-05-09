package wumpus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class AgentTest {

    int maximumSteps = 10;

    Agent agentMock;

    World worldMock;

    Player playerMock;

    @BeforeEach
    public void setUp(){
        agentMock = Mockito.mock(Agent.class);

        when(agentMock.getAction(any())).thenReturn(Environment.Action.GO_FORWARD);

        playerMock = Mockito.mock(Player.class);
        // jakie metody powinien byc zamokowane do tego testu w obiekcie Player?

        worldMock = Mockito.mock(World.class);
        when(worldMock.getPlayer()).thenReturn(playerMock);
        // jakie dwie metody przydzadza nam sie w worldMock?
    }

    @Test
    public void testNumberOfMethodCalls() throws InterruptedException {
        Runner runner = new Runner(worldMock);
        runner.run(agentMock);

        Mockito.verify(agentMock, times(maximumSteps)).getAction(any());
    }
}
