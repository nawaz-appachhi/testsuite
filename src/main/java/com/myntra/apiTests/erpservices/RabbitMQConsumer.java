package com.myntra.apiTests.erpservices;

import java.io.IOException;

import com.rabbitmq.client.*;
public class RabbitMQConsumer {
	public static void main(String []args) throws Exception {

		RabbitMQConsumer r=new RabbitMQConsumer();
		//r.purgeQueue("devint", "atlasOrderCreation");
		boolean response=r.readMessageFromQueue("d7","atlasOrderConfirmed","oms","atlasOrderConfirmed",1,"UTF-8","/");
		//System.out.println(response);
	}

	public void purgeQueue(String host,String queue,String virtualHost)
	{
		try
		{
				if(host.equals("devint"))
					host="10.146.134.133";
				ConnectionFactory factory = new ConnectionFactory();
				factory.setUsername("guest");
				factory.setPassword("guest");
				factory.setVirtualHost(virtualHost);
				factory.setHost(host);
				factory.setPort(5672);
				Connection conn;
				try {
					conn = factory.newConnection();
					Channel channel = conn.createChannel();
					channel.queuePurge(queue);
					channel.close();
					conn.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public boolean readMessageFromQueue(String host,String queueName,String exchangeName,String routingKey,int msgCount,String verifyText,String virtualHost)
	{
		boolean hasMessage=true;
		try
		{

			if(host.equals("devint"))
				host="d1rabbitmq";
			StringBuilder messageBuilder=new StringBuilder();
			ConnectionFactory factory = new ConnectionFactory();
			factory.setUsername("guest");
			factory.setPassword("guest");
			factory.setVirtualHost(virtualHost);
			factory.setHost(host);
			factory.setPort(5672);
			Connection conn = factory.newConnection();
			Channel channel = conn.createChannel();
			boolean noAck = false;
			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume(queueName, noAck, consumer);

			int j=0;
			while (j<msgCount) {
				QueueingConsumer.Delivery delivery;
				try {
					delivery = consumer.nextDelivery(5000);

				} catch (InterruptedException ie) {
					continue;
				}
				if(delivery!=null)
				{
					System.out.println("Message received" 
							+ new String(delivery.getBody()));
					messageBuilder.append(new String(delivery.getBody()));
					channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				}
				j++;
			}

			String textArr[]=verifyText.split(",");
			for(String text:textArr)
			{
				if(messageBuilder.toString().contains(text))
				{
					hasMessage=true;	
				}
				else
				{
					hasMessage=false;
					break;
				}
			}
			channel.close();
			conn.close();

		}

		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return hasMessage;
	}
}