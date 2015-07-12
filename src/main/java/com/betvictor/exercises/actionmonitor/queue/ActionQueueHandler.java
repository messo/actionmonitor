package com.betvictor.exercises.actionmonitor.queue;

import com.betvictor.exercises.actionmonitor.model.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

/**
 * The queue handler component which is responsible for enqueuing and dequeuing messages from the ActiveMQ queue.
 * For the details please see the content of {@code integration-context.xml}.
 */
@Component
public class ActionQueueHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ActionQueueHandler.class);

    private final SimpMessageSendingOperations msgTemplate;
    private final MessageChannel jmsOutChannel;


    @Autowired
    public ActionQueueHandler(SimpMessageSendingOperations msgTemplate, MessageChannel jmsOutChannel) {
        this.msgTemplate = msgTemplate;
        this.jmsOutChannel = jmsOutChannel;
    }


    /**
     * Dequeues an {@link Action} from the ActiveMQ queue and sends to the client.
     *
     * @param action the dequeued action
     */
    @ServiceActivator
    public void dequeue(Action<?> action) {
        LOG.info("An action has been dequeued: {}", action);
        msgTemplate.convertAndSend("/topic/actions", action);
    }

    /**
     * Enqueues an {@link Action} to the ActiveMQ queue.
     *
     * @param action the action to be enqueued
     */
    public void enqueue(Action<?> action) {
        LOG.info("An action has been queued: {}", action);
        jmsOutChannel.send(MessageBuilder.withPayload(action).build());
    }
}
