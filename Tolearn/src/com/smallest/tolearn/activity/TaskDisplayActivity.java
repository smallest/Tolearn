package com.smallest.tolearn.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smallest.tolearn.R;
import com.smallest.tolearn.dao.BaseTask;
import com.smallest.tolearn.utils.MyConstants;
import com.smallest.tolearn.utils.MyToast;
import com.smallest.tolearn.utils.TaskManager;

public class TaskDisplayActivity extends Activity {
	private TextView tagTv;
	private TextView finishTv;
	private TextView commentTv;
	private TaskManager taskManager;
	private BaseTask task;
	private RelativeLayout bottomBar;
	private String tid;
	private ImageView editBtn;
	private TextView titleTv;
	private TextView descTv;
	private Logger log;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		log = LoggerFactory.getLogger(TaskDisplayActivity.class);
		try {
			setContentView(R.layout.activity_taskdisplay);
			getActionBar().setDisplayHomeAsUpEnabled(true);
			Intent intent = getIntent();
			tid = intent.getStringExtra(MyConstants.TASK_ID);
			taskManager = TaskManager.getInstance(getBaseContext());
			task = taskManager.getTaskById(tid);
			if (task == null) {
				Log.d(MyConstants.UNEXPECTED_ERROR, "db invalid id");
				finish();
			}
			taskManager
					.setOnTaskStatusChangedListener(new TaskManager.OnTaskStatusChangedListener() {
						@Override
						public void taskStatusChanged() {
							task = taskManager.getTaskById(tid);
							titleTv.setText(task.getTitle());
							descTv.setText(task.getDesc());
							tagTv.setText(String.valueOf(task.getTags().length));
						}
					});
			titleTv = (TextView) findViewById(R.id.title_tv);
			tagTv = (TextView) findViewById(R.id.task_display_tag_tv);
			tagTv.setText(String.valueOf(task.getTags().length));
			descTv = (TextView) findViewById(R.id.desc_tv);
			titleTv.setText(task.getTitle());
			descTv.setText(task.getDesc());
			bottomBar = (RelativeLayout) findViewById(R.id.bottom_bar);
			finishTv = (TextView) findViewById(R.id.finish_tv);
			commentTv = (TextView) findViewById(R.id.comment_tv);
			if (task.getState() == BaseTask.STATE_TODO) {
				finishTv.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (task.getComment().isEmpty()) {
							MyToast.makeText(getBaseContext(), getResources()
									.getString(R.string.comment_needed));
							return;
						}
						taskManager = TaskManager.getInstance(getBaseContext());
						if (!taskManager.archiveTask(task)) {
							Log.d("tolearn", "task archive failed");
						} else {
							finish();
						}
					}
				});
			} else {
				bottomBar.setVisibility(View.INVISIBLE);
			}
			commentTv.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getBaseContext(),
							CommentActivity.class);
					intent.putExtra(MyConstants.TASK_ID, task.getTid());
					startActivity(intent);
				}
			});
			editBtn = (ImageView) findViewById(R.id.edit_task_btn);
			editBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(TaskDisplayActivity.this,
							EditTaskActivity.class);
					intent.putExtra(MyConstants.TASK_ID, tid);
					startActivity(intent);
				}
			});
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
