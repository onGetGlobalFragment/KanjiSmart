package com.ongetglobalfragment.activity.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.ToggleButton;

import com.ongetglobalfragment.R;
import com.ongetglobalfragment.activity.dialogfragment.DialogTorokuFragment;
import com.ongetglobalfragment.common.ViewConst;
import com.ongetglobalfragment.database.model.EventListItem;

public class EventListItemSimpleAdapter extends ArrayAdapter<EventListItem> {

	private LayoutInflater inflater;
	public ArrayList<EventListItem> al;
	private EventListItem item;
//	private Context con;

	public boolean editable;
	private FragmentManager ma;

	public static final String ATTEND = "出";
	public static final String ABSENCE = "欠";
	public static final String PAYED = "済";
	public static final String YET_PAY = "未";
	public static final int DELETE = 1;
	public static final int REMAIN = 0;
	public static final int PIXEL_DEL = 80;

	public class ViewHolder {
		//items of a row of list
//		private TextView lblNo;
		private Button btnName;
		private EditText txtPay;
		private ToggleButton btnPayflg;
		private CheckBox cbDelete;

		public ViewHolder(View base) {	
			
//			this.lblNo = (TextView)base.findViewById(R.id.txtNo);
			this.btnName = (Button)base.findViewById(R.id.txtName);
			this.txtPay = (EditText)base.findViewById(R.id.txtPay);
			this.btnPayflg = (ToggleButton)base.findViewById(R.id.btnPay);
			this.cbDelete = (CheckBox)base.findViewById(R.id.cbDelete);
		}
	}

	public EventListItemSimpleAdapter(Context context,ArrayList<EventListItem> list, boolean editable,FragmentManager ma){
		super(context,R.layout.list_item_simple,list);
		
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.item = new EventListItem();
		al = new ArrayList<EventListItem>();
		this.al = list;
		this.editable = editable;
//		this.con = context;
		this.ma = ma;
	}



	@SuppressLint("ViewHolder")
	public View getView(final int position,  View convertView, ViewGroup parent) {

		item = al.get(position);

		// 行viewの取得		
		View view = convertView;
		view = inflater.inflate(R.layout.list_item_simple, parent, false);
		// ホルダの取得
		
		ViewHolder vh = (ViewHolder) view.getTag();
		if(vh==null){
		vh = new ViewHolder(view);
		view.setTag(vh);
		}
		//描画
//		vh.lblNo.setText(position+1+"");
		if(this.editable){
			vh.cbDelete.setVisibility(View.VISIBLE);
			vh.cbDelete.setWidth(PIXEL_DEL);
		}else{
			vh.cbDelete.setVisibility(View.INVISIBLE);
			vh.cbDelete.setWidth(0);
		}
		if(item.getDeleteFlg()==DELETE){
			vh.cbDelete.setChecked(true);
		}else{
			vh.cbDelete.setChecked(false);
		}

		//チェックボックスリスナ
		vh.cbDelete.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				item = al.get(position);
				if(isChecked){
					item.setDeleteFlg(DELETE);
				}else{
					item.setDeleteFlg(REMAIN);
				}
				al.set(position, item);

			}
		});

		vh.btnName.setText(item.getNameLa()+" "+item.getNameFi());

		vh.txtPay.setText(item.getPay()+"");
		vh.txtPay.setGravity(Gravity.RIGHT);
		vh.txtPay.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				View parent = (View)v.getParent().getParent();
				EditText e = (EditText)parent.findViewById(R.id.txtPay);
				if(hasFocus){
					e.setGravity(Gravity.LEFT);
				}else{
					e.setGravity(Gravity.RIGHT);
				}
			}
		});
		if(item.getPayFlg()==0){
			vh.btnPayflg.setChecked(false);
			vh.btnPayflg.setTextColor(Color.RED);
		}else{
			vh.btnPayflg.setChecked(true);
			vh.btnPayflg.setTextColor(Color.BLACK);
		}
		//名前を押したとき
		vh.btnName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DialogTorokuFragment dtf = new DialogTorokuFragment(ViewConst.TITLE_INFO_UPDATE, position);
				dtf.show(ma, ViewConst.TITLE_INFO_UPDATE);

			}
		});
		vh.txtPay.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

				ListView view = (ListView)v.getParent().getParent().getParent();
				view.performItemClick((View)v.getParent().getParent(), position, R.id.txtPay);
				return false;
			}
		});
		//金額入力時
		vh.txtPay.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				item = al.get(position);
				String strPay = "";
				strPay = s.toString();
				if(strPay.toString().isEmpty())strPay = "0";
				item.setPay(Integer.parseInt(strPay.toString()));
				al.set(position, item);				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		//支払チェックボタン押下

		vh.btnPayflg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				item = al.get(position);
				if(isChecked){
					item.setPayFlg(1);
					buttonView.setTextColor(Color.BLACK);
				}else{
					item.setPayFlg(0);
					buttonView.setTextColor(Color.RED);
				}
				
				al.set(position, item);
				ListView v = (ListView)buttonView.getParent().getParent().getParent();
				v.performItemClick((View)buttonView.getParent().getParent(), position, R.id.txtPay);
			}
		});

		return(view);	
	}	

}
