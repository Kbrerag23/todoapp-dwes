package com.todoapp.repository;

import com.todoapp.model.Task;
import com.todoapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAuthor(User author);
    List<Task> findByTags_NameAndAuthor(String tagName, User author);
    long countByAuthor(User author);
    long countByAuthorAndCompleted(User author, boolean completed);
}