package com.example.services;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.example.services.Message.MessageDecoder;
import com.example.services.Message.MessageEncoder;

@ServerEndpoint( 
	value = "/broadcast", 
	encoders = { MessageEncoder.class }, 
	decoders = { MessageDecoder.class } 
) 
public class BroadcastServerEndpoint {
	private static final Set< Session > sessions = Collections.synchronizedSet( new HashSet< Session >() );	
			
	@OnOpen
	public void onOpen( final Session session ) {
		sessions.add( session );
	}

	@OnClose
	public void onClose( final Session session ) {
		sessions.remove( session );
	}

	@OnMessage
	public void onMessage( final Message message, final Session client ) throws IOException, EncodeException {
		for( final Session session: sessions ) {
			session.getBasicRemote().sendObject( message );
		}
	}
}