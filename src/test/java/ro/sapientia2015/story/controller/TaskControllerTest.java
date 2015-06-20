package ro.sapientia2015.story.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import ro.sapientia2015.story.config.UnitTestContext;
import ro.sapientia2015.story.dto.TaskDTO;
import ro.sapientia2015.story.model.Task;
import ro.sapientia2015.story.service.TaskService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Izabella
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UnitTestContext.class})
public class TaskControllerTest {

    private TaskController controller;

    private TaskService serviceMock;

    @Resource
    private Validator validator;
    
    @Before
    public void setUp() {
        controller = new TaskController();

        serviceMock = mock(TaskService.class);
        ReflectionTestUtils.setField(controller, "service", serviceMock);
    }

    @Test
    public void taskList1() {
        BindingAwareModelMap model = new BindingAwareModelMap();

        String view = controller.listTasks(model);

        assertEquals("task/list", view);
     }

    @Test
    public void taskList2() {
        BindingAwareModelMap model = new BindingAwareModelMap();

        controller.listTasks(model);

        List<Task> tasks =  (List<Task>)model.asMap().get("tasks");
        assertNotNull(tasks);
     }

    @Test
    public void taskList3() {
        BindingAwareModelMap model = new BindingAwareModelMap();

        controller.listTasks(model);

        verify(serviceMock, times(1)).findAll();
        verifyNoMoreInteractions(serviceMock);
     }
    
    @Test
    public void taskList4() {
        BindingAwareModelMap model = new BindingAwareModelMap();       
        List<Task> list = new ArrayList<Task>();
        list.add(new Task());
        when(serviceMock.findAll()).thenReturn(list);

        controller.listTasks(model);
        
        List<Task> tasks =  (List<Task>)model.asMap().get("tasks");

        assertEquals(1, tasks.size());
     }
    
    private BindingResult bindAndValidate(HttpServletRequest request, Object formObject) {
        WebDataBinder binder = new WebDataBinder(formObject);
        binder.setValidator(validator);
        binder.bind(new MutablePropertyValues(request.getParameterMap()));
        binder.getValidator().validate(binder.getTarget(), binder.getBindingResult());
        return binder.getBindingResult();
    }

    @Test
    public void add() {
        TaskDTO formObject = new TaskDTO();

        formObject.setTitle("title");
        formObject.setDescription("desc");
        formObject.setStatus("status");
        
        Task model = Task.getBuilder("title")
        		.description("desc").status("status").build();
        
        when(serviceMock.add(formObject)).thenReturn(model);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest("POST", "/task/add");
        BindingResult result = bindAndValidate(mockRequest, formObject);

        RedirectAttributesModelMap attributes = new RedirectAttributesModelMap();


        String view = controller.add(formObject, result, attributes);

        verify(serviceMock, times(1)).add(formObject);
        verifyNoMoreInteractions(serviceMock);

        String expectedView = "redirect:/task/list";
        assertEquals(expectedView, view);
    }

    
    @Test
    public void addEmptyTask1() {
    	
    	TaskDTO formObject = new TaskDTO();

        formObject.setTitle("");
        formObject.setDescription("");
        formObject.setStatus("");
       
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("POST", "/task/add");
        BindingResult result = bindAndValidate(mockRequest, formObject);

        RedirectAttributesModelMap attributes = new RedirectAttributesModelMap();

        String view = controller.add(formObject, result, attributes);

        assertEquals(TaskController.VIEW_ADD, view);
    }
    
    @Test
    public void addTooLongStoryTitle() {
    	
    	TaskDTO formObject = new TaskDTO();

        formObject.setTitle("123456789123456789123456789");
        formObject.setDescription("");
        formObject.setStatus("");
       
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("POST", "/task/add");
        BindingResult result = bindAndValidate(mockRequest, formObject);

        RedirectAttributesModelMap attributes = new RedirectAttributesModelMap();

        String view = controller.add(formObject, result, attributes);

        assertEquals(TaskController.VIEW_ADD, view);
    }
    
    
    
    
}
