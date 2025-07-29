package com.example.rabbitmq_publisher_sender;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
	public RabbitTemplate rabbitTemplate() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		connectionFactory.setAddresses("localhost:30000,localhost:30002,localhost:30004");
		connectionFactory.setChannelCacheSize(10);

		return new RabbitTemplate(connectionFactory);
	}

	@Bean
	public CommandLineRunner runner(RabbitTemplate rabbitTemplate) {
		return args -> {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter messages to send to 'q.example'. Type 'exit' to quit.");
			while (true) {
				System.out.print("Message: ");
				String message = scanner.nextLine();
				if ("exit".equalsIgnoreCase(message)) {
					break;
				}
				rabbitTemplate.convertAndSend("q.example", message);
				System.out.println("Sent: " + message);
			}
			scanner.close();
		};
	}
}
