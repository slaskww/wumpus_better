package controls;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import wumpus.Environment;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ConsoleCommandParserTest {

    CommandParser<String> commandParser = ConsoleCommandParser.INSTANCE;

    /**
     * Sprawdzamy czy command parser poprawnie parsuje komendy
     * Komendy są dodawane w konstruktorze więc nie musimy pisać kodu który je dodaje, możemy od razu sprawdzić
     * czy wszystko jest zgodnie z założeniami
     */
    @Test
    void testCommands() {
        assertThat(commandParser.getAction("f").get()).isEqualTo(Environment.Action.GO_FORWARD);
        assertThat(commandParser.getAction("l").get()).isEqualTo(Environment.Action.TURN_LEFT);
        assertThat(commandParser.getAction("r").get()).isEqualTo(Environment.Action.TURN_RIGHT);
        assertThat(commandParser.getAction("g").get()).isEqualTo(Environment.Action.GRAB);
        assertThat(commandParser.getAction("s").get()).isEqualTo(Environment.Action.SHOOT_ARROW);
        assertThat(commandParser.getAction("exit").get()).isEqualTo(Environment.Action.EXIT);
    }

    /**
     * Najprostsze rozwiązanie, jeśli w Optional mamy null metoda isPresent zwróci false
     */
    @Test
    void testNotRegistredCommand() {
        assertThat(commandParser.getAction("some_name_that_not_exists_as_command_name").isPresent()).isEqualTo(false);
    }

    /**
     * Inne rozwiązanie wykorzystujące AssertJ.
     * Jeśli wywołamy metodę .get() na obiekcie Optional który ma wartość null zostanie rzucony wyjątek NoSuchElementException
     */
    @Test()
    void testNotRegistredCommandExceptionWithAssertJ() throws Exception {

        Throwable thrown = catchThrowable(() -> {
            commandParser.getAction("some_name_that_not_exists_as_command_name").get();
        });

        assertThat(thrown).isInstanceOf(NoSuchElementException.class)
                .hasNoCause()
                .hasStackTraceContaining("NoSuchElementException");
    }


    /**
     * Inne rozwiązanie z wykorzystaniem JUnit
     */
    @Test()
    void testNotRegistredCommandExceptionWithJUnit() {

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            commandParser.getAction("some_name_that_not_exists_as_command_name").get();
        });

    }
}
