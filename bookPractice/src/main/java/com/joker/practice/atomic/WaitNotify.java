package com.joker.practice.atomic;

import java.util.ArrayList;
import java.util.List;
/**
 * 使用wait() notify()实现生产者和消费者模式
 * @author Joker
 *
 */
public class WaitNotify {

	public static void main(String[] args) throws Exception {

		Product product = new Product(); // 仓库

		// Thread waitThread = new Thread(new ConsumerThread(product), "消费者线程");
		// waitThread.start();// 消费者线程

		// Thread notiyThread = new Thread(new ProduceThread(product), "生产者线程");
		// notiyThread.start();// 生产者线程

		// 消费者集合
		List<Thread> clist = new ArrayList<Thread>();

		// 生产者集合
		List<Thread> plist = new ArrayList<Thread>();

		// 构建消费者
		for (int i = 0; i < 5; i++) {
			Thread cThread = new Thread(new ConsumerThread(product), "消费者线程-" + i);
			clist.add(cThread);
		}

		// 构建生产者
		for (int j = 0; j < 5; j++) {
			Thread pThread = new Thread(new ProduceThread(product), "生产者线程-" + j);
			plist.add(pThread);
		}

		// 启动所有线程
		List<Thread> lists = new ArrayList<Thread>();
		lists.addAll(clist);
		lists.addAll(plist);

		for (Thread list : lists) {
			list.start();
		}

	}

	static class ConsumerThread implements Runnable {

		private Product product;

		public ConsumerThread(Product product) {
			this.product = product;
		}

		@Override
		public void run() {
			// 消费者线程首先拿到lock对象的锁。进入同步代码块
			synchronized (product) {

				while (true) {

					if (product.i == 0) {

						try {
							SleepUtils.sleep(500);
							product.wait();// 没有东西继续等待
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {

						product.consumer();

						System.out.println("【" + Thread.currentThread().getName() + "】消费后【" + product.i + "】");

						SleepUtils.sleep(500);

						product.notifyAll();// 消费,通知生产者。
					}

				}

			}

		}

	}

	static class ProduceThread implements Runnable {
		private Product product;

		public ProduceThread(Product product) {
			this.product = product;
		}

		@Override
		public void run() {

			synchronized (product) {
				// 生产者线程获取lock对象的锁。进入同步代码块

				while (true) {
					if (product.i == 1) {
						try {
							SleepUtils.sleep(500);
							product.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {

						Product.produce();

						System.out.println("【" + Thread.currentThread().getName() + "】生产后【" + product.i + "】");

						SleepUtils.sleep(500);

						product.notifyAll();
					}

				}

			}

		}

	}

	static class Product {

		private static int i = 0; // 货物

		public static void consumer() {
			i = i - 1;
		}

		public static void produce() {
			i = i + 1;
		}
	}
}
