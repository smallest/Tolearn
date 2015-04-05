package com.smallest.tolearn.fragment;

import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.smallest.tolearn.R;
import com.smallest.tolearn.activity.AddTaskActivity;
import com.smallest.tolearn.activity.TaskDisplayActivity;
import com.smallest.tolearn.dao.BaseTask;
import com.smallest.tolearn.utils.MyConstants;
import com.smallest.tolearn.utils.TaskManager;
import com.smallest.tolearn.utils.TimeUtils;

public class TagTaskFragment extends Fragment {
	private ImageView addTaskImageV;
	private ListView taskLv;
	private List<BaseTask> taskList;
	private TaskManager taskManager;
	private BaseAdapter mBaseAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		Log.d("tolearn", "archivefragment onCreate");
		View view = inflater.inflate(R.layout.fragment_tag_tasks, container,
				false);
		addTaskImageV = (ImageView) view.findViewById(R.id.add_task_btn);
		addTaskImageV.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), AddTaskActivity.class);
				startActivity(intent);
			}
		});
		taskLv = (ListView) view.findViewById(R.id.tag_lv);
		taskManager = TaskManager.getInstance(getActivity());
		String tagName = getArguments().getString(MyConstants.TAG_DISPALY);
		if (tagName != null && !tagName.isEmpty()) {
			taskList = taskManager.getArchiveTasksByTag(tagName);
		}
		mBaseAdapter = new TagTaskAdapter(getActivity(), taskList);
		taskLv.setAdapter(mBaseAdapter);
		taskLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),
						TaskDisplayActivity.class);
				String tid = taskList.get(position).getTid();
				intent.putExtra(MyConstants.TASK_ID, tid);
				startActivity(intent);
			}
		});
		return view;
	}

	public class TagTaskAdapter extends BaseAdapter {
		private Context mContext;
		private List<BaseTask> taskList;

		public TagTaskAdapter(Context mContext, List<BaseTask> taskList) {
			this.mContext = mContext;
			this.taskList = taskList;
		}

		@Override
		public int getCount() {
			if (taskList == null) {
				return 0;
			}
			return taskList.size();
		}

		@Override
		public BaseTask getItem(int position) {
			return taskList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.tolearn_adapter_item, null);
			} else {
			}
			BaseTask task = getItem(position);
			long mimsecond = Long.parseLong(taskList.get(position).getTid());
			((TextView) convertView.findViewById(R.id.text1)).setText(task
					.getTitle());
			((TextView) convertView.findViewById(R.id.text2)).setText(TimeUtils
					.secondToTime(String.valueOf(mimsecond / 1000)));
			return convertView;
		}
	}

}
