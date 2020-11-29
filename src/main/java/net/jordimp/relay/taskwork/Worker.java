package net.jordimp.relay.taskwork;

import java.nio.charset.StandardCharsets;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import net.jordimp.relay.utils.Log;

public class Worker {

	private static final String QUEUE_NAME = "task_queue";

	private static final String TAG = "Worker";

	private int workerNum;

	public Worker(int workerNum) {
		this.workerNum = workerNum;
	}

	public void init() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		int prefetchCount = 1;
		channel.basicQos(prefetchCount);

		//Log.debug(TAG, String.format(" [*%d] Waiting for messages. To exit press CTRL+C", workerNum));

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

			Log.debug(TAG, String.format(" [%d] Received '%s'", workerNum, message));

			try {
				doWork(message);
			} finally {
				Log.debug(TAG, String.format(" [%d] Done", workerNum));
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			}
		};
		boolean autoAck = false;
		channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> {
		});
	}

	private static void doWork(String task) {
		for (char ch : task.toCharArray()) {
			if (ch == '.') {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					e.printStackTrace();
				}
			}
		}

	}
}