package intern;

import intern.scheduled.ScheduledTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class AppConfig {
    @Bean
    public ScheduledTask task() {
        return new ScheduledTask();
    }
}
