package com.smallest.tolearn.utils;

import com.smallest.tolearn.dao.BaseTask;

public class TaskFactory {
	public static BaseTask makeTask(String title, String[] tag, String src,
			String desc) {
		BaseTask task = new BaseTask();
		task.setTitle(title);
		task.setTag(tag);
		task.setSrc(src);
		task.setDesc(desc);
		return task;
	}
}
