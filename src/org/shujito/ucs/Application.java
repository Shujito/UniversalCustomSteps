package org.shujito.ucs;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.Rule;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.shujito.ucs.sockets.ChatSocketServlet;

public class Application
{
	public static final String TAG = Application.class.getSimpleName();
	
	public static void __main(String[] args) throws Exception
	{
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite::memory:"))
		{
			try (Statement smt = conn.createStatement())
			{
				smt.setQueryTimeout(30);
				smt.executeUpdate("create table if not exists touhous("
					+ "uuid text not null on conflict fail default (hex(randomblob(16))),"
					+ "name text not null on conflict fail,"
					+ "last_name text not null on conflict fail,"
					+ "primary key (uuid) on conflict replace"
					+ ")");
				//smt.executeUpdate("insert into touhous(name,last_name) values('reimu','hakurei')");
				//smt.executeUpdate("insert into touhous(name,last_name) values('marisa','kirisame')");
				//smt.executeUpdate("insert into touhous(name,last_name) values('wriggle','nightbug')");
				//smt.executeUpdate("insert into touhous(name,last_name) values('patchouli','knowledge')");
			}
			try (PreparedStatement smt = conn.prepareStatement("insert into touhous(name,last_name) values(?,?)"))
			{
				smt.setString(1, "reimu");
				smt.setString(2, "hakurei");
				smt.executeUpdate();
				smt.setString(1, "marisa");
				smt.setString(2, "kirisame");
				smt.executeUpdate();
				smt.setString(1, "sakuya");
				smt.setString(2, "izayoi");
				smt.executeUpdate();
				smt.setString(1, "meiling");
				smt.setString(2, "hong");
				smt.executeUpdate();
			}
			try (Statement smt = conn.createStatement())
			{
				try (ResultSet rs = smt.executeQuery("select * from touhous"))
				{
					while (rs.next())
					{
						System.out.println("------");
						System.out.println("id: '" + rs.getString("uuid") + "'");
						System.out.println("name: '" + rs.getString("name") + "'");
						System.out.println("last name: '" + rs.getString("last_name") + "'");
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		// create jetty server
		Server server = new Server(0xDAFE);
		// rewriter
		RewriteHandler rewriteHandler = new RewriteHandler();
		rewriteHandler.addRule(new Rule() {
			@Override
			public String matchAndApply(String target, HttpServletRequest request, HttpServletResponse response) throws IOException
			{
				if (target.startsWith("/api"))
					return null;
				File resource = new File("public" + target);
				if (resource.exists())
					return null;
				return "/";
			}
		});
		// file server
		final ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setWelcomeFiles(new String[] { "index.html" });
		resourceHandler.setResourceBase("public");
		// make a context for servlets
		ServletContextHandler serverContextHandler = new ServletContextHandler(server, "/api", ServletContextHandler.SESSIONS);
		// api errors
		serverContextHandler.setErrorHandler(new ApiErrorHandler());
		// sock
		serverContextHandler.addServlet(ChatSocketServlet.class, "/chat");
		// make a holder for servlets
		ServletHolder servletHolder = serverContextHandler.addServlet(ServletContainer.class, "/*");
		servletHolder.setInitOrder(0);
		servletHolder.setInitParameter(ServerProperties.PROVIDER_PACKAGES, "org.shujito.ucs.controllers");
		// put handlers on a list so all can be used
		HandlerList handlerList = new HandlerList();
		handlerList.setHandlers(new Handler[] {
			rewriteHandler,
			resourceHandler,
			serverContextHandler,
			new DefaultHandler()
		});
		// use them on the server
		server.setHandler(handlerList);
		// have the server do what it does best
		server.start();
		server.join();
	}
}
