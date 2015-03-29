package com.smallest.tolearn.fragment;

import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class RepoFragment extends Fragment {
	private ImageView addTaskImageV;
	private ListView repoLv;
	private List<BaseTask> taskList;
	private ArrayAdapter<BaseTask> repoAdapter;
	private TaskManager taskManager = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_repo, container, false);
		repoLv = (ListView) view.findViewById(R.id.repo_lv);
		addTaskImageV = (ImageView) view.findViewById(R.id.add_task_btn);
		addTaskImageV.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), AddTaskActivity.class);
				startActivity(intent);
			}
		});
		taskManager = TaskManager.getInstance(getActivity());
		getDataFromRepo();
		return view;
	}

	public boolean getDataFromRepo() {
		taskList = taskManager.getRepoList();
		repoAdapter = new RepoAdapter<BaseTask>(getActivity(),
				R.layout.repo_adapter_item, taskList);
		repoLv.setAdapter(repoAdapter);
		repoLv.setOnItemClickListener(new RepoListListener());
		taskManager
				.setOnRepoSetChangedListener(new TaskManager.OnRepoSetChangedListener() {

					@Override
					public void dataSetChanged() {
						repoAdapter.notifyDataSetChanged();
					}
				});
		return true;
	}

	private class RepoAdapter<T> extends ArrayAdapter<T> {
		private int resource;
		private List<BaseTask> taskList;

		public RepoAdapter(Context context, int resource, List<T> objects) {
			super(context, resource, objects);
			this.resource = resource;
			taskList = (List<BaseTask>) objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(this.getContext()).inflate(
						resource, null);
			} else {
				// TODO
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

	private class RepoListListener implements ListView.OnItemClickListener {
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
