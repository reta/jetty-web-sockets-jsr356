package com.example.services;

import java.io.StringReader;
import java.util.Collections;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class Message {
	public static class MessageEncoder implements Encoder.Text< Message > {
		@Override
		public void init( final EndpointConfig config ) {
		}
		
		@Override
		public String encode( final Message message ) throws EncodeException {
			return Json.createObjectBuilder()
					.add( "username", message.getUsername() )
					.add( "message", message.getMessage() )
					.build()
					.toString();
		}
		
		@Override
		public void destroy() {
		}
	}
	
	public static class MessageDecoder implements Decoder.Text< Message > {
		private JsonReaderFactory factory = Json.createReaderFactory( Collections.< String, Object >emptyMap() );
		
		@Override
		public void init( final EndpointConfig config ) {
		}
		
		@Override
		public Message decode( final String str ) throws DecodeException {
			final Message message = new Message();
			
			try( final JsonReader reader = factory.createReader( new StringReader( str ) ) ) {
				final JsonObject json = reader.readObject();
				message.setUsername( json.getString( "username" ) );
				message.setMessage( json.getString( "message" ) );
			}
			
			return message;
		}
		
		@Override
		public boolean willDecode( final String str ) {
			return true;
		}
		
		@Override
		public void destroy() {
		}
	}
	
	private String username;
	private String message;
	
	public Message() {
	}
	
	public Message( final String username, final String message ) {
		this.username = username;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getUsername() {
		return username;
	}

	public void setMessage( final String message ) {
		this.message = message;
	}
	
	public void setUsername( final String username ) {
		this.username = username;
	}
}