package com.smallest.tolearn.evaluation;

import java.util.Calendar;

import com.smallest.tolearn.dao.BaseTask;

public class Remark {
	int duration;
	int score;
	IRemark iRemark;
	Calendar createTime = Calendar.getInstance();
	Calendar finishTime;

	public void setRemarkStrategy(IRemark iRemark) {
		this.iRemark = iRemark;
	}

	public int getTaskScore(BaseTask task) {
		return iRemark.getTaskScore(task);
	}

	public int getWeekScore(int nWeek) {
		return iRemark.getWeekScore(nWeek);
	}

	public int getMonthScore(int nMonth) {
		return iRemark.getMonthScore(nMonth);
	}

	public int getDailyScore(int nDay) {
		return iRemark.getDayScore(nDay);
	}

}
