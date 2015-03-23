package com.smallest.tolearn.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.smallest.tolearn.R;
import com.smallest.tolearn.dao.BaseTask;
import com.smallest.tolearn.db.TaskDBHelper;
import com.smallest.tolearn.utils.MyToast;
import com.smallest.tolearn.utils.TaskFactory;
import com.smallest.tolearn.utils.TaskInputCheck;

public class AddTaskActivity extends Activity {
	private Button finishBtn;
	private EditText titleET;
	private EditText descET;
	private String title;
	private String description;
	private String tag;
	private String src;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		initView();
		getActionBar().hide();
	}

	private void initView() {
		finishBtn = (Button) findViewById(R.id.finish_btn);
		titleET = (EditText) findViewById(R.id.title_et);
		descET = (EditText) findViewById(R.id.desc_et);
		finishBtn.setOnClickListener(new FinishBtnOnClickListen());
	}

	private class FinishBtnOnClickListen implements View.OnClickListener {
		@Override
		public void onClick(View arg0) {
			createNewTask();
		}
	}

	private boolean createNewTask() {
		title = titleET.getText().toString();
		description = descET.getText().toString();
		BaseTask task = TaskFactory.makeTask(title, tag, src, description);
		TaskInputCheck.CheckResult checkResult = TaskInputCheck
				.isInputAvailable(getBaseContext(), task);
		switch (checkResult.getResultCode()) {
		case 0:
			TaskDBHelper.insertInfo(getBaseContext(), task);
			finish();
			break;
		case -1:
			MyToast.makeText(getBaseContext(), checkResult.getResultDetail());
		}
		return true;
	}
}
