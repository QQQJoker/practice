package com.joker.practice.atomic;
/**
 * 静态内部类 单例模式。保证多线程模式下的线程安全。
 * Q:为什么静态内部类可以保证多线程模式下的线程安全？
 * A:利用的是类初始化有一个初始化锁的原理。
 * @author Joker
 *
 */
public class StaticInnerClass {
	//静态内部类
	private static class InnerClass{
		public static InnerClass innerClass = new InnerClass();
	}
	
	private StaticInnerClass() {}
	
	public static InnerClass getInstance() {
		return InnerClass.innerClass;
	}
	
}
