package com.ongetglobalfragment.activity.dialogfragment;

import com.ongetglobalfragment.R;
import com.ongetglobalfragment.activity.fragment.EventListFragment;
import com.ongetglobalfragment.common.ViewConst;
import com.ongetglobalfragment.common.util.Utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class DialogSimpleDatePickFragment extends DialogFragment {
	//	private String message;
	private String title;
	private String place;
	private String ymd;
	private EditText txtPlace;
	private EditText txtDefaultPayment;
	private DatePicker dtp;
	private int year;
	private int month;
	private int day;
	private Utils ut;

	public DialogSimpleDatePickFragment(String title,String ymd,String place) {
		this.title=title;
		this.ymd = ymd;
		this.place = place;
		this.ut = new Utils();
	}



	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	
	
		LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View content = inflater.inflate(R.layout.calendar_fragment, null);
		dtp = (DatePicker)content.findViewById(R.id.datePicker1);
		txtPlace = (EditText)content.findViewById(R.id.txtDatePlace);
		txtDefaultPayment = (EditText)content.findViewById(R.id.txtDefaultPayment);
		txtPlace.setHeight(0);
		txtPlace.setVisibility(View.INVISIBLE);
		txtDefaultPayment.setHeight(0);
		txtDefaultPayment.setVisibility(View.INVISIBLE);
	
	
		builder.setView(content);
		builder.setTitle(title)
		//			builder.setMessage(message)
		.setPositiveButton("決定", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id) {
	
				int mon = dtp.getMonth()+1;
				String m = mon+"";
				if(m.length()<2)m = "0"+m;
				String d = dtp.getDayOfMonth()+"";
				if(d.length()<2)d = "0"+d;
				EventListFragment lf = (EventListFragment)getTargetFragment();
				ymd  = dtp.getYear()+"/"+m+"/"+d;
				lf.setYmd(ymd);
				if(!txtPlace.getText().toString().isEmpty()){
					lf.setTextPlace(txtPlace.getText().toString());	
					if(!txtDefaultPayment.getText().toString().isEmpty())lf.setDefaultPrice(Integer.parseInt(txtDefaultPayment.getText().toString()));
//					lf.addEasiItemSimple();
	
				}else{
					Toast.makeText(getActivity(), ViewConst.MSG_NOPLACE, Toast.LENGTH_SHORT).show();
				}
				if(lf.getAl().isEmpty()){
					lf.btnAdd.setText("NEW");		
				}else{
	
	
	
					lf.btnAdd.setText("+");
				}
				lf.labelReset();
			}
		})
		.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
	
	
			}
		});
	
	
		// Create the AlertDialog object and return it
		return builder.create();
	}
	
	}
