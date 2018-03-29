package com.joker.practice.atomic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPoolTest {

	static ConnectionPool pool = new ConnectionPool(10);
	//保证所有ConnectionRunner能够同时开始
	static CountDownLatch start = new CountDownLatch(1);
	//main线程将会等待所有的connectionRunner结束后才能继续执行
	static CountDownLatch end;
	
	public static void main(String[] args) throws InterruptedException {
		
		int threadCount = 30; //线程数量，可以修改线程数量进行观察
		
		end = new CountDownLatch(threadCount);
		
		int count = 20;
		
		AtomicInteger got = new AtomicInteger();
		
		AtomicInteger notgot = new AtomicInteger();
		
		for(int i=0;i<threadCount;i++) {
			Thread thread = new Thread(new ConnectionRunner(count,got,notgot),"ConnectionRunnerThread");
			thread.start();
		}
		start.countDown();
		end.await();
		
		System.out.println("total invoke:"+(threadCount * count));
		
		System.out.println("got connection:"+got);
		
		System.out.println("not got connection:"+notgot);
	}
	
	static class ConnectionRunner implements Runnable{

		int count;
		
		AtomicInteger got;
		
		AtomicInteger notgot;
		
		public ConnectionRunner(int count,AtomicInteger got,AtomicInteger notgot) {
			this.count = count;
			this.got = got;
			this.notgot = notgot;
		}
		
		@Override
		public void run() {
			try {
				start.await();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			while(count > 0) {
				//从线程池中获取连接，如果1000ms内无法获取到，将会返回null
				//分别统计连接获取的数量got 和未获取到的数量notgot
					try {
						Connection connection = pool.fetchConnection(1000);
						if(connection != null) {
							try {
								connection.createStatement();
								connection.commit();
							} catch (SQLException e) {
								e.printStackTrace();
							}finally {
								pool.releaseConnection(connection);
								got.incrementAndGet();
							}
						}else {
							notgot.incrementAndGet();
						}
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally {
						count--;
					}
				
			}
			end.countDown();
			
		}
		
	}

}
