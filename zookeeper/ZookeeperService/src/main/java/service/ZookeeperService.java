package service;

import java.io.IOException;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;

/**
 * 
 * �������zk��ע�������Ϣ
 * @author HP
 *
 */
public class ZookeeperService {

	//���Ӽ�Ⱥ�ķ�ʽ
	private static String connectString = "192.168.33.204:2183,192.168.33.204:2184,192.168.33.204:2185";
	
	public static void main(String[] args) throws Exception {
		
		//1. ��ȡzk�ͻ��˶���
		ZooKeeper client = getZKClient();
		
		//2. ��zk��ע���������Ϣ
		client.create("/services/service", args[0].getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		
		//3. ����ҵ����
		
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
