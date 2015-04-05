package com.smallest.tolearn.fragment;

import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.smallest.tolearn.R;
import com.smallest.tolearn.activity.AddTaskActivity;
import com.smallest.tolearn.activity.TaskDisplayActivity;
import com.smallest.tolearn.dao.BaseTask;
import com.smallest.tolearn.utils.DimenUtils;
import com.smallest.tolearn.utils.MyConstants;
import com.smallest.tolearn.utils.TaskManager;
import com.smallest.tolearn.utils.TimeUtils;

public class TolearnFragment extends Fragment {
	private ImageView addTaskImageV;
	private SwipeMenuListView mListView;
	private ArrayAdapter<BaseTask> mAdapter;
	private List<BaseTask> taskList;
	private TaskManager taskManager;
	private SwipeMenuCreator creator;
//	private int dispalyTaskPos = -1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_tolearn, container,
				false);
		addTaskImageV = (ImageView) view.findViewById(R.id.add_task_btn);
		addTaskImageV.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), AddTaskActivity.class);
				startActivity(intent);
			}
		});
		mListView = (SwipeMenuListView) view.findViewById(R.id.tolearn_lv);
		creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem unloadItem = new SwipeMenuItem(getActivity());
				unloadItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
				unloadItem.setWidth(DimenUtils.dp2px(getActivity(), 90));
				unloadItem.setTitle("unload");
				unloadItem.setTitleSize(18);
				unloadItem.setTitleColor(Color.WHITE);
				menu.addMenuItem(unloadItem);
				SwipeMenuItem lockItem = new SwipeMenuItem(getActivity());
				lockItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
						0xCE)));
				lockItem.setWidth(DimenUtils.dp2px(getActivity(), 90));
				lockItem.setTitle("lock");
				lockItem.setTitleSize(18);
				lockItem.setTitleColor(Color.WHITE);
				menu.addMenuItem(lockItem);
			}
		};

		taskManager = TaskManager.getInstance(getActivity());
		taskManager
				.setOnTodoSetChangedListener(new TaskManager.OnTodoSetChangedListener() {
					@Override
					public void dataSetChanged() {
						mAdapter.notifyDataSetChanged();
					}
				});

		taskList = taskManager.getTodoList();
		mAdapter = new MyAdapter<BaseTask>(getActivity(),
				R.layout.tolearn_adapter_item, taskList);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new TolearnListListener());
		mListView.setMenuCreator(creator);
		mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu,
					int index) {
				switch (index) {
				case 0:
					taskManager.unloadTask(taskList.get(position));
					// taskList.remove(position);
					mAdapter.notifyDataSetChanged();
					break;
				case 1:
					// lock
					break;
				}
				return false;
			}
		});
		return view;
	}

	private class MyAdapter<T> extends ArrayAdapter<T> {
		private int resource;
		private List<BaseTask> taskList;

		public MyAdapter(Context context, int resource, List<T> objects) {
			super(context, resource, objects);
			this.resource = resource;
			this.taskList = (List<BaseTask>) objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(this.getContext()).inflate(
						resource, null);
			} else {
			}
			TextView titleTv = (TextView) convertView.findViewById(R.id.text1);
			TextView idTv = (TextView) convertView.findViewById(R.id.text2);
			titleTv.setText(taskList.get(position).getTitle());
			long mimsecond = Long.parseLong(taskList.get(position).getTid());
			idTv.setText(TimeUtils.secondToTime(String
					.valueOf(mimsecond / 1000)));
			return convertView;
		}
	}

	private class TolearnListListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(getActivity(), TaskDisplayActivity.class);
			String tid = taskList.get(position).getTid();
			intent.putExtra(MyConstants.TASK_ID, tid);
			startActivity(intent);
		}
	}
}
