package com.igniubi.mapper.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;

/**
 * 测试连接
 * <p>
 *
 * @author 徐擂
 * @version 1.0.0
 * @date 2018/12/23
 */
public class TestConnectUtil {

    private static final Logger log = LoggerFactory.getLogger(TestConnectUtil.class);

    /**
     * 测试地址端口能否连接成功
     *
     * @param ip
     * @param port
     * @return
     */
    public static boolean isAccessable(String ip, Integer port) {
        Socket client = null;
        try{
            client = new Socket(ip, port);
            log.info("ip:{},port:{} 测试连接成功", ip, port);
            client.close();
        }catch(Exception e){
            log.info("ip:{},port:{} 测试连接失败", ip, port);
            return false;
        }
        return true;
    }
}
