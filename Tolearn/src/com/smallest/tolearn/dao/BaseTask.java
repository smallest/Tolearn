package com.smallest.tolearn.dao;

import java.util.ArrayList;
import java.util.Calendar;

public class BaseTask {
	Calendar startTime;
	Calendar endTime;
	ArrayList<Tag> tagList;
	Tag tag;
	String description;
	String comment;
	private TaskSource source;
}
