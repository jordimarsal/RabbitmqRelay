package net.jordimp.relay.poclog;

import java.nio.charset.StandardCharsets;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import net.jordimp.relay.utils.Log;

public class EmitLog {

	private static final String QUEUE_NAME = "task_queue";

	private static final String TAG = "EmitLog";

	private static final String[] messages = { "LOG 1.", "LOG 2..", "LOG 3....", "LOG 4..",
			"LOG 5...", "LOG 6.", "LOG 7...", "LOG 8..", "LOG 9.", "LOG 10...", "LOG 11...." };

	private EmitLog() {}
	
	public static void generate(String exchangeName) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try ( // @formatter:off
				Connection connection = factory.newConnection();
				Channel channel = connection.createChannel()) {
			// @formatter:on

			channel.exchangeDeclare(exchangeName, "fanout");

			for (String message : messages) {

				channel.basicPublish(exchangeName, QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
				Log.debug(TAG, String.format(" [x] Sent '%s'", message));

			}

		}
	}

}
