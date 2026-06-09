package com.islam.taskmanager_spring.commands;

import com.islam.taskmanager_spring.service.TaskService;
import com.islam.taskmanager_spring.ui.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandCreator {
    private static final Logger logger = LoggerFactory.getLogger(CommandCreator.class);

    public List<Parameters> getParameters(Menu menu){
        List<Parameters> params = new ArrayList<>();
        switch (menu){
            case ADD_TASK:
                params.add(Parameters.TASK_DESCRIPTION);
                break;
            case COMPLETE_TASK:
                params.add(Parameters.TASK_ID);
                break;
            case DELETE_TASK:
                params.add(Parameters.TASK_ID);
                break;
            case DISPLAY_TASKS:
            case DISPLAY_COMPLETED_TASKS:
            case DISPLAY_PENDING_TASKS:
            case DISPLAY_ABANDONED_TASKS:
            case EXIT:
                break;
        }
        logger.debug("Для пункта меню {} требуются следующие параметры: {}", menu, params);
        return params;
    }

    public Command createCommand(Menu menu, TaskService service, Map<Parameters, Object> params){
        switch (menu){
            case ADD_TASK:
                return new AddTask(service, (String) params.get(Parameters.TASK_DESCRIPTION));
            case COMPLETE_TASK:
                return new CompleteTask(service, (Integer) params.get(Parameters.TASK_ID));
            case DELETE_TASK:
                return new DeleteTask(service, (Integer) params.get(Parameters.TASK_ID));
            case DISPLAY_TASKS:
                return new DisplayTasks(service, DisplayTasksFilter.ALL_TASKS);
            case DISPLAY_COMPLETED_TASKS:
                return new DisplayTasks(service, DisplayTasksFilter.COMPLETED_TASKS);
            case DISPLAY_PENDING_TASKS:
                return new DisplayTasks(service, DisplayTasksFilter.PENDING_TASKS);
            case DISPLAY_ABANDONED_TASKS:
                return new DisplayTasks(service, DisplayTasksFilter.ABANDONED_TASKS);
            case EXIT:
                return new Exit(service);
            default:
                logger.warn("Команды для пункта меню {} нет", menu);
                throw new IllegalArgumentException("Такой команды для создания нет.");
        }
    }
}
