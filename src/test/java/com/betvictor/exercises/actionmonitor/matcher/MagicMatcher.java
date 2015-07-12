package com.betvictor.exercises.actionmonitor.matcher;

import com.betvictor.exercises.actionmonitor.model.Magic;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.CoreMatchers.is;

public class MagicMatcher extends TypeSafeDiagnosingMatcher<Magic> {

    private Matcher<Long> id;
    private Matcher<String> description;

    private MagicMatcher() {
    }


    @Factory
    public static MagicMatcher magic(Long id, String description) {
        MagicMatcher matcher = new MagicMatcher();
        matcher.id = is(id);
        matcher.description = is(description);
        return matcher;
    }


    @Override
    protected boolean matchesSafely(Magic magic, Description description) {
        return id.matches(magic.getId()) && this.description.matches(magic.getDescription());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Magic{id ").appendDescriptionOf(id)
                .appendText(", description ").appendDescriptionOf(this.description)
                .appendText("}");
    }
}
