package com.teamup.agencyportal.dao;

import java.util.List;


public interface CommonDAO 
{
	public List<Object> getAllObjectsByClassName(String className,Class cls);
	
	public List<Object> getAllObjectsByQuery(String query,Class cls);
	
	public Object getObjectByID(Class cls,int id);
	
	public Object getObjectByQuery(String query,Class cls);
	
	public void updateByObject(Object object);
	
	public void updateByQuery(String query) ;
	
	public void deleteObject(Object object);
	
	public boolean addObject(Object objects);
	
	public List getAllObjectsByQuery(String query);
	
	public List<Object> getAllObjectsForPaging(String query,Class cls,int startPageIndex,int totalrecords);
}