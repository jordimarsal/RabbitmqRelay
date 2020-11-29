package net.jordimp.relay.threading;

import net.jordimp.relay.taskwork.Worker;

public class WorkerThread implements Runnable {

	private int workerNum;

	private Worker worker;

	public WorkerThread(int num) {
		this.workerNum = num;
		worker = new Worker(workerNum);
	}

	public void run() {
		try {
			worker.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
