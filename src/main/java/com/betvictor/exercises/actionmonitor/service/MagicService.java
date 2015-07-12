package com.betvictor.exercises.actionmonitor.service;

import com.betvictor.exercises.actionmonitor.model.Action;
import com.betvictor.exercises.actionmonitor.model.Magic;
import com.betvictor.exercises.actionmonitor.queue.ActionQueueHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A service to manage the {@link Magic} entities in the database exposing a simple CRUD interface.
 * After a creation, an update or a deletion an {@link Action} gets sent to the UI.
 */
@Service
public class MagicService {

    private static final Logger LOG = LoggerFactory.getLogger(MagicService.class);

    private final MagicRepository repository;
    private final ActionQueueHandler queueHandler;


    @Autowired
    public MagicService(MagicRepository repository, ActionQueueHandler queueHandler) {
        this.repository = repository;
        this.queueHandler = queueHandler;
    }


    /**
     * Retrieves all the entities.
     *
     * @return every {@link Magic} entity in the database ordered by the ID in a descending order.
     */
    @Transactional(readOnly = true)
    public Iterable<Magic> findAll() {
        return repository.findAll(new Sort(Sort.Direction.DESC, "id"));
    }

    /**
     * Retrieves a {@link} Magic by the gived ID.
     *
     * @param id the given ID
     * @return the entity or {@code null} if it does not exist
     */
    @Transactional(readOnly = true)
    public Magic findById(Long id) {
        return repository.findOne(id);
    }

    /**
     * Creates a new {@link Magic} with the given description.
     *
     * @param description the description
     */
    @Transactional
    public void createNew(String description) {
        Magic magic = save(new Magic(description));
        LOG.info("A new Magic({}) has been created.", magic);
    }

    /**
     * Updates the description of a {@link Magic} with the given ID.
     *
     * @param id          the ID of the entity
     * @param description the new description
     */
    @Transactional
    public void update(Long id, String description) {
        Magic magic = findById(id);
        magic.setDescription(description);
        save(magic);
        LOG.info("A Magic({}) has been updated.", magic);
    }

    private Magic save(Magic magic) {
        Action.Type actionType;
        if (magic.isNew()) {
            actionType = Action.Type.INSERT;
        } else {
            actionType = Action.Type.UPDATE;
        }

        Magic saved = repository.save(magic);
        queueHandler.enqueue(new Action<>(saved, actionType));
        return saved;
    }

    /**
     * Deletes a {@link Magic} entity by the ID
     *
     * @param id the ID
     */
    @Transactional
    public void deleteById(Long id) {
        Magic magic = repository.findOne(id);
        repository.delete(magic);
        queueHandler.enqueue(new Action<>(magic, Action.Type.DELETE));
        LOG.info("A Magic({}) has been deleted.", magic);
    }
}
