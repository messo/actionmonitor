package com.betvictor.exercises.actionmonitor.queue;

import com.betvictor.exercises.actionmonitor.model.Action;
import com.betvictor.exercises.actionmonitor.model.Magic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import static com.betvictor.exercises.actionmonitor.matcher.ActionMatcher.actionWithoutTimestamp;
import static com.betvictor.exercises.actionmonitor.matcher.MessageMatcher.message;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ActionQueueHandlerTest {

    @Mock
    private SimpMessageSendingOperations msgTemplate;
    @Mock
    private MessageChannel jmsOutChannel;


    private ActionQueueHandler queueHandler;

    @Before
    public void before() {
        queueHandler = new ActionQueueHandler(msgTemplate, jmsOutChannel);
    }

    @Test
    public void enqueue() {
        // given
        Magic magic = new Magic();
        magic.setId(6L);
        Action<Magic> action = new Action<>(magic, Action.Type.DELETE);

        // when
        queueHandler.enqueue(action);

        // then
        verify(jmsOutChannel, only()).send(argThat(message(action)));
    }

    @Test
    public void dequeue() {
        // given
        Magic magic = new Magic();
        magic.setId(6L);
        Action<Magic> action = new Action<>(magic, Action.Type.INSERT);

        // when
        queueHandler.dequeue(action);

        // then
        verify(msgTemplate, only()).convertAndSend(eq("/topic/actions"), argThat(actionWithoutTimestamp(6L, Action.Type.INSERT)));
    }
}
