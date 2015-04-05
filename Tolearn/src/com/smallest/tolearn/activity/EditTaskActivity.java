package com.smallest.tolearn.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.smallest.tolearn.utils.TaskInputCheck;
import com.smallest.tolearn.utils.TaskManager;

public class EditTaskActivity extends Activity {
	private Button finishBtn;
	private EditText titleET;
	private EditText descET;
	private String title;
	private String description;
	private String[] tagArray;
	private String src;
	private ImageButton tagImageBtn;
	private TextView tagNameTv;
	private TaskManager taskManager;
	private BaseTask task;
	private String tid;
	private static final int TAG_REQUEST_CODE = 1;
	private Logger log;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		log = LoggerFactory.getLogger(CommentActivity.class);
		try {
			tid = getIntent().getStringExtra(MyConstants.TASK_ID);
			taskManager = TaskManager.getInstance(getBaseContext());
			task = taskManager.getTaskById(tid);
			initView();
			getActionBar().hide();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	private void initView() throws Exception {
		setContentView(R.layout.activity_edit_task);
		finishBtn = (Button) findViewById(R.id.finish_btn);
		titleET = (EditText) findViewById(R.id.title_et);
		titleET.setText(task.getTitle());
		tagArray = task.getTags();
		tagImageBtn = (ImageButton) findViewById(R.id.tag_btn);
		tagImageBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EditTaskActivity.this,
						EditTagActivity.class);
				if (tagArray != null && tagArray.length > 0) {
					intent.putExtra(MyConstants.TAG_EDIT, tagArray);
				}
				startActivityForResult(intent, TAG_REQUEST_CODE);
			}
		});
		tagNameTv = (TextView) findViewById(R.id.add_task_tag_tv);
		descET = (EditText) findViewById(R.id.desc_et);
		descET.setText(task.getDesc());
		finishBtn.setOnClickListener(new FinishBtnOnClickListen());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if (requestCode == TAG_REQUEST_CODE) {
				if (resultCode == RESULT_OK) {
					tagArray = data.getStringArrayExtra(MyConstants.TAG_EDIT);
					if (tagArray != null && tagArray.length > 0) {
						tagNameTv.setText(String.valueOf(tagArray.length));
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	private class FinishBtnOnClickListen implements View.OnClickListener {
		@Override
		public void onClick(View arg0) {
			modifyTaskDesc(task);
		}
	}

	private boolean modifyTaskDesc(BaseTask task) {
		try {
			title = titleET.getText().toString();
			description = descET.getText().toString();
			task.setTitle(title);
			task.setDesc(description);
			task.setTag(tagArray);
			TaskInputCheck.CheckResult checkResult = TaskInputCheck
					.isInputAvailable(getBaseContext(), task);
			switch (checkResult.getResultCode()) {
			case 0:
				if (!taskManager.modifyTaskDesc(task)) {
					MyToast.makeText(getApplicationContext(), getResources()
							.getString(R.string.modify_task_failed));
				} else {
					finish();
				}
				break;
			case -1:
				MyToast.makeText(getBaseContext(),
						checkResult.getResultDetail());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return true;
	}
}
