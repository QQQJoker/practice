package com.joker.practice.atomic;

import java.util.concurrent.TimeUnit;
/**
 * 用于测试线程状态
 * @author joker
 *
 */
public class ThreadState {

	public static void main(String[] args) {
		/**
		 * "TimeWaitingThread" #9 prio=5 os_prio=0 tid=0x00007f97100f5000 nid=0x6692 waiting on condition [0x00007f96fa932000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at java.lang.Thread.sleep(Thread.java:340)
        at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
        at com.joker.practice.atomic.SleepUtils.second(ThreadState.java:63)
        at com.joker.practice.atomic.ThreadState$TimeWaiting.run(ThreadState.java:25)
        at java.lang.Thread.run(Thread.java
		 */
		new Thread(new TimeWaiting(),"TimeWaitingThread").start();
		/**
		 * "WaitingThread" #10 prio=5 os_prio=0 tid=0x00007f97100f7000 nid=0x6693 in Object.wait() [0x00007f96fa831000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x0000000781f73f68> (a java.lang.Class for com.joker.practice.atomic.ThreadState$Waiting)
        at java.lang.Object.wait(Object.java:502)
        at com.joker.practice.atomic.ThreadState$Waiting.run(ThreadState.java:37)
        - locked <0x0000000781f73f68> (a java.lang.Class for com.joker.practice.atomic.ThreadState$Waiting)
        at java.lang.Thread.run(Thread.java:748)

		 */
		new Thread(new Waiting(),"WaitingThread").start();

		/**
		 * BlokcedThread-1" #11 prio=5 os_prio=0 tid=0x00007f97100f8800 nid=0x6694 waiting on condition [0x00007f96fa730000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at java.lang.Thread.sleep(Thread.java:340)
        at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
        at com.joker.practice.atomic.SleepUtils.second(ThreadState.java:63)
        at com.joker.practice.atomic.ThreadState$Blocked.run(ThreadState.java:52)
        - locked <0x0000000781f76ff0> (a java.lang.Class for com.joker.practice.atomic.ThreadState$Blocked)
        at java.lang.Thread.run(Thread.java:748)

		 */
		new Thread(new Blocked(),"BlokcedThread-1").start();
		/**
		 * "BlokcedThread-2" #12 prio=5 os_prio=0 tid=0x00007f97100fa000 nid=0x6695 waiting for monitor entry [0x00007f96fa62f000]
   java.lang.Thread.State: BLOCKED (on object monitor)
        at com.joker.practice.atomic.ThreadState$Blocked.run(ThreadState.java:52)
        - waiting to lock <0x0000000781f76ff0> (a java.lang.Class for com.joker.practice.atomic.ThreadState$Blocked)
        at java.lang.Thread.run(Thread.java:748)
		 */
		new Thread(new Blocked(),"BlokcedThread-2").start();
	}
	
	//线程不断进行睡眠
	static class TimeWaiting implements Runnable{

		@Override
		public void run() {

			while(true) {
				SleepUtils.second(100);
			}
		}}
	//该线程在waiting.class实例上等待
	static class Waiting implements Runnable{

		@Override
		public void run() {
			while(true) {
				
				synchronized(Waiting.class) {
					try {
						Waiting.class.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}}
	//
	static class Blocked implements Runnable{

		@Override
		public void run() {
		
			synchronized(Blocked.class) {
				while(true) {
					SleepUtils.second(100);
				}
			}
		}
		
	}
}

class SleepUtils{
	public static final void second(long seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}