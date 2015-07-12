package com.betvictor.exercises.actionmonitor.matcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.springframework.messaging.Message;

import static org.hamcrest.CoreMatchers.is;

public class MessageMatcher<T> extends TypeSafeDiagnosingMatcher<Message<T>> {

    private Matcher<T> payload;

    private MessageMatcher() {
    }


    @Factory
    public static <T> MessageMatcher<T> message(T payload) {
        MessageMatcher<T> matcher = new MessageMatcher<>();
        matcher.payload = is(payload);
        return matcher;
    }


    @Override
    protected boolean matchesSafely(Message<T> message, Description description) {
        return payload.matches(message.getPayload());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Message{payload ").appendDescriptionOf(payload).appendText("}");
    }
}
