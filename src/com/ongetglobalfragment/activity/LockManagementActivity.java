package com.ongetglobalfragment.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.ongetglobalfragment.R;
import com.ongetglobalfragment.activity.dialogfragment.DialogPasswordRegist;
import com.ongetglobalfragment.common.ActionConst;
import com.ongetglobalfragment.database.dao.PasswordDAO;
import com.ongetglobalfragment.database.model.PasswordItem;

public class LockManagementActivity extends Activity {
	
	private LinearLayout layEmail;
	private EditText txtEmail;
	private LinearLayout layStatus;
	private Switch btnLockStatus;
	private Button btnForgot;

	private NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;
	private IntentFilter[] mIntentFilters;
	private String[][] techList;
	private PasswordDAO dao;
	private boolean lockManageFlag;
	private PasswordItem  mPasswordItem;

	private String TAG = "LockManagementActivity";

	public LockManagementActivity() {
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lock);
		layEmail = (LinearLayout)findViewById(R.id.layEmail);
		layStatus = (LinearLayout)findViewById(R.id.layLockStatus);
		txtEmail =(EditText)findViewById(R.id.txtUserEmail);
		btnLockStatus = (Switch)findViewById(R.id.switchLock);
		btnForgot = (Button)findViewById(R.id.btnPassForgot);
		btnForgot.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			//TODO:小画面表示後、パスワード入力してロック解除する
				Toast.makeText(LockManagementActivity.this, "未実装です、申し訳ありません。",Toast.LENGTH_SHORT).show();
				
			}
		});

		dao = new PasswordDAO(this);
		lockManageFlag=false;
		mPasswordItem = new PasswordItem();
		mNfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());

		Intent intent = new Intent(getApplicationContext(), getClass());
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		mPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

		IntentFilter intentFilter = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		mIntentFilters = new IntentFilter[]{intentFilter};
		techList = new String[][] { 
				{
					android.nfc.tech.NfcA.class.getName(),
					android.nfc.tech.NfcB.class.getName(),
					android.nfc.tech.NfcF.class.getName(),
					android.nfc.tech.NfcV.class.getName(), 
					android.nfc.tech.IsoDep.class.getName(),
					android.nfc.tech.MifareClassic.class.getName(),
					android.nfc.tech.MifareUltralight.class.getName(),
					android.nfc.tech.NdefFormatable.class.getName(),
					
				}
		};

		Intent activityIntent = getIntent();
		if(activityIntent.getAction().equals(ActionConst.ACTION_LOGIN)){
			lockManageFlag=true;
			layEmail.setVisibility(View.INVISIBLE);
			layStatus.setVisibility(View.INVISIBLE);
			
		}else if(activityIntent.getAction().equals(ActionConst.ACTION_LOCKSETTING)){
			lockManageFlag=false;
		}
	}
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
	    if (event.getAction()==KeyEvent.ACTION_DOWN) {
	        switch (event.getKeyCode()) {
	        case KeyEvent.KEYCODE_BACK:
	        	
	            if(lockManageFlag)return true;
	        }
	    }
	    return super.dispatchKeyEvent(event);
	}
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		String action = intent.getAction();
		if (TextUtils.isEmpty(action))
			return;

		if (!action.equals(NfcAdapter.ACTION_TAG_DISCOVERED))
			return;
		
		String idm = "";
		if (action.equals(NfcAdapter.ACTION_TAG_DISCOVERED)){
			//カードのタイプ判別（特に今のところ不要なのでコメントアウト
			//		Tag tag = (Tag)intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			//		String techStr = "";
			//        for (String tech : tag.getTechList()) {
			//            techStr = techStr + tech + "\n";
			//        }
			//        if (techStr.equals("")) {
			//            techStr = "no techList.";
			//        }
			byte[] ids = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
			StringBuilder tagId = new StringBuilder();
			for (int i=0; i<ids.length; i++) {
				tagId.append(String.format("%02x", ids[i] & 0xff));
			}
			idm = tagId.toString();
//			Toast.makeText(getApplicationContext(), tagId.toString(), Toast.LENGTH_SHORT).show();
		}

		if(lockManageFlag){
			if(dao.isData(idm)){
				setResult(ActionConst.RESULT_CODE_OK);
				finish();
			}else{
				Toast.makeText(this, getString(R.string.msgWrongIDM),Toast.LENGTH_LONG).show();
			}
		}else{
			//パスワードを登録or更新
			DialogPasswordRegist dpr=null;
			PasswordItem item = new PasswordItem();
			item.setPassWord(idm);
			item.setMailAdress(txtEmail.getText().toString());

			if(btnLockStatus.isChecked()){
				item.setLockFlag(1);
			}else{
				item.setLockFlag(0);
			}
			
			if(dao.isData()){
				//小画面表示して更新確認を促す
				dpr = new DialogPasswordRegist(item, false);
				dpr.show(getFragmentManager(), TAG);
			}else{
				
				dpr = new DialogPasswordRegist(item, true);
				dpr.show(getFragmentManager(), TAG);
			}
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mIntentFilters, techList);
	}
	@Override
	protected void onPause() {
		super.onPause();
		mNfcAdapter.disableForegroundDispatch(this);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	public PasswordItem getmPasswordItem() {
		return mPasswordItem;
	}
	public void setmPasswordItem(PasswordItem mPasswordItem) {
		this.mPasswordItem = mPasswordItem;
	}



}
