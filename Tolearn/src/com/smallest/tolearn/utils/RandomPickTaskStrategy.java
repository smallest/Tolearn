package com.smallest.tolearn.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.smallest.tolearn.dao.BaseTask;

public class RandomPickTaskStrategy implements IPickTasksStrategy {

	@Override
	public List<BaseTask> pickTasks(List<BaseTask> taskList, int num) {
		int size = taskList.size();
		if (size <= num) {
			return taskList;
		} else {
			List<BaseTask> mTaskList = new ArrayList<BaseTask>();
			int[] mIndex = randomArray(size, num);
			for (int i : mIndex) {
				Log.d("random", String.valueOf(i));
			}
			for (int i = 0; i < mIndex.length; i++) {
				mTaskList.add(taskList.get(i));
			}
			return mTaskList;
		}
	}

	/**
	 * 从0-N-1中随机选取M个数
	 */
	private static int[] randomArray(int N, int M) {
		if (N <= 0) {
			return null;
		}
		int[] source = new int[N];
		for (int i = 0; i < N; i++) {
			source[i] = i;
		}
		if (M >= N) {
			return source;
		} else {
			int[] result = new int[M];
			Random random = new Random();
			for (int i = 0; i < M; i++) {
				int num = random.nextInt(N - i);
				result[i] = source[num];
				source[num] = source[N - 1 - i];// 保证source数组的[0-(N-1-i)]
			}
			return result;
		}
	}
}
