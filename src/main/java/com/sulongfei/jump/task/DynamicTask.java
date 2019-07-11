package com.sulongfei.jump.task;

import com.sulongfei.jump.context.GlobalContext;
import com.sulongfei.jump.exception.JumpException;
import com.sulongfei.jump.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/11 15:00
 * @Version 1.0
 */
@Slf4j
@Component
public class DynamicTask implements SchedulingConfigurer {
    @Autowired
    private TaskService taskService;
    @Autowired
    private GlobalContext globalContext;

    private static String cron;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(doTask(), getTrigger());
    }

    private Runnable doTask() {
        return () -> {
            log.info("=============开始执行定时任务=============");
            taskService.settle();
            taskService.resetRank();
            log.info("=============定时任务执行完毕=============");
        };
    }

    private Trigger getTrigger() {
        return triggerContext -> {
            CronTrigger trigger = new CronTrigger(getCron());
            return trigger.nextExecutionTime(triggerContext);
        };
    }

    private String getCron() {
        String newCron = globalContext.getResetRankCron();
        if (StringUtils.isEmpty(newCron)) {
            throw new JumpException("The config cron expression is empty");
        }
        if (!newCron.equals(cron)) {
            log.info("Cron has been changed to:'{}'. Old cron was:'{}'", newCron, cron);
            cron = newCron;
        }
        return cron;
    }
}
