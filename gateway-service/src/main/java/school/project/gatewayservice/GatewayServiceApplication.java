package school.project.gatewayservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import static org.springframework.boot.SpringApplication.run;

@EnableEurekaClient
@SpringBootApplication
public class GatewayServiceApplication {

    public static void main( String[] args ) {
        run( GatewayServiceApplication.class, args );
    }

}
