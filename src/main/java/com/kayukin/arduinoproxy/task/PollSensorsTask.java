package com.kayukin.arduinoproxy.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PollSensorsTask {
    private static final int POLL_RATE = 3000;

    @Scheduled(fixedRate = POLL_RATE)
    public void run() {
        System.out.println("task-runned");
    }
}
