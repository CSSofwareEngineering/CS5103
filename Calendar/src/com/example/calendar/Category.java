package com.example.calendar;

import java.io.Serializable;

public class Category implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8742712942580730361L;

	private String categoryName = null;
	private int categoryColor = -1;
	
	public Category(String categoryName, int categoryColor) {
		this.categoryName = categoryName;
		this.categoryColor = categoryColor;
	}
	
	public String getName() {
		return categoryName;
	}
	
	public void setName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public int getColor() {
		return categoryColor;
	}
	
	public void setColor(int categoryColor) {
		this.categoryColor = categoryColor;
	}
}
