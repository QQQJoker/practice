package com.joker.practice.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <li>使用CAS原子操作来实现的一个线程安全计数方法safeCount()</li>
 *     存在问题，ABA,循环时间长，开销很大。 
 * <li>使用非线程安全的计数方法count()</li>
 * @author Joker
 *
 */
public class AtomicTest {

	private AtomicInteger atomicI = new AtomicInteger(0);
	
	private int i = 0;
	
	public static void main(String[] args) {
		final AtomicTest cas = new AtomicTest();
		
		List<Thread> ts = new ArrayList<Thread>(600);
		
		long start = System.currentTimeMillis();
		
		for(int j=0 ; j < 100 ; j++) {
			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					for(int i=0 ; i < 100 ; i++) {
						cas.count();
						cas.safeCount();
					}
					
				}
			});
			ts.add(t);
		}
		
		for(Thread t : ts) {
			t.start();
		}
		//wait all thead done
		for(Thread t : ts) {
			try {
				t.join();
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("cas.i = "+cas.i);
			System.out.println("cas.atomicI ="+cas.atomicI);
			System.out.println("time = "+(System.currentTimeMillis() - start));
		}
		
		}
	
	   /**
	    * 使用CAS实现线程安全计数器
	    */
		private void safeCount() {
			for(;;) {
				int i = atomicI.get();
				boolean suc = atomicI.weakCompareAndSet(i, ++i);
				if(suc) {
					break;
				}
			}
		}
		
		/**
		 *  非线程安全计数器
		 */
		private void count() {
			i++;
		}
	
	}

