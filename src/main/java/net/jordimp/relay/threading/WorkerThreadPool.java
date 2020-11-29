package net.jordimp.relay.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.jordimp.relay.utils.Log;

public class WorkerThreadPool {

	private static final String TAG = "WorkerThreadPool";

	private static ExecutorService executor = null;

	private WorkerThreadPool() {
	}

	public static void init(int numWorkers) {
		Log.debug(TAG, " init "+numWorkers+" workers.");
		executor = Executors.newFixedThreadPool(numWorkers);
		while (!executor.isTerminated()) {
			for (int i = 0; i < numWorkers; i++) {
				Runnable worker = WorkerThreadHandler.getWorker(i);
				executor.execute(worker);// calling execute method of ExecutorService
			}
		}

		System.out.println("Finished all threads");
	}

	public static void stop() {
		executor.shutdown();
	}
}

