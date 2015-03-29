package com.smallest.tolearn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.smallest.tolearn.R;
import com.smallest.tolearn.dao.BaseTask;
import com.smallest.tolearn.utils.MyConstants;
import com.smallest.tolearn.utils.MyToast;
import com.smallest.tolearn.utils.TaskFactory;
import com.smallest.tolearn.utils.TaskInputCheck;
import com.smallest.tolearn.utils.TaskManager;

public class AddTaskActivity extends Activity {
	private Button finishBtn;
	private EditText titleET;
	private EditText descET;
	private String title;
	private String description;
	private String[] tagArray;
	private String src;
	private ImageButton tagImageBtn;
	private TextView tagNameTv;
	private static final int TAG_REQUEST_CODE = 0;

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
		tagImageBtn = (ImageButton) findViewById(R.id.tag_btn);
		tagImageBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddTaskActivity.this,
						EditTagActivity.class);
				if (tagArray != null && tagArray.length > 0) {
					intent.putExtra(MyConstants.TAG_EDIT, tagArray);
				}
				startActivityForResult(intent, TAG_REQUEST_CODE);
			}
		});
		tagNameTv = (TextView) findViewById(R.id.add_task_tag_tv);
		descET = (EditText) findViewById(R.id.desc_et);
		finishBtn.setOnClickListener(new FinishBtnOnClickListen());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == TAG_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				tagArray = data
						.getStringArrayExtra(MyConstants.TAG_EDIT);
				if (tagArray != null && tagArray.length > 0) {
					tagNameTv.setText(String.valueOf(tagArray.length));
				}
			}
		}
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
		BaseTask task = TaskFactory.makeTask(title, tagArray, src, description);
		TaskInputCheck.CheckResult checkResult = TaskInputCheck
				.isInputAvailable(getBaseContext(), task);
		switch (checkResult.getResultCode()) {
		case 0:
			TaskManager taskManager = TaskManager
					.getInstance(getApplicationContext());
			if (!taskManager.addTask(task)) {
				MyToast.makeText(getApplicationContext(), getResources()
						.getString(R.string.add_task_failed));
			} else {
				finish();
			}
			break;
		case -1:
			MyToast.makeText(getBaseContext(), checkResult.getResultDetail());
		}
		return true;
	}
}
