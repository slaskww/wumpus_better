package controls;

import wumpus.Environment;

import java.util.Optional;

public interface CommandParser<T> {

    Optional<Environment.Action> getAction(T command);
}
