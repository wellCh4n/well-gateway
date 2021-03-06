package com.wellch4n.boot;

import com.wellch4n.boot.config.BloomFilterConfig;
import com.wellch4n.boot.config.JpaConfig;
import com.wellch4n.boot.config.RedisConfig;
import com.wellch4n.service.engine.GatewayServer;
import com.wellch4n.service.env.EnvironmentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Method;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/08 12:57
 * 下周我就努力工作
 */

public class GatewayMain {

    private static Logger logger = LoggerFactory.getLogger(GatewayMain.class);

    public static void main(String[] args) throws Exception {
        logger.info("Gateway start...");

        logger.info("Load bean...");
        ApplicationContext context = new AnnotationConfigApplicationContext(
                EnvironmentContext.class,
                BloomFilterConfig.class,
                JpaConfig.class,
                RedisConfig.class);

        // 启动 Gateway 转发
        Thread serverThread = new Thread(new GatewayServer(context));
        serverThread.start();

        Class<?> clazz = Class.forName("com.wellch4n.web.WebService");
        Object obj = clazz.getConstructor(ApplicationContext.class).newInstance(context);
        Method method = clazz.getMethod("initService");
        method.invoke(obj);
    }
}
