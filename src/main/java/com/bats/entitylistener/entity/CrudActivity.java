package com.bats.entitylistener.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the crud_activity database table.
 * 
 */
@Entity
@Table(name="crud_activity")
@NamedQuery(name="CrudActivity.findAll", query="SELECT c FROM CrudActivity c")
public class CrudActivity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="activity_id")
	private int activityId;

	@Column(name="operation")
	private String operation;

	@Column(name="table_name")
	private String tableName;

	@Column(name="table_ref_id")
	private int tableRefId;

	public CrudActivity() {
	}

	public int getActivityId() {
		return this.activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public int getTableRefId() {
		return this.tableRefId;
	}

	public void setTableRefId(int tableRefId) {
		this.tableRefId = tableRefId;
	}

	public String getOperation()
	{
		return operation;
	}

	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

}