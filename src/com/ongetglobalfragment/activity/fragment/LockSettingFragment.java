package com.ongetglobalfragment.activity.fragment;

import com.ongetglobalfragment.R;
import com.ongetglobalfragment.activity.LockManagementActivity;
import com.ongetglobalfragment.common.ActionConst;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class LockSettingFragment extends Fragment {


	public LockSettingFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_locksetting, container, false);
		Button btnNfc = (Button)v.findViewById(R.id.bthNfcLock);
		btnNfc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(),LockManagementActivity.class);
				i.setAction(ActionConst.ACTION_LOCKSETTING);
				startActivity(i);
				
			}
		});
		return v;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	



}
