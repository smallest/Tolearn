
package com.smallest.tolearn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;

import com.smallest.tolearn.R;
import com.smallest.tolearn.db.TaskDBHelper;
import com.smallest.tolearn.db.TaskDBHelper.TaskInfo;

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
            title = titleET.getText().toString();
            description = descET.getText().toString();
            if (createNewTask()) {
                Intent intent = new Intent(getBaseContext(), TaskDisplayActivity.class);
                startActivity(intent);
            }
        }
    }

    private boolean createNewTask() {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.tid = String.valueOf(System.currentTimeMillis());
        taskInfo.start_time = String.valueOf(System.currentTimeMillis());
        taskInfo.title = title;
        taskInfo.desc = description;
        TaskDBHelper.insertInfo(getBaseContext(), taskInfo);
        return true;
    }
}
