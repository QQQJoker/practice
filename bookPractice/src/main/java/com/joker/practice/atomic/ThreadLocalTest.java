package com.joker.practice.atomic;
/**
 * 使用ThreadLocal测试统计耗时。
 * @author joker
 *
 */
public class ThreadLocalTest {

	private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {

		@Override
		protected Long initialValue() {
			return System.currentTimeMillis();
		}
		
	};
	
	public static void main(String[] args) {
		ThreadLocalTest.begin();
		
		SleepUtils.second(1);
		
		System.out.println("Cost: "+ThreadLocalTest.end()+" mills");
		
	}
	
	public static final void begin() {
		TIME_THREADLOCAL.set(System.currentTimeMillis());
	}
	
	public static final long end() {
		return System.currentTimeMillis()-TIME_THREADLOCAL.get();
	}

}
