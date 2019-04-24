/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package socket.threadControl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerClientThreadControl {
    
    /** 
     * @fields ACCEPTORCOUNT �������˽����̵߳�����
     */ 
    public static final int ACCEPTORCOUNT = 20; 
    
    public static ExecutorService acceptorService = Executors.newFixedThreadPool(ACCEPTORCOUNT);
    
    public synchronized static void startAcceptorThread(Runnable task){
        acceptorService.execute(task);
    }
}