package clinet;

import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * 客户端检测服务端状态
 */
public class ZookeeperClinet {

	//连接集群的方式
	private static String connectString = "192.168.33.204:2183,192.168.33.204:2184,192.168.33.204:2185";
	
	private static ZooKeeper zkClient;
	
	public static void main(String[] args) throws Exception{
		//1. 获取zk客户端
		zkClient = getZKClient();
		watch();
		Thread.sleep(Long.MAX_VALUE);
	}
	
	private static ZooKeeper getZKClient() throws Exception {
		ZooKeeper client = new ZooKeeper(connectString, 2000, null);
		return client;
	}
	
	private static void watch() throws Exception{
		
		//2. 监听指定的路径下数据的变化
		List<String> children = zkClient.getChildren("/services", new Watcher(){

			@Override
			public void process(WatchedEvent arg0) {
				try {
					watch();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		for (String string : children) {
			System.out.println(string);
		}
	}
	
}
