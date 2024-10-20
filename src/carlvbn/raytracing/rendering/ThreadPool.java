package carlvbn.raytracing.rendering;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

// source https://www.javacodegeeks.com/2016/12/implement-thread-pool-java.html

public class ThreadPool {
	private ArrayBlockingQueue<Runnable> tasks;
	private List<Thread> threads;
	private boolean run;
	
	public ThreadPool(int amountThreads, int amountTasks) {
		this.run = true;
		this.tasks = new ArrayBlockingQueue<>(amountTasks);
		this.threads = new ArrayList<>();
		
		for(int i = 0; i<amountThreads; i++) {
			Thread threadie = new Thread(new TheadPoolWorker());
			this.threads.add(threadie);
			threadie.start();
		}
		
	}
	
	public void submit(Runnable t) {
		if(run) {
			try {
				tasks.put(t);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void shutdown() {
		this.run = false;
		
		for(Thread t : threads) {
			t.interrupt();
			
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private class TheadPoolWorker implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
                while (run || !tasks.isEmpty()) {
                    Runnable task = tasks.take();
                    task.run();
                }
            } catch (InterruptedException e) {
                // blabla
            }
			
		}
		
	}

}
