package com.betvictor.exercises.actionmonitor.service;

import com.betvictor.exercises.actionmonitor.model.Action;
import com.betvictor.exercises.actionmonitor.model.Magic;
import com.betvictor.exercises.actionmonitor.queue.ActionQueueHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import java.util.LinkedList;
import java.util.List;

import static com.betvictor.exercises.actionmonitor.matcher.ActionMatcher.actionWithoutTimestamp;
import static com.betvictor.exercises.actionmonitor.matcher.MagicMatcher.magic;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MagicServiceTest {

    @Mock
    private MagicRepository repository;
    @Mock
    private ActionQueueHandler queueHandler;


    private MagicService service;

    @Before
    public void before() {
        service = new MagicService(repository, queueHandler);
    }


    @Test
    public void findAll() {
        // given
        List<Magic> expectedMagics = new LinkedList<>();
        when(repository.findAll(new Sort(Sort.Direction.DESC, "id"))).thenReturn(expectedMagics);

        // when
        Iterable<Magic> actualMagics = service.findAll();

        // then
        Assert.assertEquals(expectedMagics, actualMagics);
    }

    @Test
    public void findById() {
        // given
        Magic expectedMagic = new Magic();
        when(repository.findOne(12L)).thenReturn(expectedMagic);

        // when
        Magic actualMagic = service.findById(12L);

        // then
        Assert.assertEquals(expectedMagic, actualMagic);
    }

    @Test
    public void createNew() {
        // given
        final String description = "My description";
        Magic expectedMagic = new Magic(description);
        expectedMagic.setId(5L);
        when(repository.save(new Magic(description))).thenReturn(expectedMagic);

        // when
        service.createNew(description);

        // then
        verify(repository, only()).save(new Magic(description));
        verify(queueHandler, only()).enqueue(argThat(actionWithoutTimestamp(5L, Action.Type.INSERT)));
    }

    @Test
    public void update() {
        // given
        Magic oldMagic = new Magic("Old description");
        oldMagic.setId(5L);
        when(repository.findOne(5L)).thenReturn(oldMagic);

        final String description = "New description";
        Magic expectedMagic = new Magic(description);
        expectedMagic.setId(5L);
        when(repository.save(argThat(magic(5L, description)))).thenReturn(expectedMagic);

        // when
        service.update(5L, description);

        // then
        verify(repository).findOne(5L);
        verify(repository).save(expectedMagic);
        verifyNoMoreInteractions(repository);
        verify(queueHandler, only()).enqueue(argThat(actionWithoutTimestamp(5L, Action.Type.UPDATE)));
    }

    @Test
    public void delete() {
        // given
        Magic magic = new Magic("Description");
        magic.setId(5L);
        when(repository.findOne(5L)).thenReturn(magic);

        // when
        service.deleteById(5L);

        // then
        verify(repository).findOne(5L);
        verify(repository).delete(magic);
        verifyNoMoreInteractions(repository);
        verify(queueHandler, only()).enqueue(argThat(actionWithoutTimestamp(5L, Action.Type.DELETE)));
    }
}
