/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package entity;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import sun.util.logging.resources.logging;
import util.PropertyUtils;

/** 
 * @description 暂定ip地址和用户名都是唯一的,并且一一对应 
 * @author 陈吕奖
 * @date 2018-12-13 
 */
public class Friend {

    public Map<String, String> friendByName;//wangli:10.41.105.6
    public Map<String, String> friendByIp;//10.41.105.6:wangli
    
    private Friend(Map<String, String> map, User user){
        friendByName = new LinkedHashMap<String, String>();
        friendByIp = new LinkedHashMap<String, String>();
        Collection<String> values = map.values();
        friendByName.put("all", "all");
        friendByIp.put("all", "all");
        for(String value : values) {
            String[] split = value.split(":");
            if(split[0].equals(user.ipAddressName)){
                friendByIp.put(split[0], split[1]);
                continue;
            }
            friendByName.put(split[1], split[0]);
            friendByIp.put(split[0], split[1]);
        }
    }
    
    public static Friend createFriend(User user){
        Map<String, String> loadProperties = PropertyUtils.loadProperties("friends");
        Friend friend = new Friend(loadProperties, user);
        return friend;
    }
    
    public String getFriendNameByIpAdderName(String ipAdderName){
        return friendByIp.get(ipAdderName);
    }
    
    public String getFriendIpAdderNameByName(String name){
        return friendByName.get(name);
    }
    
    public String[] getAllNames(){
        return friendByIp.values().toArray(new String[]{});
    }
    
}
