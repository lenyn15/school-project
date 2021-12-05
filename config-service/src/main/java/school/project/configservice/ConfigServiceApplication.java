package school.project.configservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import static org.springframework.boot.SpringApplication.run;

@EnableConfigServer
@SpringBootApplication
public class ConfigServiceApplication {

    public static void main( String[] args ) {
        run( ConfigServiceApplication.class, args );
    }

}
