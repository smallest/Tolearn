package com.smallest.tolearn.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.smallest.tolearn.R;
import com.smallest.tolearn.utils.MyConstants;
import com.smallest.tolearn.utils.TaskManager;

public class CommentActivity extends Activity {
	Button finishBtn;
	EditText commentTv;
	String taskID;
	Logger log;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		log = LoggerFactory.getLogger(CommentActivity.class);
		getActionBar().hide();
		try {
			commentTv = (EditText) findViewById(R.id.comment_et);
			finishBtn = (Button) findViewById(R.id.finish_btn);
			finishBtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					String comment = commentTv.getText().toString();
					if (!comment.isEmpty()) {
						TaskManager.getInstance(getBaseContext())
								.modifyComment(taskID, comment);
					}
					finish();
				}
			});
			Bundle extra = getIntent().getExtras();
			taskID = extra.getString(MyConstants.TASK_ID);
			String initComment = TaskManager.getInstance(getBaseContext())
					.getTaskById(taskID).getComment();
			commentTv.setText(initComment);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
