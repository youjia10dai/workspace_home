package service;

import java.io.IOException;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;

/**
 * 
 * 服务端在zk上注册服务信息
 * @author HP
 *
 */
public class ZookeeperService {

	//连接集群的方式
	private static String connectString = "192.168.33.204:2183,192.168.33.204:2184,192.168.33.204:2185";
	
	public static void main(String[] args) throws Exception {
		
		//1. 获取zk客户端对象
		ZooKeeper client = getZKClient();
		
		//2. 在zk上注册服务器信息
		client.create("/services/service", args[0].getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		
		//3. 进行业务处理
		
		bussies();
		
	}

	private static void bussies() {
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static ZooKeeper getZKClient() throws Exception {
		ZooKeeper client = new ZooKeeper(connectString, 2000, null);
		return client;
	}
	
}
