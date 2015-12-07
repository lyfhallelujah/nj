package com.example.sound;

public class music {
	private String name;
	private String url;
	
	public music(String name,String url){
		this.name = name;
		this.url = url;
	}
	
	public String getName(){
		return name;
	}
	
	public String getUrl(){
		return url;
	}
}
