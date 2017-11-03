package com.simplevat.web.filter;

import java.io.Serializable;
import java.util.LinkedList;
import javax.faces.application.ResourceHandler;

public class Breadcrumb implements Serializable{
	
	private LinkedList<String> urlList = new LinkedList<String>();
	
	public void addUri(String uri){
            if(!uri.contains(ResourceHandler.RESOURCE_IDENTIFIER)){
		if(urlList.contains(uri)){
			urlList.remove(uri);
		}
		urlList.add(uri);
            }
	}
	
	public String getCurrentUri(){
		return urlList.element();
	}
	
	public String getPreviousUri(){
		return urlList.get(urlList.size()-2);
	}
	
	public String getPopPreviousUri(){
		return urlList.get(urlList.size()-2);
	}
	
}
