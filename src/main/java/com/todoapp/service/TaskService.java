package com.todoapp.service;

import com.todoapp.dto.TaskRequestDto;
import com.todoapp.dto.TaskResponseDto;
import com.todoapp.dto.UserResponseDto;
import com.todoapp.model.Category;
import com.todoapp.model.Priority;
import com.todoapp.model.Tag;
import com.todoapp.model.Task;
import com.todoapp.model.User;
import com.todoapp.repository.CategoryRepository;
import com.todoapp.repository.TagRepository;
import com.todoapp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public List<TaskResponseDto> getTasksByAuthor(User author) {
        return taskRepository.findByAuthor(author).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public TaskResponseDto getTaskById(Long id, User author) {
        Task task = getTaskIfOwner(id, author);
        return mapToDto(task);
    }

    public List<TaskResponseDto> getTasksByTag(String tag, User author) {
        return taskRepository.findByTags_NameAndAuthor(tag, author).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<TaskResponseDto> searchTasks(User author, String title, Boolean completed, String category, Priority priority, LocalDate deadlineBefore) {
        Stream<Task> stream = taskRepository.findByAuthor(author).stream();

        if (title != null && !title.isEmpty()) stream = stream.filter(t -> t.getTitle().toLowerCase().contains(title.toLowerCase()));
        if (completed != null) stream = stream.filter(t -> t.isCompleted() == completed);
        if (category != null && !category.isEmpty()) stream = stream.filter(t -> t.getCategory() != null && t.getCategory().getTitle().equalsIgnoreCase(category));
        if (priority != null) stream = stream.filter(t -> t.getPriority() == priority);
        if (deadlineBefore != null) stream = stream.filter(t -> t.getDeadline() != null && t.getDeadline().isBefore(deadlineBefore));

        return stream.map(this::mapToDto).collect(Collectors.toList());
    }

    public TaskResponseDto createTask(TaskRequestDto dto, User author) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.isCompleted());
        task.setDeadline(dto.getDeadline());
        task.setPriority(dto.getPriority());
        task.setAuthor(author);

        if (dto.getCategoryId() != null) {
            task.setCategory(categoryRepository.findById(dto.getCategoryId()).orElse(null));
        }

        if (dto.getTagIds() != null && !dto.getTagIds().isEmpty()) {
            task.setTags(tagRepository.findAllById(dto.getTagIds()));
        } else {
            task.setTags(new ArrayList<>());
        }

        return mapToDto(taskRepository.save(task));
    }

    public TaskResponseDto updateTask(Long id, TaskRequestDto dto, User author) {
        Task task = getTaskIfOwner(id, author);

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.isCompleted());
        task.setDeadline(dto.getDeadline());
        task.setPriority(dto.getPriority());

        if (dto.getCategoryId() != null) {
            task.setCategory(categoryRepository.findById(dto.getCategoryId()).orElse(null));
        }
        if (dto.getTagIds() != null) {
            task.setTags(tagRepository.findAllById(dto.getTagIds()));
        }

        return mapToDto(taskRepository.save(task));
    }

    public void deleteTask(Long id, User author) {
        Task task = getTaskIfOwner(id, author);
        taskRepository.delete(task);
    }

    public TaskResponseDto addTagToTask(Long taskId, Long tagId, User author) {
        Task task = getTaskIfOwner(taskId, author);
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag no encontrado"));
        if (!task.getTags().contains(tag)) {
            task.getTags().add(tag);
        }
        return mapToDto(taskRepository.save(task));
    }

    public TaskResponseDto removeTagFromTask(Long taskId, Long tagId, User author) {
        Task task = getTaskIfOwner(taskId, author);
        task.getTags().removeIf(t -> t.getId().equals(tagId));
        return mapToDto(taskRepository.save(task));
    }

    public Map<String, Object> getDashboardStats(User author) {
        long total = taskRepository.countByAuthor(author);
        long completed = taskRepository.countByAuthorAndCompleted(author, true);
        return Map.of("totalTasks", total, "completedTasks", completed, "pendingTasks", total - completed);
    }

    private Task getTaskIfOwner(Long id, User author) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarea no encontrada"));
        if (!task.getAuthor().getId().equals(author.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para modificar esta tarea");
        }
        return task;
    }

    private TaskResponseDto mapToDto(Task task) {
        UserResponseDto authorDto = UserResponseDto.builder()
                .id(task.getAuthor().getId())
                .username(task.getAuthor().getUsername())
                .email(task.getAuthor().getEmail())
                .fullname(task.getAuthor().getFullname())
                .role(task.getAuthor().getRole())
                .build();

        return TaskResponseDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.isCompleted())
                .createdAt(task.getCreatedAt())
                .deadline(task.getDeadline())
                .priority(task.getPriority())
                .categoryName(task.getCategory() != null ? task.getCategory().getTitle() : null)
                .tags(task.getTags() != null ? task.getTags().stream().map(Tag::getName).collect(Collectors.toList()) : new ArrayList<>())
                .author(authorDto)
                .build();
    }
}