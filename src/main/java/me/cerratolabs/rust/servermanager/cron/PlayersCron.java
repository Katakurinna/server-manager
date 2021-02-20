package me.cerratolabs.rust.servermanager.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

@EnableAsync
public class PlayersCron {

    @Autowired

    @Async
    @Scheduled(fixedRate = 60000)
    public void scheduleFixedRateTaskAsync() {

    }

}
