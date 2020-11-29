package net.jordimp.relay;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.jordimp.relay.service.ConsumerServiceImpl;
import net.jordimp.relay.taskwork.NewTask;
import net.jordimp.relay.utils.Log;

@Component
public class StartUpInit {
	/* https://medium.com/@dmarko484/spring-boot-startup-init-through-postconstruct-765b5a5c1d29
	 	aprivate static final String EXCHANGE_NAME = "logs";
	 	EmitLog.generate(EXCHANGE_NAME);
	*/

	private static final String TAG = "StartUpInit";

	@Autowired
	private ConsumerServiceImpl consumerService;

	@PostConstruct
	public void init() {
		Log.debug(TAG, " *** RabbitMQ init ***");
		NewTask.connect(1);

		if (consumerService != null) {
			consumerService.relayMessages();
		}

	}

}
