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
        when(playerMock.isAlive()).thenReturn(true);
        when(playerMock.getLastAction()).thenReturn(Environment.Action.GO_FORWARD);

        worldMock = Mockito.mock(World.class);
        when(worldMock.getPlayer()).thenReturn(playerMock);
        when(worldMock.getResult()).thenReturn(Environment.Result.IN_GAME);
    }

    @Test
    public void testNumberOfMethodCalls() throws InterruptedException {
        Runner runner = new Runner(worldMock, maximumSteps);
        runner.run(agentMock);

        Mockito.verify(agentMock, times(maximumSteps)).getAction(any());
    }
}
