package com.joker.practice.atomic;
/**
 * 测试指令重排对多线程的影响
 * @author Joker
 *
 */
public class ReorderTest {


	
	public static void main(String[] args) {
		
		ReorderExample reorder = new ReorderExample();
		
		ThreadA a = new ThreadA(reorder);
		
		ThreadB b = new ThreadB(reorder);
		
		a.start();
		
		b.start();
		
	}
	
	

}

class ReorderExample{
	
	int a = 0;
	
	boolean falg = false;
	
	public void writer() {  //操作1 和2之间没有数据依赖关系，会进行指令重排
		System.out.println("begin to write");
		a =1 ;     			//操作1
		falg = true;		//操作2
	}
	
	public void reader() { //操作3 和4之间没有数据依赖关系，会进行指令重排
		System.out.println("begin to read");
		if(falg) {			//操作3
			int i = a*a;	//操作4
			System.out.println(" i = "+i);
		}
	}
}

class ThreadA extends Thread{
	
	private ReorderExample reorder ;
	
	public  ThreadA(ReorderExample reorder) {
		this.reorder = reorder;
	}
	
	@Override
	public void run() {
		reorder.writer();
	}
}

class ThreadB extends Thread{
	
	private ReorderExample reorder ;
	
	public ThreadB(ReorderExample reorder) {
		this.reorder = reorder;
	}
	
	@Override
	public void run() {
		reorder.reader();
	}
}
