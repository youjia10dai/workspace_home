package com.atgui;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

public class TestZookeeper {

	//连接集群的方式
	private String connectString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
	private String connectString1 = "127.0.0.1:2181";
	
	//连接zk的超时时间
	private int seesionTimeout = 2000;
	
	private ZooKeeper client;
	
	@Before
	public void init() throws IOException{
		client = new ZooKeeper(connectString1, seesionTimeout, new Watcher(){

			@Override
			public void process(WatchedEvent arg0) {
				List<String> children;
				try {
					children = client.getChildren("/", true);
					for (String string : children) {
						System.out.println(string);
					}
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});
	} 
	
	//创建节点
	@Test
	public void createNode() throws Exception {
		String string = client.create("/atgui", "ssssddd".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(string);
	}
	
	//获取子节点并监控节点的变化
	@Test
	public void getDataAndWatch() throws Exception {
		List<String> children = client.getChildren("/", true);
		for (String string : children) {
			System.out.println(string);
		}
		
		Thread.sleep(Long.MAX_VALUE);
	} 
	
	//判断节点是否存在
	@Test
	public void exist() throws Exception {
		Stat exists = client.exists("/atgui", false);
		System.out.println(exists == null ? "不存在" : "存在");
		
	} 
	
	
}
