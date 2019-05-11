package wumpus;

import java.util.Iterator;
import java.util.List;

public class TestAgent implements Agent {

    Iterator<Environment.Action> actionIterator;

    public TestAgent(List<Environment.Action> actions) {
        this.actionIterator = actions.iterator();
    }

    @Override
    public Environment.Action getAction(Player player) {
        return actionIterator.next();
    }

    @Override
    public void beforeAction(Player player) {

    }

    @Override
    public void afterAction(Player player) {

    }
}