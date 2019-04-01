package entity;

import java.util.Map;

/** 
 * @description ÓÃ»§
 * @author ³ÂÂÀ½±
 * @date 2018-12-07 
 */
public class User {
    
	public String name;
    
    public String ipAddressName;
    
	public User() {
		super();
	}
    
    private User(String name, String IpAddressName){
        this.name = name;
        this.ipAddressName = IpAddressName;
    }
    
    public User(String name, String ipAddressName, int type){
        this(name,ipAddressName);
    }
    
    public static User createUser(Map<String, String> map){
        return new User(map.get("userName"), map.get("ipAddressName"));
    }
    
    public static User createUser(String userMessage){
        String[] userInfos = userMessage.split(",");
        return new User(userInfos[0], userInfos[1]);
    }
}
