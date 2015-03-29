package com.smallest.tolearn.dao;

public class Record{
	private int typeOfOperation;
	private String timestamp;
	public Record(int operation,String timestamp){
		this.typeOfOperation=operation;
		this.timestamp=timestamp;
	}
	public int getTypeOfOperation() {
		return typeOfOperation;
	}
	public String getTimestamp() {
		return timestamp;
	}
}
