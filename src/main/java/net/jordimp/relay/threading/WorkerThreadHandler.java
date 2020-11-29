package net.jordimp.relay.threading;

import java.util.HashMap;
import java.util.Map;

import net.jordimp.relay.threading.factory.WorkerFactory;



public class WorkerThreadHandler {

	static Map<Integer, Runnable> workers = new HashMap<>();

	private WorkerThreadHandler() {}
	
	public static Runnable getWorker(Integer id) {
		return getFromMap(id);
	}

	private static Runnable getFromMap(Integer id) {
		if (!workers.containsKey(id)) {
			Runnable gotWorker = new WorkerFactory().create(id);
			workers.put(id, gotWorker);
			return gotWorker;
		}
		return workers.get(id);
	}

}
