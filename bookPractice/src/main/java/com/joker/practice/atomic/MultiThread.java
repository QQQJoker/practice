package com.joker.practice.atomic;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
/**
 * 使用jmx查看一个普通的Java进程包含那些线程
 * @author joker
 *
 */
public class MultiThread {

	public static void main(String[] args) {
		//获取Java现场管理MXBean
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		//不需要获取同步的monitor和synchronizer信息，仅获取现场和现场堆栈信息
		ThreadInfo[] threadinfos = threadMXBean.dumpAllThreads(false, false);
		//遍历线程信息，打印线程ID和线程名称
		for(ThreadInfo thread : threadinfos) {
			System.out.println("["+thread.getThreadId()+"] = "+thread.getThreadName());
		}
	}

}
