package com.ongetglobalfragment.activity.dialogfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.ongetglobalfragment.R;
import com.ongetglobalfragment.activity.MainActivity;
import com.ongetglobalfragment.activity.fragment.EventListFragment;

public class AlertDataFragment extends DialogFragment {
	private String title;
	private NumberPicker np ;
	private ArrayAdapter<String> adapter;
	private Spinner spn;
	public AlertDataFragment(String title) {
		this.title = title;
		
	}

	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


		LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View content = inflater.inflate(R.layout.dialog_addlist, null);
		Button btnMoveToSearch = (Button)content.findViewById(R.id.btnMoveToSearch);
		Button btnGetEasyList = (Button)content.findViewById(R.id.btnGetEasyList);
		np = (NumberPicker)content.findViewById(R.id.numPick);
		spn = (Spinner)content.findViewById(R.id.spnNumber);
		btnMoveToSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//検索タブへ移動
				MainActivity ma = (MainActivity)getTargetFragment().getActivity();

				ma.actionBar.setSelectedNavigationItem(0);
				AlertDataFragment.this.dismiss();
			}
		});
		btnGetEasyList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EventListFragment lf = (EventListFragment)getTargetFragment();
				lf.setAl(lf.getEmptyArray(np.getValue()));
				lf.initListView();
				AlertDataFragment.this.dismiss();

			}
		});
		adapter = new ArrayAdapter<String>(
				getTargetFragment().getActivity(),R.layout.spinneritem);	
		adapter.add("10");
		adapter.add("30");
		adapter.add("50"); 
		adapter.add("100");
		adapter.add("200");
		adapter.add("300");
		adapter.add("500");
		adapter.add("1000");
		spn.setAdapter(adapter);
		spn.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				np.setMaxValue(Integer.parseInt(spn.getSelectedItem().toString()));
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		np.setMaxValue(Integer.parseInt(spn.getSelectedItem().toString()));

		builder.setView(content);
		builder.setTitle(title);
		


		// Create the AlertDialog object and return it
		return builder.create();
	}

}
