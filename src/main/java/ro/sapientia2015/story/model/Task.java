package ro.sapientia2015.story.model;


import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.joda.time.DateTime;





import javax.persistence.*;

/**
 * @author Izabella 
 */
@Entity
@Table(name="task")
public class Task {

    public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_TITLE = 15;
    public static final int MIN_LENGTH_TITLE = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "creation_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationTime;

    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;

    @Column(name = "modification_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationTime;

    @Column(name = "title", nullable = false)
    @Length(max = Task.MAX_LENGTH_TITLE, min = Task.MIN_LENGTH_TITLE)
    private String title;
   
    //@Range(min = 0, max = 100)
    @Column(name = "status", nullable = true)
    //@Column(name = "status", nullable = false, length = MAX_LENGTH_TITLE)
    private String status;
    
    
    @Version
    private long version;

    public Task() {

    }

    public static Builder getBuilder(String title) {
        return new Builder(title);
    }

    public Long getId() {
        return id;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }

    public String getDescription() {
        return description;
    }

    public DateTime getModificationTime() {
        return modificationTime;
    }

    public String getTitle() {
        return title;
    }
    
    public String getStatus() {
        return status;
    }

    public long getVersion() {
        return version;
    }

    @PrePersist
    public void prePersist() {
        DateTime now = DateTime.now();
        creationTime = now;
        modificationTime = now;
    }

    @PreUpdate
    public void preUpdate() {
        modificationTime = DateTime.now();
    }

    public void update(String description, String title, String status) {
        this.description = description;
        this.title = title;
        this.status = status;
    }

    public static class Builder {

        private Task built;

        public Builder(String title) {
            built = new Task();
            built.title = title;
        }

        public Task build() {
            return built;
        }

        public Builder description(String description) {
            built.description = description;
            return this;
        }
        
        public Builder status(String string){
        	built.status = string;
        	return this;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
