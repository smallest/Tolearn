package com.smallest.tolearn.activity;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.smallest.tolearn.R;
import com.smallest.tolearn.utils.MyConstants;
import com.smallest.tolearn.utils.TaskManager;

public class EditTagActivity extends Activity {
	private ListView tagLv;
	private EditTagAdapter<String> mAdapter;
	private List<String> tagList;
	private TaskManager taskManager;
	private EditText tagEt;
	private Button cancelBtn;
	private Button okBtn;
	private Logger log;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		log = LoggerFactory.getLogger(CommentActivity.class);
		try {
			setContentView(R.layout.activity_edittag);

			Log.d("smallest", "EdittagActivity setContentView finished");
			Intent intent = getIntent();
			String tag = intent.getStringExtra(MyConstants.TAG_EDIT);
			tagEt = (EditText) findViewById(R.id.tag_et);
			taskManager = TaskManager.getInstance(getBaseContext());
			tagList = taskManager.getTagList();
			Log.d("smallest", "tagList size 47:" + tagList.size());
			tagLv = (ListView) findViewById(R.id.edit_tag_lv);
			mAdapter = new EditTagAdapter<String>(this,
					R.layout.tag_adapter_item, tagList);
			tagLv.setAdapter(mAdapter);
			cancelBtn = (Button) findViewById(R.id.edit_tag_cancel);
			okBtn = (Button) findViewById(R.id.edit_tag_ok);
			cancelBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
			okBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					Set<String> tagsSet = mAdapter.getCheckTags();
					String tag = tagEt.getText().toString();
					Log.d("smallest", "tag:" + tag);
					tagsSet.add(tag);
					Log.d("smallest", "tag size:" + tagsSet.size());
					intent.putExtra(MyConstants.TAG_EDIT,
							tagsSet.toArray(new String[] {}));
					setResult(RESULT_OK, intent);
					finish();
				}
			});
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	private class EditTagAdapter<T> extends ArrayAdapter<T> {
		private int resource;
		private List<String> tagList;
		private Set<String> checkedTagSet = new TreeSet<String>();

		public EditTagAdapter(Context context, int resource, List<T> objects) {
			super(context, resource, objects);
			this.resource = resource;
			tagList = (List<String>) objects;
			Log.d("smallest", "tagList size:" + tagList.size());
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(this.getContext()).inflate(
						resource, null);
			}
			TextView tagNameTv = (TextView) convertView
					.findViewById(R.id.tag_name);
			tagNameTv.setText(tagList.get(position));
			CheckBox checkBox = (CheckBox) convertView
					.findViewById(R.id.tag_check);
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (isChecked) {
						checkedTagSet.add(tagList.get(position));
					} else {
						if (checkedTagSet.contains(tagList.get(position))) {
							checkedTagSet.remove(tagList.get(position));
						}
					}
				}
			});
			return convertView;
		}

		public Set<String> getCheckTags() {
			return checkedTagSet;
		}
	}
}
