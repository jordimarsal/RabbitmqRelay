package net.jordimp.relay.threading.factory;

import net.jordimp.relay.threading.WorkerThread;

public class WorkerFactory implements AbstractFactory<Runnable> {

	@Override
	public Runnable create(Integer id) {

		return new WorkerThread(id);

	}

}