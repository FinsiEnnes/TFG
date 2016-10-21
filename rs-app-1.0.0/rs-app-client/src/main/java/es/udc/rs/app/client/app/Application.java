package es.udc.rs.app.client.app;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan({ "es/udc/rs/app/client/app", "es/udc/rs/app/client/controllers", "es/udc/rs/app/client/conversor" })
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ImportResource("file:/home/finsi/myProject/rs-app-1.0.0/rs-app-model/src/main/resources/spring-config.xml")
public class Application {
    
	public static void main(String[] args) {
        
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}
