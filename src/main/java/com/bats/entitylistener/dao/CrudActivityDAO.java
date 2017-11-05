package com.bats.entitylistener.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bats.entitylistener.entity.CrudActivity;

public interface CrudActivityDAO extends JpaRepository<CrudActivity, Integer>
{
	@Query("SELECT coalesce(max(ch.activityId), 0) FROM CrudActivity ch")
	Integer getMaxId();
	
	List<CrudActivity> findByActivityIdGreaterThan(Integer activityId);
}
