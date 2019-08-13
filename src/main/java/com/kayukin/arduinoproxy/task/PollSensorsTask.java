package com.kayukin.arduinoproxy.task;

import com.kayukin.arduinoproxy.connection.ThingSpeakConnection;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PollSensorsTask {
    private static final int POLL_RATE = 3000;

    private final ThingSpeakConnection connection;

    public PollSensorsTask(ThingSpeakConnection connection) {
        this.connection = connection;
    }

    @Scheduled(fixedRate = POLL_RATE)
    public void run() {
    }
}
