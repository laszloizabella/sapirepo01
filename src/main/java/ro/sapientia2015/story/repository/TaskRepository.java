package ro.sapientia2015.story.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.sapientia2015.story.model.Task;

/**
 * @author Izabella
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
}
