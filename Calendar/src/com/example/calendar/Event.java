package com.example.calendar;

import java.io.Serializable;
import java.util.Calendar;

public class Event implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3380350146373983638L;
	private Calendar eventStartTime, eventEndTime;
	private String eventName;
	private String eventDescription;
	private String eventLocation;
	private int eventCategory;
	private int eventRecurrence;
	
	public Event() {
		
	}
	
	public Event(Event eventItem) {
		this.eventStartTime = eventItem.eventStartTime;
		this.eventEndTime = eventItem.eventEndTime;
		this.eventCategory = eventItem.eventCategory;
		this.eventDescription = eventItem.eventDescription;
		this.eventLocation = eventItem.eventLocation;
		this.eventRecurrence = eventItem.eventRecurrence;
	}
	
	public void setStartTime(Calendar eventStart) {
		this.eventStartTime = eventStart;
	}
	
	public Calendar getStartTime() {
		return this.eventStartTime;
	}
	
	public void setEndTime(Calendar eventEnd) {
		this.eventEndTime = eventEnd;
	}
	
	public Calendar getEndTime() {
		return this.eventEndTime;
	}
	
	public void setName(String eventName) {
		this.eventName = eventName;
	}
	
	public String getName() {
		return this.eventName;
	}
	
	public void setDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	
	public String getDescription() {
		return this.eventDescription;
	}
	
	public void setLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}
	
	public String getLocation() {
		return this.eventLocation;
	}
	
	public void setCategory(int eventCategory) {
		this.eventCategory = eventCategory;
	}
	
	public int getCategory() {
		return this.eventCategory;
	}
	
	public void setRecurrence(int eventRecurrence) {
		this.eventRecurrence = eventRecurrence;
	}
	
	public int getRecurrence() {
		return this.eventRecurrence;
	}
}
