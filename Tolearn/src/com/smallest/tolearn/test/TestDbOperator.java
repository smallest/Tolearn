package com.smallest.tolearn.test;

import java.util.Random;

import android.content.Context;

import com.smallest.tolearn.dao.BaseTask;
import com.smallest.tolearn.db.TaskDBHelper;
import com.smallest.tolearn.utils.TaskFactory;

public class TestDbOperator {
	
	/** 添加n个Task到表 */
	public static boolean addManyTask(Context context, int n) {
		Random random = new Random();
		for (int i = 0; i < n; i++) {
			BaseTask task = TaskFactory.makeTask("test" + random.nextInt(100),
					new String[] {"test"}, "test", "test");
			String time = String.valueOf(System.currentTimeMillis());
			task.setTid(time);
			task.setStartTime(time);
			TaskDBHelper.insertInfo(context, task);
		}
		return true;
	}
}
