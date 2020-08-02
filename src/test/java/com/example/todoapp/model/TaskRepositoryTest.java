package com.example.todoapp.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TaskRepositoryTest implements TaskRepository {
    private Map<Integer, Task> tasks = new HashMap<>();

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Page<Task> findAll(Pageable page) {
        return null;
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public Task save(final Task taskToSave) {
        return tasks.put(tasks.size() + 1, taskToSave);
    }

    @Override
    public boolean existsById(Integer id) {
        return tasks.containsKey(id);
    }

    @Override
    public boolean existsByDoneIsFalseAndTasks_Group_ID(Integer groupId) {
        return false;
    }

    @Override
    public List<Task> findByDone(boolean done) {
        return null;
    }




    @Override
    public List<Task> findAllByTaskGroupId(Integer groupId) {
        return null;
    }
}