package com.smallest.tolearn.utils;

import android.content.Context;

import com.smallest.tolearn.R;
import com.smallest.tolearn.dao.BaseTask;

public class TaskInputCheck {
	private static TaskInputCheck instance;

	private TaskInputCheck() {
	}

	public static CheckResult isInputAvailable(Context context, BaseTask task) {
		if (instance == null) {
			instance = new TaskInputCheck();
		}
		if (task.getTitle() == null || task.getTitle().isEmpty()) {
			return instance.new CheckResult(-1, context.getResources()
					.getString(R.string.no_title_toast));
		} else {
			return instance.new CheckResult(0, "");
		}
	}

	public class CheckResult {
		private int resultCode = 0;
		private String resultDetail = "";

		public CheckResult(int resultCode, String resultDetail) {
			this.resultCode = resultCode;
			this.resultDetail = resultDetail;
		}

		public int getResultCode() {
			return resultCode;
		}

		public String getResultDetail() {
			return resultDetail;
		}

	}
}
