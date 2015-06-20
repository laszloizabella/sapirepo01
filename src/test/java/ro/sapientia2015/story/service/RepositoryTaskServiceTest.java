package ro.sapientia2015.story.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import ro.sapientia2015.story.StoryTestUtil;
import ro.sapientia2015.story.TaskTestUtil;
import ro.sapientia2015.story.dto.TaskDTO;
import ro.sapientia2015.story.exception.NotFoundException;
import ro.sapientia2015.story.model.Task;
import ro.sapientia2015.story.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Izabella
 */
public class RepositoryTaskServiceTest {

    private RepositoryTaskService service;

    private TaskRepository repositoryMock;

    @Before
    public void setUp() {

    	service = new RepositoryTaskService();
        repositoryMock = mock(TaskRepository.class);
        ReflectionTestUtils.setField(service, "repository", repositoryMock);
    }

    @Test
    public void findAll() {
        List<Task> models = new ArrayList<Task>();
        when(repositoryMock.findAll()).thenReturn(models);

        List<Task> actual = service.findAll();

        verify(repositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(repositoryMock);

        assertEquals(models, actual);
    }


    @Test
    public void findById() throws NotFoundException {
    	Task model = TaskTestUtil.createModel(TaskTestUtil.ID, TaskTestUtil.DESCRIPTION, TaskTestUtil.TITLE, TaskTestUtil.STATUS);
        when(repositoryMock.findOne(StoryTestUtil.ID)).thenReturn(model);

        Task actual = service.findById(TaskTestUtil.ID);

        verify(repositoryMock, times(1)).findOne(TaskTestUtil.ID);
        verifyNoMoreInteractions(repositoryMock);

        assertEquals(model, actual);
    }

    @Test(expected = NotFoundException.class)
    public void findByIdWhenIsNotFound() throws NotFoundException {
        when(repositoryMock.findOne(TaskTestUtil.ID)).thenReturn(null);

        service.findById(TaskTestUtil.ID);

        verify(repositoryMock, times(1)).findOne(StoryTestUtil.ID);
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void update() throws NotFoundException {
        TaskDTO dto = TaskTestUtil.createFormObject(TaskTestUtil.ID, TaskTestUtil.DESCRIPTION_UPDATED, TaskTestUtil.TITLE_UPDATED, TaskTestUtil.STATUS_UPDATED);
        Task model = TaskTestUtil.createModel(TaskTestUtil.ID, TaskTestUtil.DESCRIPTION, TaskTestUtil.TITLE, TaskTestUtil.STATUS);
        when(repositoryMock.findOne(dto.getId())).thenReturn(model);

        Task actual = service.update(dto);

        verify(repositoryMock, times(1)).findOne(dto.getId());
        verifyNoMoreInteractions(repositoryMock);

        assertEquals(dto.getId(), actual.getId());
        assertEquals(dto.getDescription(), actual.getDescription());
        assertEquals(dto.getStatus(), actual.getStatus());
        assertEquals(dto.getTitle(), actual.getTitle());
    }

    @Test(expected = NotFoundException.class)
    public void updateWhenIsNotFound() throws NotFoundException {
    	TaskDTO dto = TaskTestUtil.createFormObject(TaskTestUtil.ID, TaskTestUtil.DESCRIPTION_UPDATED, TaskTestUtil.TITLE_UPDATED, TaskTestUtil.STATUS_UPDATED);
        when(repositoryMock.findOne(dto.getId())).thenReturn(null);

        service.update(dto);

        verify(repositoryMock, times(1)).findOne(dto.getId());
        verifyNoMoreInteractions(repositoryMock);
    }
    
    
 }
