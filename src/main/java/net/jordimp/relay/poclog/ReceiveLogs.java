package net.jordimp.relay.poclog;

import java.nio.charset.StandardCharsets;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import net.jordimp.relay.utils.Log;

public class ReceiveLogs {
	private static final String EXCHANGE_NAME = "logs";

	private static final String TAG = "ReceiveLogs";
	
	private ReceiveLogs() {}

	public static void go(int worker) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, "");

		Log.debug(TAG, String.format(" [*%d] Waiting for messages. To exit press CTRL+C", worker));

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
			Log.debug(TAG, String.format(" [%d] Received '%s'", worker, message));
		};
		channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
		});
	}
}
