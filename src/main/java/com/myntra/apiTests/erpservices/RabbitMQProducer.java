package com.myntra.apiTests.erpservices;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.rabbitmq.client.*;

public class RabbitMQProducer {
	public static void main(String[] args) throws Exception {
		RabbitMQProducer rabbitObj = new RabbitMQProducer();
		BufferedReader rd = new BufferedReader(new InputStreamReader(new FileInputStream("Data/lms/temp.txt")));
		String line = null;
		StringBuilder payloadBuilder = new StringBuilder();
		while ((line = rd.readLine()) != null) {
			payloadBuilder.append(line);
		}
		rabbitObj.writeInQueue("54.151.192.119", payloadBuilder.toString(), "oms", "packReleaseEvents", "/");
	}

	public void writeInQueue(String host, String payload, String exchangeName, String routingKey, String virtualHost) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setUsername("guest");
			factory.setPassword("guest");
			factory.setVirtualHost(virtualHost);
			factory.setHost(host);
			factory.setPort(5672);
			Connection conn = factory.newConnection();
			Channel channel = conn.createChannel();
			// String exchangeName = "oms";
			// String routingKey = "packReleaseQueueLMS";
			byte[] messageBodyBytes = payload.getBytes();
			channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, messageBodyBytes);
			channel.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}