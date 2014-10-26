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

import com.ongetglobalfragment.R;
import com.ongetglobalfragment.activity.LockManagementActivity;
import com.ongetglobalfragment.database.dao.PasswordDAO;
import com.ongetglobalfragment.database.model.PasswordItem;

public class DialogPasswordRegist extends DialogFragment {

	private PasswordItem item;
	private boolean isNew;
	public DialogPasswordRegist(PasswordItem item, boolean isNew) {
		this.item=item;
		this.isNew = isNew;
	}
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		String text = "";
		if(isNew){
			text = getActivity().getString(R.string.msgPasswordNew);			
		}else{
			text =  getActivity().getString(R.string.msgPasswordUpdate);
		}
		builder.setMessage(text)
		.setPositiveButton("はい", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id) {
				PasswordDAO dao = new PasswordDAO(getActivity());
				if(isNew){
					dao.isInsert(item);
				}else{
					String oldIdm = dao.getPasswordItem().getPassWord();
					dao.isUpdate(item,oldIdm);
				}
			}
		})
		.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			

			}
		});
	

		// Create the AlertDialog object and return it
		return builder.create();
	}
	

}
