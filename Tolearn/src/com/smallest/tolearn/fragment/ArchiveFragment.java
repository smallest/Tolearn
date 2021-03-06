package com.smallest.tolearn.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.app.FragmentTransaction;
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
import com.smallest.tolearn.dao.BaseTask;
import com.smallest.tolearn.utils.MyConstants;
import com.smallest.tolearn.utils.TaskManager;

public class ArchiveFragment extends Fragment {
	private ImageView addTaskImageV;
	private ListView archiveLv;
	private Map<String, String> tagMap;
	private TaskManager taskManager;
	private BaseAdapter mBaseAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		Log.d("tolearn", "archivefragment onCreate");
		View view = inflater.inflate(R.layout.fragment_archive, container,
				false);
		addTaskImageV = (ImageView) view.findViewById(R.id.add_task_btn);
		addTaskImageV.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), AddTaskActivity.class);
				startActivity(intent);
			}
		});
		archiveLv = (ListView) view.findViewById(R.id.tag_lv);
		taskManager = TaskManager.getInstance(getActivity());
		tagMap = taskManager.getArchiveTagsMap();
		mBaseAdapter = new ArchiveAdapter(getActivity(), tagMap);
		archiveLv.setAdapter(mBaseAdapter);
		archiveLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Fragment tagTaskFragment = new TagTaskFragment();
				Bundle bundle = new Bundle();
				bundle.putString(MyConstants.TAG_DISPALY,
						((String) mBaseAdapter.getItem(position)));
				tagTaskFragment.setArguments(bundle);
				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();
				transaction.replace(R.id.content_frame, tagTaskFragment,
						MyConstants.TAG_TASKS_FRAGMENT);
				String archiveTag = getResources().getStringArray(
						R.array.nav_list)[3];
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
		return view;
	}

	public class ArchiveAdapter extends BaseAdapter {
		private Map<String, String> mTagMap;
		private Context mContext;
		private List<String> mTagKeyList;

		public ArchiveAdapter(Context mContext, Map<String, String> tagMap) {
			this.mContext = mContext;
			this.mTagMap = tagMap;
			this.mTagKeyList = new ArrayList<String>(tagMap.keySet());
		}

		@Override
		public int getCount() {
			return mTagMap.size();
		}

		@Override
		public String getItem(int position) {
			return mTagKeyList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.archive_adapter_item, null);
			} else {
			}
			String tagName = (String) getItem(position);
			((TextView) convertView.findViewById(R.id.text1)).setText(tagName);
			((TextView) convertView.findViewById(R.id.text2)).setText("("
					+ mTagMap.get(tagName) + ")");
			return convertView;
		}
	}

}
