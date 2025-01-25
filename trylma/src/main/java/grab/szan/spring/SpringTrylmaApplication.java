package grab.szan.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import grab.szan.Server;

/**
 * Main Spring Boot application class.
 */
@SpringBootApplication(scanBasePackages = "grab.szan")
@EnableJpaRepositories("grab.szan")
@EntityScan("grab.szan")
public class SpringTrylmaApplication {

    private final Server server;

    @Autowired
    public SpringTrylmaApplication(Server server) {
        this.server = server;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringTrylmaApplication.class, args);
    }

    @EventListener(ContextRefreshedEvent.class)
    public void startServer() {
        new Thread(server::start).start();
    }
}
