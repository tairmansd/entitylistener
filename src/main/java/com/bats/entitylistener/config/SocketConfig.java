package com.bats.entitylistener.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@ServerEndpoint(value = "/ws/entitylistener", configurator = SpringConfigurator.class)
public class SocketConfig {
	
	@Resource(name="entitySessionMap")
	private Map<String, List<Session>> entitySessionMap;
	
	@OnMessage
	public String onMessage(Session session, String data) throws JsonParseException, JsonMappingException, IOException 
	{	
		return null;
	}
	
	@OnOpen
	public void open(Session session) throws IOException {
		Map<String, String> requestMap = session.getPathParameters();
		if(requestMap.containsKey("entity")) {
			String entityName = requestMap.get("entity");
			if(entitySessionMap.containsKey(entityName)) {
				entitySessionMap.get(entityName).add(session);
			} else {
				List<Session> sessionList = new ArrayList<>();
				sessionList.add(session);
				entitySessionMap.put(entityName, sessionList);
			}
		} else {
			List<Session> sessionList = new ArrayList<>();
			sessionList.add(session);
			entitySessionMap.put("ALL", sessionList);
		}
	}

	@OnError
	public void error(Session session, Throwable t) {
		//
	}

	@OnClose
	public void closedConnection(Session session) {
	}
	
}
