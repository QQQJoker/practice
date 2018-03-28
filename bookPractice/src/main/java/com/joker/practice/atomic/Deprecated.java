package com.joker.practice.atomic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
/**
 * 用于测试suspend resume stop 方法，对线程的暂停 恢复，停止
 * 但是都是过时的方法，不建议使用。因为suspend的暂停是带锁的暂停，不会释放资源。stop方法也不会保证资源的正常释放。
 * @author joker
 *
 */
public class Deprecated {

	public static void main(String[] args) throws Exception {

		DateFormat format = new SimpleDateFormat("HH:mm:ss");
		
		Thread printhread = new Thread(new Runner(),"PrintThread") ;
		printhread.setDaemon(true);
		printhread.start();
		
		TimeUnit.SECONDS.sleep(3);
		//将printthread进行暂停，输出内容停止
		printhread.suspend();
		
		System.out.println("main suspend printThread at : "+format.format(new Date()));
		
		TimeUnit.SECONDS.sleep(3);
		
		//将pringThread进行恢复，输出内容将继续输出
		printhread.resume();
		
		System.out.println("main resume printThread at : "+format.format(new Date()));
		
		TimeUnit.SECONDS.sleep(3);
		//将thread停止。
		printhread.stop();
		
		System.out.println("main stop printThread at : "+format.format(new Date()));
		TimeUnit.SECONDS.sleep(3);
		
		
	}
	
	static class Runner implements Runnable{

		@Override
		public void run() {
			DateFormat format = new SimpleDateFormat("HH:mm:ss");
			while(true) {
				System.out.println(Thread.currentThread().getName()+" run at :"+format.format(new Date()));
				SleepUtils.second(1);
			}
		}
		
	}

}
