package com.example.ws;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.example.config.AppConfig;

public class ServerStarter  {
    public static void main( String[] args ) throws Exception {
        Server server = new Server(8080);

        // Create the 'root' Spring application context
        final ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        context.addEventListener(new ContextLoaderListener());
        context.setInitParameter("contextClass",AnnotationConfigWebApplicationContext.class.getName());
        context.setInitParameter("contextConfigLocation",AppConfig.class.getName());

        // Create default servlet (servlet api required)
        // The name of DefaultServlet should be set to 'defualt'.
        final ServletHolder defaultHolder = new ServletHolder( "default", DefaultServlet.class );
        defaultHolder.setInitParameter( "resourceBase", System.getProperty("user.dir") );
        context.addServlet( defaultHolder, "/" );

        server.setHandler(context);
        WebSocketServerContainerInitializer.configureContext(context);

        server.start();
        server.join();	
    }
}
