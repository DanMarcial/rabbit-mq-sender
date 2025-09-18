package com.example.rabbitmq_publisher_sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class Sender {

	public static void main(String[] args) {
		SpringApplication.run(Sender.class, args);
	}

	@Bean
	public CommandLineRunner runner(RabbitTemplate rabbitTemplate,
									@Value("${app.queue-name}") String queueName) {
		return args -> {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter messages to send to '" + queueName + "'. Type 'exit' to quit.");
			while (true) {
				System.out.print("Message: ");
				String message = scanner.nextLine();
				if ("exit".equalsIgnoreCase(message)) {
					break;
				}
				rabbitTemplate.convertAndSend(queueName, message);
				System.out.println("Sent: " + message);
			}
			scanner.close();
		};
	}
}