package com.sinnord.todolist.dao;

import com.sinnord.todolist.model.Task;
import com.sinnord.todolist.util.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Sinnord on 19.11.2016.
 */
@Repository
public class TaskDao {


    @Autowired
    private HibernateUtil hibernateUtil;

    public long createTask(Task task){
        return (long) hibernateUtil.create(task);
    }

    public Task updateTask(Task task){
        return hibernateUtil.update(task);
    }

    public void deleteTask(long id) {
        Task task = new Task();
        task.setId(id);
        hibernateUtil.delete(task);
    }

    public Task getTask(long id){
        return hibernateUtil.fetchById(id, Task.class);
    }

    public List<Task> getAllTasks(){
        return hibernateUtil.fetchAll(Task.class);
    }

    public int getSizeWithFilter(String filter) {
        filter = setSQLFilter(filter);
        return hibernateUtil.getSizeWithFilter(Task.class, filter);
    }

    public List<Task> getPageWithFilter(String filter, int offset, int pageSize){
        filter = setSQLFilter(filter);
        return hibernateUtil.fetchPageWithFilter(Task.class, filter, offset, pageSize);
    }

    private String setSQLFilter(String filter){
        switch (filter){
            case "done":
                filter =" WHERE done=" + true;
                break;
            case "undone":
                filter = " WHERE done=" + false;
                break;
            default:
                filter = "";
        }
        return filter;
    }
}
