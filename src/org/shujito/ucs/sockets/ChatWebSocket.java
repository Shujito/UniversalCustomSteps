package org.shujito.ucs.sockets;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class ChatWebSocket
{
	public static final String TAG = ChatWebSocket.class.getSimpleName();
	private static final Set<Session> mSessions = new HashSet<>();
	private Session mSession;
	private String user = UUID.randomUUID().toString();
	
	@OnWebSocketConnect
	public void onConnect(Session session) throws Exception
	{
		System.out.println("somebody joined");
		this.broadcast(this.user + " joined!");
		this.mSession = session;
		mSessions.add(session);
	}
	
	@OnWebSocketMessage
	public void onMessage(String message) throws Exception
	{
		//System.out.println("message:'" + message + "'");
		if (message.startsWith("/nick "))
		{
			String oldUser = this.user;
			this.user = message.substring(5);
			//System.out.println("message:'" + this.user + "'");
			this.mSession.getRemote().sendString("you are now " + this.user);
			this.broadcast(oldUser + " is now " + this.user, true);
			return;
		}
		this.broadcast(this.user + " says:'" + message + "'");
	}
	
	@OnWebSocketClose
	public void onClose(int code, String reason) throws Exception
	{
		System.out.println("code:'" + code + "' reason:'" + reason + "'");
		this.broadcast(this.user + " left the chat");
		mSessions.remove(this.mSession);
	}
	
	@OnWebSocketError
	public void onError(Throwable error) throws Exception
	{
		System.out.println("error:'" + error.toString() + "'");
	}
	
	public void broadcast(String message) throws Exception
	{
		this.broadcast(message, false);
	}
	
	public void broadcast(String message, boolean exclude) throws Exception
	{
		for (Session session : mSessions)
		{
			if (!session.isOpen() || (exclude && this.mSession == session))
				continue;
			session.getRemote().sendString(message);
		}
	}
}
