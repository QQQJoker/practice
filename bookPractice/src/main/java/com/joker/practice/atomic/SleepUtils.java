package com.joker.practice.atomic;

import java.util.concurrent.TimeUnit;

public class SleepUtils {

	public static final void second(long seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static final void sleep(long seconds) {
		try {
			Thread.currentThread().sleep(seconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static final void millseconds(long mills) {
		try {
			TimeUnit.MICROSECONDS.sleep(mills);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
