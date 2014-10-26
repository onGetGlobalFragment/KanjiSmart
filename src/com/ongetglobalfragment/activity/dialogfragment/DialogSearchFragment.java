package com.ongetglobalfragment.activity.dialogfragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.ongetglobalfragment.R;
import com.ongetglobalfragment.activity.MainActivity;
import com.ongetglobalfragment.activity.adapter.SearchResultDialogAdapter;
import com.ongetglobalfragment.common.ViewConst;
import com.ongetglobalfragment.database.model.EventListSumItem;

public class DialogSearchFragment extends DialogFragment {

	private String title;
	private ArrayList<EventListSumItem> array;

	private ListView lv;
	private SearchResultDialogAdapter ad;
	private MainActivity ma;

	public static final int SHOW_BEFORE = 0;
	public static final int SHOW_AFTER = 1;
	public static final int SHOW_BEFOREAFTER = 2;

	public DialogSearchFragment(String title,ArrayList<EventListSumItem> array,int handle) {
		this.title  =title;
		this.array = new ArrayList<EventListSumItem>();
		this.array = array;
	}

	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		ma = (MainActivity)getActivity();
		LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View content = inflater.inflate(R.layout.dialog_search_result, null);

		lv = (ListView)content.findViewById(R.id.listSearchResult);
		ad = new SearchResultDialogAdapter(getActivity(), array);
		lv.setAdapter(ad);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(array.get(position).getListSum()!=0){

					ma.lf.resumeFromSearch(array.get(position).getYmd()+"",array.get(position).getPlace());

					ma.actionBar.setSelectedNavigationItem(1);
					DialogSearchFragment.this.dismiss();
				}else{
					Toast.makeText(getActivity(), ViewConst.MSG_NO_DATA, Toast.LENGTH_SHORT).show();
				}
			}
		});

		builder.setView(content);
		builder.setTitle(title)
		.setNegativeButton(ViewConst.LABEL_CLOSE, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

			}
		});

		return builder.create();
	}

}
