package com.smallest.tolearn.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smallest.tolearn.R;
import com.smallest.tolearn.activity.AddTaskActivity;

public class RepoFragment extends Fragment {
	private ImageView addTaskImageV;
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 super.onCreateView(inflater, container, savedInstanceState);
	        View view = inflater.inflate(R.layout.fragment_repo, container);
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
