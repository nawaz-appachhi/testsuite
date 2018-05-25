/**
 * 
 */
package com.myntra.apiTests.portalservices.queueverification;

/**
 * @author santwana.samantray
 *
 */

import com.myntra.apiTests.erpservices.RabbitMQConsumer;
import com.myntra.test.commons.testbase.BaseTest;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.testng.annotations.Test;

import java.io.IOException;


public class QueueAssertion extends BaseTest {
			RabbitMQConsumer r=new RabbitMQConsumer();
		public static void purgeQueue(String host,String queue, int port) 
		{
					//if(host.equals("delta7"))
				//host="54.255.22.141";
					ConnectionFactory factory = new ConnectionFactory();
					factory.setUsername("guest");
					factory.setPassword("guest");
					factory.setVirtualHost("/");
					factory.setHost(host);
					factory.setPort(port);
					Connection conn;
					try {
						conn = factory.newConnection();
						Channel channel = conn.createChannel();
						int consumers=channel.queueDeclarePassive(queue).getConsumerCount();
						channel.queuePurge(queue);
						channel.close();
						conn.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//return true;
		}

		public static boolean readMessageFromQueue(String host,int port,String queueName,String exchangeName,String routingKey,int msgCount,String verifyText)
		{
			boolean hasMessage=true;
			try
			{

				//if(host.equals("devint"))
					//host="d1rabbitmq";
				StringBuilder messageBuilder=new StringBuilder();
				ConnectionFactory factory = new ConnectionFactory();
				factory.setUsername("guest");
				factory.setPassword("guest");
				factory.setVirtualHost("/");
				factory.setHost(host);
				factory.setPort(port);
				Connection conn = factory.newConnection();
				Channel channel = conn.createChannel();

				//String exchangeName = "oms";
				//			String queueName = "packReleaseQueueLMSTemp";
				//			String routingKey = "packReleaseQueueLMS";
				boolean durable = true;
				//set up exchange,queue

				//channel.exchangeDeclare(exchangeName, "direct", durable);
				//channel.queueDeclare(queueName, durable,false,false,null);
				//channel.queueBind(queueName, exchangeName, routingKey);
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
					if(delivery==null)
					{
						hasMessage= false;
						return hasMessage;
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

		@Test
	public void consumerCount()
		{
			purgeQueue("erprabbitmq.stage.myntra.com","releaseProgressEventWmsMyntra",15672);
		}
	

}
