/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.serv;

import net.ymate.platform.core.YMP;
import net.ymate.platform.serv.nio.INioClientCfg;
import net.ymate.platform.serv.nio.INioCodec;
import net.ymate.platform.serv.nio.INioServerCfg;
import net.ymate.platform.serv.nio.client.NioClient;
import net.ymate.platform.serv.nio.client.NioClientListener;
import net.ymate.platform.serv.nio.datagram.NioUdpClient;
import net.ymate.platform.serv.nio.datagram.NioUdpListener;
import net.ymate.platform.serv.nio.datagram.NioUdpServer;
import net.ymate.platform.serv.nio.server.NioServer;
import net.ymate.platform.serv.nio.server.NioServerListener;

/**
 * @author 刘镇 (suninformation@163.com) on 15/10/15 上午10:22
 * @version 1.0
 */
public interface IServ {

    String MODULE_NAME = "serv";

    /**
     * @return 返回所属YMP框架管理器实例
     */
    YMP getOwner();

    /**
     * @return 返回服务模块配置对象
     */
    IServModuleCfg getModuleCfg();

    /**
     * @param clazz 服务端监听器类型
     * @param <T>   返回值类型声明
     * @return 返回匹配clazz类型的服务端对象, 若不存在则返回null
     */
    <T extends IServer> T getServer(Class<? extends IListener> clazz);

    /**
     * @param clazz 客户端监听器类型
     * @param <T>   返回值类型声明
     * @return 返回匹配clazz类型的客户端对象, 若不存在则返回null
     */
    <T extends IClient> T getClient(Class<? extends IListener> clazz);

    /**
     * 注册服务端
     *
     * @param listenerClass 服务监听接口类型
     */
    void registerServer(Class<? extends IListener> listenerClass);

    void registerServer(String serverName, Class<? extends IServer> implClass, Class<? extends ICodec> codec, Class<? extends IListener> listenerClass);

    <LISTENER extends NioServerListener, CODEC extends INioCodec> NioServer buildNioServer(INioServerCfg serverCfg, CODEC codec, LISTENER listener);

    <LISTENER extends NioUdpListener, CODEC extends INioCodec> NioUdpServer buildNioUdpServer(INioServerCfg serverCfg, CODEC codec, LISTENER listener);

    /**
     * 注册客户端
     *
     * @param listenerClass 服务监听接口类型
     */
    void registerClient(Class<? extends IListener> listenerClass);

    void registerClient(String clientName, Class<? extends IClient> implClass, Class<? extends ICodec> codec, Class<? extends IListener> listenerClass, Class<? extends IReconnectService> reconnectClass, Class<? extends IHeartbeatService> heartbeatClass);

    <LISTENER extends NioClientListener, CODEC extends INioCodec> NioClient buildNioClient(INioClientCfg clientCfg, CODEC codec, IReconnectService reconnect, IHeartbeatService heartbeat, LISTENER listener);

    <LISTENER extends NioUdpListener, CODEC extends INioCodec> NioUdpClient buildNioUdpClient(INioClientCfg clientCfg, CODEC codec, IReconnectService reconnect, IHeartbeatService heartbeat, LISTENER listener);

    /**
     * 启动所有Server端和Client端服务
     *
     * @throws Exception 可能产生的异常
     */
    void startup() throws Exception;
}
