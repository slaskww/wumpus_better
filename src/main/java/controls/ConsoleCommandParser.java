package controls;

import wumpus.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Java. Efektywne Programowanie. Temat 3. Wymuszanie właściwości singleton za pomocą prywatnego kontruktora
 */
public class ConsoleCommandParser implements CommandParser<String> {

    private Map<String, Environment.Action> actionMap = new HashMap<String, Environment.Action>();

    public static final ConsoleCommandParser INSTANCE = new ConsoleCommandParser();

    private ConsoleCommandParser() {
        add("f", Environment.Action.GO_FORWARD);
        add("l", Environment.Action.TURN_LEFT);
        add("r", Environment.Action.TURN_RIGHT);
        add("g", Environment.Action.GRAB);
        add("s", Environment.Action.SHOOT_ARROW);
        add("exit", Environment.Action.EXIT);
    }

    private void add(String command, Environment.Action action) {
        actionMap.put(command, action);
    }

    /**
     *
     * @param command as string
     * @return Action for this command. If command doesn't match any action will return optional with null
     */
    public Optional<Environment.Action> getAction(String command) {
        return Optional.ofNullable(actionMap.get(command));
    }


}
