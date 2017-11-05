package com.bats.entitylistener.listeners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bats.entitylistener.dao.CrudActivityDAO;
import com.bats.entitylistener.entity.CrudActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ScheduleListenerDeployer
{
	@Autowired
	private CrudActivityDAO crudActivityDAO;
	
	@Resource(name="entitySessionMap")
	private Map<String, List<Session>> entitySessionMap;
	
	private Integer lastId;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@PostConstruct
	public void init() {
		crudActivityDAO.deleteAllInBatch();
		lastId = crudActivityDAO.getMaxId();
	}
	
	@Scheduled(initialDelay=6000, fixedDelay=5000)
	public void scheduledRun() throws IOException {
		Integer latestId = crudActivityDAO.getMaxId();
		
		System.out.println("Latest id found:"+latestId+" lastid:"+lastId);
		
		if(latestId > lastId) {
			List<CrudActivity> latestChanges = crudActivityDAO.findByActivityIdGreaterThan(lastId);
			
			Map<String, List<CrudActivity>> collectiveMap = new HashMap<String, List<CrudActivity>>();
			
			for (CrudActivity crudActivity : latestChanges)
			{
				if(collectiveMap.containsKey(crudActivity.getTableName())) {
					List<CrudActivity> list = collectiveMap.get(crudActivity.getTableName());
					list.add(crudActivity);
					collectiveMap.put(crudActivity.getTableName(), list);
				} else {
					List<CrudActivity> list = new ArrayList<>();
					list.add(crudActivity);
					collectiveMap.put(crudActivity.getTableName(), list);
				}
			}
			
			for (Entry<String, List<CrudActivity>> entry : collectiveMap.entrySet())
			{
				if(entitySessionMap.containsKey(entry.getKey())) {
					List<Session> sessions = entitySessionMap.get(entry.getKey());
					for (Session session : sessions)
					{
						session.getBasicRemote().sendText(wrapToJson(entry.getValue(), entry.getKey()));
					}
				}
			}
			
			List<Session> sessions = entitySessionMap.get("ALL");
			
			if(sessions != null) {
				for (Session session : sessions)
				{
					session.getBasicRemote().sendText(wrapToJson(latestChanges, "ALL"));
				}				
			}
			
			lastId = latestId; 
		}
	}

	private String wrapToJson(List<CrudActivity> value, String entityType) throws JsonProcessingException
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", value);
		result.put("entityType", entityType);
		return mapper.writeValueAsString(result); 
	}
}
