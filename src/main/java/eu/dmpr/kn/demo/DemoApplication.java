package eu.dmpr.kn.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import eu.dmpr.kn.demo.exception.ApplicationErrorListener;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(DemoApplication.class)
				.listeners(new ApplicationErrorListener())
				.run(args);

		ctx.close();
	}
}
