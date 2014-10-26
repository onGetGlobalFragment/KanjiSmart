package com.ongetglobalfragment.activity.fragment;


import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ongetglobalfragment.R;
import com.ongetglobalfragment.activity.MainActivity;
import com.ongetglobalfragment.activity.dialogfragment.DialogSearchFragment;
import com.ongetglobalfragment.common.ViewConst;
import com.ongetglobalfragment.common.util.Utils;
import com.ongetglobalfragment.database.model.EventListSumItem;


public class SearchFragment extends Fragment {

	private View v;

	private RadioButton radioBefore;
	private RadioButton radioAfter;
	private RadioButton radioRange;
	private DatePicker dtpBefore;
	private DatePicker dtpAfter;
	private LinearLayout layBefore;
	private LinearLayout layAfter;
	private Button btnSearch;
	private int year;
	private int month;
	private int day;
	private String ymdBefore;
	private String ymdAfter;
	private int handle;
	private Utils ut;

	private ArrayList<EventListSumItem> array;
	private FragmentManager manager;
	public SearchFragment() {
		array = new ArrayList<EventListSumItem>();
		ut = new Utils();
	}
	//getter & setter
	public String getYmdBefore() {
		return ymdBefore;
	}

	public void setYmdBefore(String ymdBefore) {
		this.ymdBefore = ymdBefore;
	}

	public String getYmdAfter() {
		return ymdAfter;
	}

	public void setYmdAfter(String ymdAfter) {
		this.ymdAfter = ymdAfter;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		manager = getActivity().getFragmentManager();
		v = inflater.inflate(R.layout.fragment_search,container, false);
		radioBefore = (RadioButton)v.findViewById(R.id.radioBefore);
		radioAfter  = (RadioButton)v.findViewById(R.id.radioAfter);
		radioRange = (RadioButton)v.findViewById(R.id.radioRange);
		btnSearch = (Button)v.findViewById(R.id.btnEventSearch);
		dtpBefore = (DatePicker)v.findViewById(R.id.dtpBefore);
		dtpAfter = (DatePicker)v.findViewById(R.id.dtpAfter);
		layBefore =(LinearLayout)v.findViewById(R.id.layBefore);
		layAfter =(LinearLayout)v.findViewById(R.id.layAfter);

		radioBefore.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				if(isChecked)layBefore.setVisibility(View.VISIBLE);
				if(isChecked)layAfter.setVisibility(View.INVISIBLE);
			}
		});
		radioAfter.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				if(isChecked)layBefore.setVisibility(View.INVISIBLE);
				if(isChecked)layAfter.setVisibility(View.VISIBLE);
			}
		});

		radioRange.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				if(isChecked)layBefore.setVisibility(View.VISIBLE);
				if(isChecked)layAfter.setVisibility(View.VISIBLE);
			}
		});
		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String title = "";
				try {
					if(radioBefore.isChecked()){	
						setYmdOnlyBefore();
						array = MainActivity.mEventRecordDAO.getEventListBefore(Integer.parseInt(ymdBefore));					
						title = " ~ "+ut.getSlashYmdFromPlaneYmd(ymdBefore);
					}
					if(radioAfter.isChecked()){
						setYmdOnlyAfter();
						array = MainActivity.mEventRecordDAO.getEventListAfter(Integer.parseInt(ymdAfter));
						title = ut.getSlashYmdFromPlaneYmd(ymdAfter)+" ~ ";
					}

					if(radioRange.isChecked()){
						setYmdBeforeAfter();
						array = MainActivity.mEventRecordDAO.getEventListBetween(ymdAfter,ymdBefore);
						title = ut.getSlashYmdFromPlaneYmd(ymdAfter)+" ~ "+ut.getSlashYmdFromPlaneYmd(ymdBefore);
					}
					if(!array.isEmpty()){
						DialogSearchFragment dsf = new DialogSearchFragment(title, array, handle);
						dsf.show(manager,title);
					}else{
						Toast.makeText(getActivity(), ViewConst.MSG_NO_DATA, Toast.LENGTH_SHORT).show();
					}
				}catch(Exception e){
					e.printStackTrace();
					Toast.makeText(getActivity(), ViewConst.MSG_SYS_ERR, Toast.LENGTH_SHORT).show();
				}
			}
		});
		return v;
	}
	public void setYmdBeforeAfter(){
		year = dtpBefore.getYear();
		month = dtpBefore.getMonth()+1;
		day = dtpBefore.getDayOfMonth();
		String m = "";
		String d = "";
		if(month<10){
			m="0"+month;
		}else{
			m=month+"";
		}
		if(day<10){
			d = "0"+day;		
		}else{
			d = day+"";
		}
		ymdBefore = year+m+d;	
		year = dtpAfter.getYear();
		month = dtpAfter.getMonth()+1;
		day = dtpAfter.getDayOfMonth();
		m = "";
		d= "";
		if(month<10){
			m="0"+month;
		}else{
			m=month+"";
		}
		if(day<10){
			d = "0"+day;		
		}else{
			d = day+"";
		}
		ymdAfter = year+m+d;
	}
	public void setYmdOnlyAfter(){
		year = dtpAfter.getYear();
		month = dtpAfter.getMonth()+1;
		day = dtpAfter.getDayOfMonth();
		String m = "";
		String d = "";
		if(month<10){
			m="0"+month;
		}else{
			m=month+"";
		}
		if(day<10){
			d = "0"+day;		
		}else{
			d = day+"";
		}
		ymdAfter = year+m+d;
	}
	public void setYmdOnlyBefore(){
		year = dtpBefore.getYear();
		month = dtpBefore.getMonth()+1;
		day = dtpBefore.getDayOfMonth();
		String m = "";
		String d = "";
		if(month<10){
			m="0"+month;
		}else{
			m=month+"";
		}
		if(day<10){
			d = "0"+day;		
		}else{
			d = day+"";
		}
		ymdBefore = year+m+d;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();	
		//データが存在しない場合、リストタブを表示
		if(!MainActivity.mEventRecordDAO.isData()){
			MainActivity ma = (MainActivity)getActivity();
			ma.actionBar.setSelectedNavigationItem(1);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

}
