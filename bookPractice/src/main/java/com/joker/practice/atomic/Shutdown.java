package com.joker.practice.atomic;

import java.util.concurrent.TimeUnit;
/**
 * 优雅停机，不使用stop直接终结线程
 * @author Joker
 *
 */
public class Shutdown {

	public static void main(String[] args) throws Exception {
		
		Runner one = new Runner();
		Thread countThread  = new Thread(one,"CountThread-1");
		countThread.start();
		TimeUnit.SECONDS.sleep(1);
		
		countThread.interrupt();
		
		Runner two = new Runner();
		countThread = new Thread(two,"CountThread-2");
		countThread.start();
		TimeUnit.SECONDS.sleep(1);
		
		two.cancel();
		
	}
	
	static class Runner implements Runnable{
		
		private long i;
		
		private volatile boolean on = true;
		
		@Override
		public void run() {
			while(on && !Thread.currentThread().isInterrupted()) {
				i++;
			}
			System.out.println("Count i = "+i);
		}
		
		public void cancel() {
			on = false;
		}
		
	}

}
