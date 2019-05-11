package wumpus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MockShowCaseAgentTest {

    Agent agentMock;

    @BeforeEach
    public void setUp(){
        agentMock = Mockito.mock(Agent.class);

        // Jeśli ktoś wywoła na mocku metodę getAction z dowolonym paramtrem zwróć Akcję idź prosto
        when(agentMock.getAction(any())).thenReturn(Environment.Action.GO_FORWARD);
    }

    // Metoda nic nie testuje, ma tylko zaprezentować jak działa mock
    // metoda getAction zawsze bedzie zwracać to samo
    @Test
    public void showCase(){

        System.out.println(agentMock.getAction(null));
    }


}
