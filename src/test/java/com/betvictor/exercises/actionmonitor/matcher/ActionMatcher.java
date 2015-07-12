package com.betvictor.exercises.actionmonitor.matcher;

import com.betvictor.exercises.actionmonitor.model.Action;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Date;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;

public class ActionMatcher extends TypeSafeDiagnosingMatcher<Action> {

    private Matcher<Long> id;
    private Matcher<Action.Type> actionType;
    private Matcher<Date> timestamp;

    private ActionMatcher() {
    }


    @Factory
    public static ActionMatcher actionWithoutTimestamp(Long id, Action.Type actionType) {
        ActionMatcher actionMatcher = new ActionMatcher();
        actionMatcher.id = is(id);
        actionMatcher.actionType = is(actionType);
        actionMatcher.timestamp = any(Date.class);
        return actionMatcher;
    }


    @Override
    protected boolean matchesSafely(Action action, Description description) {
        return id.matches(action.id) && actionType.matches(action.actionType) && timestamp.matches(action.timestamp);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Action{id ").appendDescriptionOf(id)
                .appendText(", actionType ").appendDescriptionOf(actionType)
                .appendText(", timestamp ").appendDescriptionOf(timestamp)
                .appendText("}");
    }
}
