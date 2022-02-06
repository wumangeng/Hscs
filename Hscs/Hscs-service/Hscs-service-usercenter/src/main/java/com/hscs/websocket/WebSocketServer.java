package com.hscs.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * //ws://localhost:端口/websocket/x
 * @author Painter
 */
@ServerEndpoint(value = "/websocket/{name}")
@Component
public class WebSocketServer {


	/**与某个客户端的会话*/
	private Session session;

	/**客户端标志(Id)*/
	private String name;

	/**
	 *  用于存所有的连接服务的客户端，这个对象存储是安全的
	 */
	private static ConcurrentHashMap<String, WebSocketServer> webSocketServers = new ConcurrentHashMap<>();




	/**建立链接调用方法*/
	@OnOpen
	public void onOpen(Session session, @PathParam("name") String name) {
		this.session = session;
		this.name = name;
		webSocketServers.put(name,this);
		System.out.println(name+"与服务器端连接成功！");
		System.err.println("[ + ] 建立链接: {}");
	}

	/**关闭连接调用的方法*/
	@OnClose
	public void onClose() {
		webSocketServers.remove(this.name);
		System.out.println(name+"与服务器端断开连接！");
		System.err.println("[ - ] 退出链接: {}");
	}

	/**websocket连接发生错误*/
	@OnError
	public void onError( Throwable error) {
		error.printStackTrace();
		System.out.println(name+"与服务器端连接异常！");
		System.err.println("[ - ] 链接异常: {}");
	}

	@OnMessage
	public void OnMessage(String message){
		System.out.println("[WebSocket] 收到消息："+message);
		//判断是否需要指定发送，具体规则自定义
		if(message.indexOf("TOUSER") == 0){
			String name = message.substring(message.indexOf("TOUSER")+6,message.indexOf(";"));
			AppointSending(name,message.substring(message.indexOf(";")+1,message.length()));
		}else{
			GroupSending(message);
		}

	}

	/**
	 * 群发
	 * @param message
	 */
	public void GroupSending(String message){
		for (String name : webSocketServers.keySet()){
			try {
				webSocketServers.get(name).session.getBasicRemote().sendText(message);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 指定发送
	 * @param name
	 * @param message
	 */
	public void AppointSending(String name,String message) {
		try {
			webSocketServers.get(name).session.getBasicRemote().sendText(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 指定发送
	 * @param name
	 * @param obj
	 */
	public void SendObject(String name,Object obj) {
		try {
			webSocketServers.get(name).session.getBasicRemote().sendObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
