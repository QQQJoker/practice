package com.joker.practice.atomic;

import java.util.concurrent.locks.Lock;

import org.junit.jupiter.api.Test;

public class TwinsLokcTest {

	@Test
	public void test() {
		final Lock lock = new TwinsLock();
		
		class Worker extends Thread{

			@Override
			public void run() {
				while(true) {
					lock.lock();
					try {
						SleepUtils.second(1);
						System.out.println(Thread.currentThread().getName());
						SleepUtils.second(1);
					}finally {
						lock.unlock();
					}
					
				}
			}
			
		}
		
		
		for(int i=0;i<10;i++) {
			Worker w = new Worker();
			w.setDaemon(true);
			w.start();
		}
		
		for(int i=0;i<10;i++) {
			SleepUtils.second(1);
			System.out.println();
		}
	}

}
