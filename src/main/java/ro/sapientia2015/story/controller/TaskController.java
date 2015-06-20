package ro.sapientia2015.story.controller;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ro.sapientia2015.story.dto.TaskDTO;
import ro.sapientia2015.story.exception.NotFoundException;
import ro.sapientia2015.story.model.Sprint;
import ro.sapientia2015.story.model.Task;
import ro.sapientia2015.story.service.TaskService;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.List;
import java.util.Locale;

/**
 * @author Izabella
 */
@Controller
@SessionAttributes("task")
public class TaskController {

    protected static final String FEEDBACK_MESSAGE_KEY_ADDED = "feedback.message.task.added";
    protected static final String FEEDBACK_MESSAGE_KEY_UPDATED = "feedback.message.task.updated";
    protected static final String FEEDBACK_MESSAGE_KEY_DELETED = "feedback.message.task.deleted";

    protected static final String FLASH_MESSAGE_KEY_ERROR = "errorMessage";
    protected static final String FLASH_MESSAGE_KEY_FEEDBACK = "feedbackMessage";

    protected static final String MODEL_ATTRIBUTE = "task";
    protected static final String MODEL_ATTRIBUTE_LIST = "tasks";

    protected static final String PARAMETER_ID = "id";

    protected static final String REQUEST_MAPPING_LIST = "/";
    protected static final String REQUEST_MAPPING_VIEW = "/task/{id}";

    protected static final String VIEW_ADD = "task/add";
    protected static final String VIEW_LIST = "task/list";
    protected static final String VIEW_UPDATE = "task/update";
    protected static final String VIEW_VIEW = "task/view";

    @Resource
    private TaskService service;

    @Resource
    private MessageSource messageSource;

    @RequestMapping(value = "/task/add", method = RequestMethod.GET)
    public String showAddForm(Model model) {
        TaskDTO formObject = new TaskDTO();
        model.addAttribute(MODEL_ATTRIBUTE, formObject);

        return VIEW_ADD;
    }

    @RequestMapping(value = "/task/add", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute(MODEL_ATTRIBUTE) TaskDTO dto, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return VIEW_ADD;
        }

        Task added = service.add(dto);
        addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ADDED, added.getTitle());
        attributes.addAttribute(PARAMETER_ID, added.getId());

        return createRedirectViewPath(REQUEST_MAPPING_VIEW);
    }
    
    //ez volt utoljara, de mukodik
    @RequestMapping(value = "/task/delete/{id}", method = RequestMethod.GET)
    public String deleteById(@PathVariable("id") Long id, RedirectAttributes attributes) throws NotFoundException {
        Task deleted = service.deleteById(id);
        addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_DELETED, deleted.getTitle());
        return createRedirectViewPath(REQUEST_MAPPING_LIST);
    }

    @RequestMapping(value = "/task/list", method = RequestMethod.GET)
	public String listTasks(Model model) {

		List<Task> tasks = service.findAll();
		model.addAttribute("tasks", tasks);
		return VIEW_LIST;
	}
   
    /*
    @RequestMapping(value = REQUEST_MAPPING_LIST, method = RequestMethod.GET)
    public String findAll(Model model) {
        List<Task> tasks = service.findAll();
        model.addAttribute(MODEL_ATTRIBUTE_LIST, tasks);
        return VIEW_LIST;
    }*/

    
    @RequestMapping(value = REQUEST_MAPPING_VIEW, method = RequestMethod.GET)
    public String findById(@PathVariable("id") Long id, Model model) throws NotFoundException {
        Task found = service.findById(id);
        model.addAttribute(MODEL_ATTRIBUTE, found);
        return VIEW_VIEW;
    }

    @RequestMapping(value = "/task/update/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Long id, Model model) throws NotFoundException {
        Task updated = service.findById(id);
        TaskDTO formObject = constructFormObjectForUpdateForm(updated);
        model.addAttribute(MODEL_ATTRIBUTE, formObject);

        return VIEW_UPDATE;
    }

    @RequestMapping(value = "/task/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute(MODEL_ATTRIBUTE) TaskDTO dto, BindingResult result, RedirectAttributes attributes) throws NotFoundException {
        if (result.hasErrors()) {
            return VIEW_UPDATE;
        }

        Task updated = service.update(dto);
        addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_UPDATED, updated.getTitle());
        attributes.addAttribute(PARAMETER_ID, updated.getId());

        return createRedirectViewPath(REQUEST_MAPPING_VIEW);
    }
    

    private TaskDTO constructFormObjectForUpdateForm(Task updated) {
    	TaskDTO dto = new TaskDTO();

        dto.setId(updated.getId());
        dto.setDescription(updated.getDescription());
        dto.setTitle(updated.getTitle());
        dto.setStatus(updated.getStatus());

        return dto;
    }

    private void addFeedbackMessage(RedirectAttributes attributes, String messageCode, Object... messageParameters) {
        String localizedFeedbackMessage = getMessage(messageCode, messageParameters);
        attributes.addFlashAttribute(FLASH_MESSAGE_KEY_FEEDBACK, localizedFeedbackMessage);
    }

    private String getMessage(String messageCode, Object... messageParameters) {
        Locale current = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageCode, messageParameters, current);
    }


    private String createRedirectViewPath(String requestMapping) {
        StringBuilder redirectViewPath = new StringBuilder();
        redirectViewPath.append("redirect:");
        redirectViewPath.append(requestMapping);
        return redirectViewPath.toString();
    }
}
