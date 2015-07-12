package com.betvictor.exercises.actionmonitor.model;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.Date;

/**
 * A simple entity for demonstration purposes.
 */
@Entity
@SequenceGenerator(name = "magicIdGenerator")
public class Magic implements HasId {

    @Id
    @GeneratedValue(generator = "magicIdGenerator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Basic
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;


    public Magic() {
    }

    public Magic(String description) {
        this.description = description;
    }


    public boolean isNew() {
        return id == null;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, description, createdAt, updatedAt);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Magic other = (Magic) obj;
        return Objects.equal(this.id, other.id)
                && Objects.equal(this.description, other.description)
                && Objects.equal(this.createdAt, other.createdAt)
                && Objects.equal(this.updatedAt, other.updatedAt);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("description", description)
                .add("createdAt", createdAt)
                .add("updatedAt", updatedAt)
                .toString();
    }
}
