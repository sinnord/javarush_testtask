package com.sinnord.todolist.controller;

import com.sinnord.todolist.model.Task;
import com.sinnord.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sinnord on 19.11.2016.
 */
@Controller
public class TaskController {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private TaskService taskService;

    @RequestMapping("createTask")
    public ModelAndView createTask(@ModelAttribute Task task) {
        return new ModelAndView("taskForm", "taskObject", task);
    }

    @RequestMapping("editTask")
    public ModelAndView editTask(@RequestParam long id, @ModelAttribute Task task){
        task = taskService.getTask(id);
        return new ModelAndView("taskForm", "taskObject", task);
    }

    @RequestMapping("saveTask")
    public ModelAndView saveTask(@ModelAttribute Task task){
        if (task.getId() == 0){
            taskService.createTask(task);
        } else{
            taskService.updateTask(task);
        }
        return new ModelAndView("redirect:getAllTasks");
    }

    @RequestMapping("deleteTask")
    public ModelAndView deleteTask(@RequestParam long id) {
        taskService.deleteTask(id);
        return new ModelAndView("redirect:getAllTasks");
    }



    @RequestMapping(value = {"getAllTasks", "/"})
    public ModelAndView getAllTasks(@RequestParam(value = "done", defaultValue = "all") String filter,
                                    @RequestParam(value = "page", defaultValue = "1") String page) {

        int offset = (Integer.parseInt(page) - 1) * PAGE_SIZE;
        List<Task> tasks = taskService.getPageWithFilter(filter, offset, PAGE_SIZE);
        int size = taskService.getSizeWithFilter(filter);
        int total = size / PAGE_SIZE;
        if (size % PAGE_SIZE > 0) total++;
        List<Integer> pageList = new ArrayList<>();
        for (int i = 1; i <= total; i++) {
            pageList.add(i);
        }
        ModelAndView mav = new ModelAndView("tasks", "tasksList", tasks);
        mav.addObject("pageList", pageList);
        mav.addObject("filter", filter);
        mav.addObject("curPage", Integer.parseInt(page));
        return mav;
    }

    @RequestMapping("addTestTasks")
    public ModelAndView addTestTasks(){
        for (int i = 0; i < 100; i++) {
            Task task = new Task();
            task.setDescription("TestTask " + i);
            task.setSchedule(new Date());
            task.setDone(Math.random() > 0.5);
            taskService.createTask(task);
        }
        return new ModelAndView("redirect:getAllTasks?done=all?page=1");
    }


    @InitBinder
    protected void initBinder(WebDataBinder binder) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));

    }
}
