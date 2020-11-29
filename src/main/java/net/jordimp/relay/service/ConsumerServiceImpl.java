package net.jordimp.relay.service;

import org.springframework.stereotype.Service;

import net.jordimp.relay.threading.WorkerThreadPool;
import net.jordimp.relay.utils.Log;

@Service("gamePlayService")
public class ConsumerServiceImpl implements ConsumerService {

	private static final String TAG = "ConsumerServiceImpl";

	@Override
	public String relayMessages() {
		Log.debug(TAG, " *** relayMessages ***");
		try {
			WorkerThreadPool.init(3);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "ok";
	}

}
