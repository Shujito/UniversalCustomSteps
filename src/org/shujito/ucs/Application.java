package org.shujito.ucs;

import java.io.File;
import java.io.IOException;

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

public class Application
{
    public static final String TAG = Application.class.getSimpleName();
    
    public static void main(String[] args) throws Exception
    {
        // create jetty server
        Server server = new Server(1337);
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
