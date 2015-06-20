package ro.sapientia2015.story.dto;


import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import ro.sapientia2015.story.model.Task;

public class TaskDTO {
	  private Long id;

	    @Length(max = Task.MAX_LENGTH_DESCRIPTION)
	    private String description;

	    @NotEmpty
	    @Length(max = Task.MAX_LENGTH_TITLE, min = Task.MIN_LENGTH_TITLE)
	    private String title;
	    
	    @NotEmpty
	    //@Range(min = 0, max = 100)
	    //@Length(max = Task.MAX_LENGTH_TITLE)
	    private String status;
	    
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public TaskDTO() {

	    }

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

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }  
	    

	    @Override
	    public String toString() {
	        return ToStringBuilder.reflectionToString(this);
	    }
}
