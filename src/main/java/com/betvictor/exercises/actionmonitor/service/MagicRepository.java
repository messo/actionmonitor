package com.betvictor.exercises.actionmonitor.service;

import com.betvictor.exercises.actionmonitor.model.Magic;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A simple repository for {@link Magic} entities
 */
interface MagicRepository extends JpaRepository<Magic, Long> {
}
