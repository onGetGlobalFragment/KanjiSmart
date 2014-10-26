package com.ongetglobalfragment.activity.dialogfragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;

import com.ongetglobalfragment.R;
import com.ongetglobalfragment.activity.MainActivity;
import com.ongetglobalfragment.common.ViewConst;
import com.ongetglobalfragment.database.model.EventListItem;

public class DialogTorokuFragment extends DialogFragment {

	private String title;
	private int position;
	
	private EditText txtSeiKana;
	private EditText txtMeiKana;
	private EditText txtSei;
	private EditText txtMei;
	private EditText txtAnother;
	private RadioButton radioMale;
	private RadioButton radioFemale;
	private NumberPicker np;
	
	private EventListItem item;
	private MainActivity ma;
	
	
	public DialogTorokuFragment(String title,int position) {
		this.title = title;
		this.position = position;
	}

	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		item = new EventListItem();
		ma = (MainActivity)getActivity();

		item = ma.lf.getAdSimple().al.get(position);
		LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View content = inflater.inflate(R.layout.dialog_toroku, null);
//		txtSeiKana = (EditText)content.findViewById(R.id.txtSeiKana);
//		txtMeiKana = (EditText)content.findViewById(R.id.txtMeiKana);
		txtSei = (EditText)content.findViewById(R.id.txtSei);
		txtMei = (EditText)content.findViewById(R.id.txtMei);
		txtAnother = (EditText)content.findViewById(R.id.txtAnother);
		radioMale = (RadioButton)content.findViewById(R.id.radioMale);
		radioFemale = (RadioButton)content.findViewById(R.id.radioFemale);
		np = (NumberPicker)content.findViewById(R.id.npAge);
		
//		txtSeiKana.setText(item.getKanaLa());
//		txtMeiKana.setText(item.getKanaFi());
		txtSei.setText(item.getNameLa());
		txtMei.setText(item.getNameFi());
		txtAnother.setText(item.getAnother());
		if(item.getSex()==0){
			radioMale.setChecked(true);
		}else{
			radioFemale.setChecked(true);
		}
		np.setMinValue(0);
		np.setMaxValue(85);
		np.setValue(item.getAge());
		if(item.getAge()==0)np.setValue(25);
		
		builder.setView(content)
//		builder.setTitle(title)
		.setPositiveButton(ViewConst.LABEL_TOROKU, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				setItems();
			}
		})
		.setNegativeButton(ViewConst.LABEL_CLOSE, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				
			}
		});

		return builder.create();
	}
	
	private void setItems(){
//		item.setKanaLa(txtSeiKana.getText().toString());
		item.setKanaFi(txtMeiKana.getText().toString());
		item.setNameLa(txtSei.getText().toString());
		item.setNameFi(txtMei.getText().toString());
		if(radioMale.isChecked())item.setSex(0);
		if(radioFemale.isChecked())item.setSex(1);
		item.setAge(np.getValue());
		item.setAnother(txtAnother.getText().toString());
		ma.lf.listPosition=ma.lf.lv.getFirstVisiblePosition();
		ma.lf.resumeListSimple(item, position);
		ma.lf.lv.setSelection(ma.lf.listPosition);
		
	}

}
