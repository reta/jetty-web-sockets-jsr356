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
		Server server = new Server( 8080 );
        
		// Create the 'root' Spring application context
 		final ServletHolder servletHolder = new ServletHolder( new DefaultServlet() );
 		final ServletContextHandler context = new ServletContextHandler();

 		context.setContextPath( "/" );
 		context.addServlet( servletHolder, "/*" );
 		context.addEventListener( new ContextLoaderListener() ); 		
 		context.setInitParameter( "contextClass", AnnotationConfigWebApplicationContext.class.getName() );
 		context.setInitParameter( "contextConfigLocation", AppConfig.class.getName() );
 		
        server.setHandler( context );
        WebSocketServerContainerInitializer.configureContext( context );        
        
        server.start();
        server.join();	
    }
}