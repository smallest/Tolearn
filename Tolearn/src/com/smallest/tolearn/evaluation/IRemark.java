package com.smallest.tolearn.evaluation;

import com.smallest.tolearn.dao.BaseTask;

public interface IRemark {
	public int getTaskScore(BaseTask task);//满分100分
	public int getDayScore(int nDay);
	public int getWeekScore(int nWeek);
	public int getMonthScore(int nMonth);
	public int getYearScore(int nYear);
}
