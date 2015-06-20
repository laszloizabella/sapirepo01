package ro.sapientia2015.story.model;

import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * @author Izabella
 */
public class TaskTest {

    private String TITLE = "title";
    private String DESCRIPTION = "description";
    private String STATUS = "status";

    @Test
    public void buildWithMandatoryInformation() {
        Task built = Task.getBuilder(TITLE).build();

        assertNull(built.getId());
        assertNull(built.getCreationTime());
        assertNull(built.getDescription());
        assertNull(built.getModificationTime());
        assertNull(built.getStatus());
        assertEquals(TITLE, built.getTitle());
        assertEquals(0L, built.getVersion());
    }

    @Test
    public void buildWithAllInformation() {
        Task built = Task.getBuilder(TITLE)
                .description(DESCRIPTION)
                .status(STATUS)
                .build();

        assertNull(built.getId());
        assertNull(built.getCreationTime());
        assertEquals(DESCRIPTION, built.getDescription());
        assertNull(built.getModificationTime());
        assertEquals(TITLE, built.getTitle());
        assertEquals(STATUS, built.getStatus());
        assertEquals(0L, built.getVersion());
    }

    @Test
    public void prePersist() {
        Task task = new Task();
        task.prePersist();

        assertNull(task.getId());
        assertNotNull(task.getCreationTime());
        assertNull(task.getDescription());
        assertNotNull(task.getStatus());
        assertNotNull(task.getModificationTime());
        assertNull(task.getTitle());
        assertEquals(0L, task.getVersion());
        assertEquals(task.getCreationTime(), task.getModificationTime());
    }

    @Test
    public void preUpdate() {
        Task task = new Task();
        task.prePersist();

        pause(1000);

        task.preUpdate();

        assertNull(task.getId());
        assertNotNull(task.getCreationTime());
        assertNull(task.getDescription());
        assertNotNull(task.getStatus());
        assertNotNull(task.getModificationTime());
        assertNull(task.getTitle());
        assertEquals(0L, task.getVersion());
        assertTrue(task.getModificationTime().isAfter(task.getCreationTime()));
    }

    private void pause(long timeInMillis) {
        try {
            Thread.currentThread().sleep(timeInMillis);
        }
        catch (InterruptedException e) {
            //Do Nothing
        }
    }
}
