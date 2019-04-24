/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package socket.threadControl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerClientThreadControl {
    
    /** 
     * @fields ACCEPTORCOUNT 服务器端接收线程的数量
     */ 
    public static final int ACCEPTORCOUNT = 20; 
    
    public static ExecutorService acceptorService = Executors.newFixedThreadPool(ACCEPTORCOUNT);
    
    public synchronized static void startAcceptorThread(Runnable task){
        acceptorService.execute(task);
    }
}