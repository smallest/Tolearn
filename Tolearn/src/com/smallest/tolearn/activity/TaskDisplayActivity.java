package com.smallest.tolearn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.smallest.tolearn.R;
import com.smallest.tolearn.dao.BaseTask;
import com.smallest.tolearn.db.TaskDBHelper;
import com.smallest.tolearn.utils.MyConstants;

public class TaskDisplayActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_taskdisplay);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();
		String tid = intent.getStringExtra(MyConstants.TASK_ID);
		BaseTask task = TaskDBHelper.getTaskFromId(this, tid);
		if (task == null) {
			Log.d(MyConstants.UNEXPECTED_ERROR, "db invalid id");
			finish();
		}
		TextView titleTv = (TextView) findViewById(R.id.title_tv);
		TextView descTv = (TextView) findViewById(R.id.desc_tv);
		titleTv.setText(task.getTitle());
		descTv.setText(task.getDesc());
	}
}
