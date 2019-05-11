package controls;


import org.junit.jupiter.api.Test;
import wumpus.Environment;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsoleCommandParserTest {

    CommandParser<String> commandParser = ConsoleCommandParser.INSTANCE;

    @Test
    void testCommands(){

        //test  if equal to
        assertThat(commandParser.getAction("f").get()).isEqualTo(Environment.Action.GO_FORWARD);
        assertThat(commandParser.getAction("l").get()).isEqualTo(Environment.Action.TURN_LEFT);
        assertThat(commandParser.getAction("r").get()).isEqualTo(Environment.Action.TURN_RIGHT);
        assertThat(commandParser.getAction("g").get()).isEqualTo(Environment.Action.GRAB);
        assertThat(commandParser.getAction("exit").get()).isEqualTo(Environment.Action.EXIT);
    }

    @Test
    void testNotRegistredCommand(){

        //test if not equal to
        assertThat(commandParser.getAction("test").isPresent()).isFalse();
        assertThat(commandParser.getAction("1111").isPresent()).isFalse();

    }
}
