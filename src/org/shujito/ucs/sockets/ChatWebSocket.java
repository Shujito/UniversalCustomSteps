package org.shujito.ucs.sockets;

import java.util.HashSet;
import java.util.Set;

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
	private Session mSession;
	private static final Set<Session> mSessions = new HashSet<>();
	
	@OnWebSocketConnect
	public void onConnect(Session session) throws Exception
	{
		System.out.println("somebody joined");
		this.mSession = session;
		mSessions.add(session);
	}
	
	@OnWebSocketMessage
	public void onMessage(String message) throws Exception
	{
		System.out.println("message:'" + message + "'");
		for (Session session : mSessions)
		{
			if (!session.isOpen())
				continue;
			session.getRemote().sendString(message);
		}
	}
	
	@OnWebSocketClose
	public void onClose(int code, String reason) throws Exception
	{
		System.out.println("code:'" + code + "' reason:'" + reason + "'");
		mSessions.remove(this.mSession);
	}
	
	@OnWebSocketError
	public void onError(Throwable error)
	{
		System.out.println("error:'" + error.toString() + "'");
	}
}
