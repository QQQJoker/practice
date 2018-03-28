package com.joker.practice.atomic;
/**
 * 单例模式的双重检测测试。使用volatile来禁止指令重排序。保证多线程模式下的线程安全。
 * @author Joker
 *
 */
public class DoubleCheckedLocking {
	private volatile static DoubleCheckedLocking checkedLocking;
	
	private DoubleCheckedLocking() {}
	
	public static DoubleCheckedLocking getInstance() {
		if( checkedLocking == null) {
			synchronized(DoubleCheckedLocking.class) {
				if(checkedLocking == null) {
					checkedLocking = new DoubleCheckedLocking();
				}
			}
		}
		return checkedLocking;
	}
	
}
