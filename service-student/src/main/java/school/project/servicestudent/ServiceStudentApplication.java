package school.project.servicestudent;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import static org.springframework.boot.SpringApplication.run;

@EnableEurekaClient
@SpringBootApplication
public class ServiceStudentApplication {

    public static void main( String[] args ) {
        run( ServiceStudentApplication.class, args );
    }

}
