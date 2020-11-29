package net.jordimp.relay.taskwork;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import net.jordimp.relay.utils.Log;

public class NewTask {

	private static final String QUEUE_NAME = "task_queue";

	private static final String TAG = "NewTask";

	private static final String[] messages = { "Missatge 1.", "Missatge 2.", "Missatge 3..", "Missatge 4.",
			"Missatge 5.", "Missatge 6.", "Missatge 7.", "Missatge 8.", "Missatge 9..", "Missatge 10.",
			"Missatge 11.." };

	private NewTask() {
	}

	public static void connect(int tasker) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
			boolean durable = false;
			channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

			batch(tasker, channel);

		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}

	private static void batch(int tasker, Channel channel) throws IOException {
		for (String message : messages) {
			String task = String.format("(%d): %s", tasker, message);
			publish(channel, task);
		}
	}

	private static void publish(Channel channel, String message) throws IOException {
		channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,
				message.getBytes(StandardCharsets.UTF_8));
		Log.debug(TAG, String.format(" [x] Sent '%s'", message));

	}

}
