package controls;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import wumpus.Environment;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsoleCommandParserTest {

    CommandParser<String> commandParser = ConsoleCommandParser.INSTANCE;

    @Test
    void testCommands(){
        assertThat(commandParser.getAction("f").get()).isEqualTo(Environment.Action.GO_FORWARD);
        assertThat(commandParser.getAction("l").get()).isEqualTo(Environment.Action.TURN_LEFT);
        assertThat(commandParser.getAction("r").get()).isEqualTo(Environment.Action.TURN_RIGHT);
        assertThat(commandParser.getAction("g").get()).isEqualTo(Environment.Action.GRAB);
        assertThat(commandParser.getAction("s").get()).isEqualTo(Environment.Action.SHOOT_ARROW);
        assertThat(commandParser.getAction("exit").get()).isEqualTo(Environment.Action.EXIT);

    }

    /**
     * The easiest solution
     */
    @Test
    void testNotRegistredCommand(){
        assertThat(commandParser.getAction("some_name_that_not_exists_as_command_name").isPresent()).isEqualTo(false);
    }

    /**
     * Solution with Junit5
     */
    @Test()
    void testNotRegistredCommand2(){

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            commandParser.getAction("some_name_that_not_exists_as_command_name").get();
        });

    }
}
