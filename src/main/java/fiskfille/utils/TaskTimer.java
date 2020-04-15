package fiskfille.utils;

import java.util.Map;

import com.google.common.collect.Maps;

public class TaskTimer
{
    private static Map<String, TaskTimer> timers = Maps.newHashMap();

    private final String task;
    private long startTime;

    private TaskTimer(String s)
    {
        task = s;
    }

    public static void start(String task)
    {
        get(task).startTime = System.currentTimeMillis();
    }

    public static void stop(String task)
    {
        Log.info2("Tasks", "Finished task %s in %s ms", task, System.currentTimeMillis() - get(task).startTime);
    }

    private static TaskTimer get(String task)
    {
        TaskTimer timer = timers.get(task);

        if (timer == null)
        {
            timers.put(task, timer = new TaskTimer(task));
        }

        return timer;
    }
}
