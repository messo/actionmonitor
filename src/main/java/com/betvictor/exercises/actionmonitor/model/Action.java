package com.betvictor.exercises.actionmonitor.model;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents an action which describes what happened to an entity. It gets sent to the UI.
 */
public class Action<T extends HasId> implements Serializable {

    public enum Type {
        INSERT, UPDATE, DELETE
    }


    /**
     * The ID of the entity
     */
    public long id;
    /**
     * The type of the action
     */
    public Type actionType;
    /**
     * The timestamp of the action
     */
    public Date timestamp;


    public Action() {
    }

    public Action(T entity, Type actionType) {
        this.id = entity.getId();
        this.actionType = actionType;
        this.timestamp = new Date();
    }

}
