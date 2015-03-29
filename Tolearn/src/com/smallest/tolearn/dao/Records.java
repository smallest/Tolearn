package com.smallest.tolearn.dao;

import java.util.ArrayList;

/**
 * record a task operate and the time
 *  */
public class Records {
	/**
	 * operate
	 * 0: pop to todo list
	 * 1: unload task to repo
	 * 2: move task to trash
	 * 3: move task from trash to repo
	 * */
	private ArrayList<Record> recordList;
	public boolean addRecord(int operation){
		recordList.add(new Record(operation,String.valueOf(System.currentTimeMillis())));
		return true;
	}
	
}
