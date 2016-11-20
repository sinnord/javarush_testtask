package com.sinnord.todolist.service;

import com.sinnord.todolist.dao.TaskDao;
import com.sinnord.todolist.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Sinnord on 19.11.2016.
 */
@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskDao taskDao;

    public long createTask(Task task){
        return taskDao.createTask(task);
    }

    public Task updateTask(Task task){
        return taskDao.updateTask(task);
    }

    public void deleteTask(long id){
        taskDao.deleteTask(id);
    }

    public List<Task> getAllTasks() {
        return taskDao.getAllTasks();
    }

    public int getSizeWithFilter(String filter){
        return taskDao.getSizeWithFilter(filter);
    }

    public List<Task> getPageWithFilter(String filter, int offset, int pageSize){
        return taskDao.getPageWithFilter(filter, offset, pageSize);
    }

    public Task getTask(long id){
        return taskDao.getTask(id);
    }
}
