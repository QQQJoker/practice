package com.joker.practice.atomic;
/**
 * 静态内部类 单例模式。保证多线程模式下的线程安全。
 * <li>Q:为什么静态内部类可以保证多线程模式下的线程安全？</li>
 * <li>A:利用的是类初始化有一个初始化锁的原理。</li>
 *    一个类或者接口中声明的一个静态字段被使用，而且这个字段不是一个常量字段。
 * 那么这个类或者接口将被立即初始化。初始化将会获得一个初始化锁
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
