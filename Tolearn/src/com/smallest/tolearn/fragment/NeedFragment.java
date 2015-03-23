package com.smallest.tolearn.fragment;

import com.smallest.tolearn.R;
import com.smallest.tolearn.activity.AddTaskActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class NeedFragment extends Fragment {
	private ImageView addTaskImageV;

//	public NeedFragment() {
//		// TODO Auto-generated constructor stub
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 super.onCreateView(inflater, container, savedInstanceState);
		Log.d("tolearn", "needfragment onCreate");
		View view = inflater.inflate(R.layout.fragment_need, container,false);
		addTaskImageV = (ImageView) view.findViewById(R.id.add_task_btn);
		addTaskImageV.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), AddTaskActivity.class);
				startActivity(intent);
			}
		});
		return view;
	}
}
