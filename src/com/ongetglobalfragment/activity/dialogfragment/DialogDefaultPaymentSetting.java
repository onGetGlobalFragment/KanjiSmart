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

import com.ongetglobalfragment.R;
import com.ongetglobalfragment.activity.MainActivity;
import com.ongetglobalfragment.common.ViewConst;

public class DialogDefaultPaymentSetting extends DialogFragment {
	private String title;
	private EditText txtIndividual;
	private EditText txtTotal;
	private MainActivity ma;
	private int price;
	private int total;
	public DialogDefaultPaymentSetting(String title,int price,int total) {
		this.title = title;
		this.price = price;
		this.total = total;
		
	}

	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		ma = (MainActivity)getActivity();
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	
		LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View content = inflater.inflate(R.layout.dialog_defaultvalue_oflist, null);
		txtIndividual = (EditText)content.findViewById(R.id.txtIndividualPayment);
		txtTotal = (EditText)content.findViewById(R.id.txtTotalPayment); 
		if(price!=0){
			txtIndividual.setText(price+"");
		}
		if(total!=0){
			txtTotal.setText(total+"");
		}else{
			if(price!=0)txtTotal.setText((price*ma.lf.getAl().size())+"");
		}
		
		builder.setView(content);
		builder.setTitle(title)
		.setPositiveButton(ViewConst.LABEL_TOROKU, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				//デフォルト値を更新
				if(!txtIndividual.getText().toString().isEmpty()){
					ma.lf.setDefaultPrice(Integer.parseInt(txtIndividual.getText().toString()));
				}
				if(!txtTotal.getText().toString().isEmpty()){
					ma.lf.setTotalYosan(Integer.parseInt(txtTotal.getText().toString()));
				}
			}
		})
		.setNegativeButton(ViewConst.LABEL_CLOSE, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				
			}
		});
		return builder.create();
		}
	
	public void setDefaultValues(){
		
	}
}
