package com.joker.practice.atomic;

import static com.joker.practice.atomic.SleepUtils.second;;
/**
 * 测试守护线程中finally块中代码是否会执行
 * 在构建Daemon线程时候，不能依靠finally块中的内容来确保执行关闭或者是清理资源。
 * 在Daemon线程中的finally块不一定会执行。
 * @author joker
 *
 */
public class Daemon {

	public static void main(String[] args) {
		
		Thread thread = new Thread(new DaemonRunner(),"DaemonRunner");
		thread.setDaemon(true);
		thread.start();
	}
	
	static class DaemonRunner implements Runnable{

		@Override
		public void run() {

			try {
				second(100); //静态导入 = SleepUtils.second(100);
			}finally{
				System.out.println("DaemonRunner finally run");
			}
		}
		
	}

}
