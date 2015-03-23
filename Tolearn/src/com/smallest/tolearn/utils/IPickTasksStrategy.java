package com.smallest.tolearn.utils;

import java.util.List;

import com.smallest.tolearn.dao.BaseTask;

public interface IPickTasksStrategy {
	/**
	 * 从任务数组中选取num个任务*/
	public List<BaseTask> pickTasks(List<BaseTask> taskList, int num);
}
