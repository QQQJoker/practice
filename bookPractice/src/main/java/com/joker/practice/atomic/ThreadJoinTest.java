package com.joker.practice.atomic;
/**
 * 测试thread.join(),等待一个线程终止之后才返回。
 * 
 * @author Joker
 *
 */
public class ThreadJoinTest {

	public static void main(String[] args) {
		Thread previous = Thread.currentThread();
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new Domino(previous), String.valueOf(i));
			thread.start();
			previous = thread;
		}
		
		SleepUtils.second(2);
		
		System.out.println(Thread.currentThread().getName()+" terminate.");
	}

	static class Domino implements Runnable {

		private Thread thread;

		public Domino(Thread previous) {
			this.thread = previous;
		}

		@Override
		public void run() {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+" terminate.");
		}

	}

}
