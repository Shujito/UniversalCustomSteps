package org.shujito.ucs.sockets;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public final class ChatSocketServlet extends WebSocketServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	public void configure(WebSocketServletFactory factory)
	{
		factory.register(ChatWebSocket.class);
	}
}