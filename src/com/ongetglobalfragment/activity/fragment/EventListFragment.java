package com.ongetglobalfragment.activity.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings.Global;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ongetglobalfragment.R;
import com.ongetglobalfragment.activity.MainActivity;
import com.ongetglobalfragment.activity.adapter.EventListItemSimpleAdapter;
import com.ongetglobalfragment.activity.dialogfragment.AlertDataFragment;
import com.ongetglobalfragment.activity.dialogfragment.DatePickFragment;
import com.ongetglobalfragment.activity.dialogfragment.DialogDefaultPaymentSetting;
import com.ongetglobalfragment.activity.dialogfragment.DialogSimpleDatePickFragment;
import com.ongetglobalfragment.common.ViewConst;
import com.ongetglobalfragment.common.util.Utils;
import com.ongetglobalfragment.database.model.EventListItem;

/** Fragment of  list view
 * @author Y.aratake
 * @since 2014/09/10
 * @version 1.0
 * @update 
 */
public class EventListFragment extends Fragment {

	private String ymd;

	private View v;
	public ListView lv;
	private Button btnInsert;
	private Button btnEdit;
	private Button btnDelete;
	private TextView txtDate;
	public Button btnAdd;
//	private TextView lblDelete;
	private TextView lblListSum;
	private TextView lblListPayed;
	private EditText txtPlace;
	private TextView lblAverage;
	private int defaultPrice;
	private int totalYosan;
	public Button btnRenew;
	private LinearLayout laySum;


	private ArrayList<EventListItem> al;
	private EventListItemSimpleAdapter adSimple;
	private Context con;
	public boolean editable;
	public boolean custom;
	public int listPosition;

	private FragmentManager manager ;
	private Utils ut;
	public EventListFragment() {
		ut = new Utils();
		this.ymd = ut.getTodayPlaneYmd();
		this.al = new ArrayList<EventListItem>();
		this.defaultPrice=0;
		setTotalYosan(0);
		
	}
	
	public EventListItemSimpleAdapter getAdSimple() {
		return adSimple;
	}
	public void setAdSimple(EventListItemSimpleAdapter adSimple) {
		this.adSimple = adSimple;
	}
	
	public int getDefaultPrice() {
		return defaultPrice;
	}


	public void setDefaultPrice(int defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	public int getTotalYosan() {
		return totalYosan;
	}
	public void setTotalYosan(int totalYosan) {
		this.totalYosan = totalYosan;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		manager = getActivity().getFragmentManager();
		v = inflater.inflate(R.layout.fragment_simplelist, container, false);
		lv = (ListView)v.findViewById(R.id.list);
		btnInsert = (Button)v.findViewById(R.id.btnInsert);
		btnEdit = (Button)v.findViewById(R.id.btnEdit);
		btnDelete = (Button)v.findViewById(R.id.btnDlete);
		txtDate = (TextView)v.findViewById(R.id.txtDate);
//		lblDelete = (TextView)v.findViewById(R.id.lblDelete);
		btnAdd = (Button)v.findViewById(R.id.btnAdd);
		lblListSum = (TextView)v.findViewById(R.id.lblListSum);
		lblListPayed = (TextView)v.findViewById(R.id.lblListPayed);
		txtPlace = (EditText)v.findViewById(R.id.txtPlace);
		lblAverage = (TextView)v.findViewById(R.id.lblListAverage);
		btnRenew =(Button)v.findViewById(R.id.btnRenew);
		laySum = (LinearLayout)v.findViewById(R.id.laySumUp);
		txtDate.setText(ut.getSlashYmdFromPlaneYmd(ymd));

		con = getActivity();
		editable = false;
//		lblDelete.setVisibility(View.INVISIBLE);
//		lblDelete.setWidth(0);

		txtDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DatePickFragment dpf = new DatePickFragment("年月日選択"
						,txtDate.getText().toString()
						,txtPlace.getText().toString()
						,true);
				dpf.setTargetFragment(EventListFragment.this, 0);
				dpf.show(manager, "MyDialog");
				
			}
		});
		//アダプターから呼ぶメソッド
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				labelReset();
			}
		});
		//リストビューのポジションを記憶する
		lv.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {					
			}		
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				listPosition = firstVisibleItem;			
			}
		});
		
		laySum.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			//一人当たり予算・合計予算設定ダイアログを開く
				DialogDefaultPaymentSetting ddps = new DialogDefaultPaymentSetting(ViewConst.TITLE_DEFAULTPAYMENT
						,defaultPrice
						,totalYosan);
				ddps.show(manager, ViewConst.TITLE_DEFAULTPAYMENT);
			}
		});
		//登録ボタン押下時処理
		btnInsert.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if(al!=null&&!al.isEmpty()){
					if(txtPlace.getText().toString().isEmpty()){
						Toast.makeText(getActivity(), ViewConst.MSG_NOPLACE, Toast.LENGTH_SHORT).show();
						return;
					}
					listPosition = lv.getFirstVisiblePosition();
					al = adSimple.al;
					try {
						MainActivity.mEventRecordDAO.deleteData(ut.getIntYmdFromSlashYmd(txtDate.getText().toString())
								,txtPlace.getText().toString());
						for(int i = 0;i<al.size();i++){
							al.get(i).setYmd(ut.getIntYmdFromSlashYmd(txtDate.getText().toString()));
							MainActivity.mEventRecordDAO.insertEventRecord(al.get(i)
									,ut.getIntYmdFromSlashYmd(txtDate.getText().toString())
									, txtPlace.getText().toString());
							lv.setSelection(listPosition);
							Toast.makeText(getActivity(), ViewConst.MSG_UPDATE, Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
	
						e.printStackTrace();
						Toast.makeText(getActivity(), "damepo", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(getActivity(), ViewConst.MSG_NO_DATA, Toast.LENGTH_SHORT).show();
					return;
				}

			}
		});
		//編集ボタン押下処理
		btnEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(al.isEmpty()){
					Toast.makeText(getActivity(), ViewConst.MSG_NO_DATA, Toast.LENGTH_SHORT).show();
					return;
				}
				if(txtPlace.getText().toString().isEmpty()){
					Toast.makeText(getActivity(), ViewConst.MSG_NOPLACE, Toast.LENGTH_SHORT).show();
					return;
				}


				if(editable){
					editable = false;
//					lblDelete.setVisibility(View.INVISIBLE);
//					lblDelete.setWidth(0);
					btnDelete.setVisibility(View.INVISIBLE);
					btnEdit.setText("編集");
					//削除フラグを全てオフにする
					for(EventListItem item:adSimple.al){
						item.setDeleteFlg(0);
					}
					adSimple.al = al;
				}else{
					editable = true;
//					lblDelete.setVisibility(View.VISIBLE);
//					lblDelete.setWidth(80);
					btnDelete.setVisibility(View.VISIBLE);
					btnEdit.setText("取消");

				}

				resumeListSimple();
				lv.setSelection(listPosition);


			}
		});
		//削除ボタン押下
		btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				setUpdateArraySimple();

				editable = false;
//				lblDelete.setVisibility(View.INVISIBLE);
//				lblDelete.setWidth(0);

				resumeListSimple();

				lv.setSelection(listPosition);
				btnDelete.setVisibility(View.INVISIBLE);
				btnEdit.setText("編集");
				labelReset();
			}

		});
		btnDelete.setVisibility(View.INVISIBLE);

		//+ボタン押下
		btnAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if(al.isEmpty()){
					DatePickFragment dpf = new DatePickFragment("年月日選択"
							,txtDate.getText().toString()
							,txtPlace.getText().toString()
							,false);
					dpf.setTargetFragment(EventListFragment.this, 0);
					dpf.show(manager, "MyDialog");
				}else{
					addListItemSimple();
				}

			}


		});
		lblAverage.setText("平均:");
		if(al.isEmpty()){
			btnAdd.setText("NEW");		
		}else{
			labelReset();
			btnAdd.setText("+");
		}
		//NEWボタン押下
		btnRenew.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				shokikasuruyo();

			}
		});

		return v;
	}

	public void initListView(){

		if(al!=null&&!al.isEmpty()){
			txtDate.setText(ut.getSlashYmdFromPlaneYmd((al.get(0).getYmd()+"")));
			setTextPlace(al.get(0).getPlace());
			adSimple = new EventListItemSimpleAdapter(con, al, editable,manager);

			lv.setAdapter(adSimple);
			labelReset();
		}
		if(al.isEmpty()){
			btnAdd.setText("NEW");		
		}else{
			labelReset();
			btnAdd.setText("+");
		}
	}
	public void resumeFromSearch(String ymd ,String place){

		arraySetting(ymd,place);
		if(al!=null&&!al.isEmpty()){
			adSimple = new EventListItemSimpleAdapter(con,al,editable,manager);
			lv.setAdapter(adSimple);
			setTextPlace(place);
			setYmd(ut.getSlashYmdFromPlaneYmd(ymd));
			labelReset();
		}
		if(al.isEmpty()){
			btnAdd.setText("NEW");		
		}else{
			labelReset();
			btnAdd.setText("+");
		}
	}

	//リスト再設定
	public void resumeListSimple(){

		if(al!=null&&!al.isEmpty()){
			al = new ArrayList< EventListItem>();
			al = adSimple.al;
			adSimple = new  EventListItemSimpleAdapter(con,al,editable,manager);
			lv.setAdapter(adSimple);
		}else{
			al = new ArrayList< EventListItem>();
			al = getEmptyArray(1);
			adSimple = new  EventListItemSimpleAdapter(con,al,editable,manager);
			lv.setAdapter(adSimple);
		}
		if(al.isEmpty()){
			btnAdd.setText("NEW");		
		}else{
			labelReset();
			btnAdd.setText("+");
		}
	}
	public void resumeListSimple( EventListItem item,int position){
		if(al!=null&&!al.isEmpty()){
			al.set(position, item);
			adSimple = new  EventListItemSimpleAdapter(con,al,editable,manager);
			lv.setAdapter(adSimple);
			labelReset();
		}
		if(al.isEmpty()){
			btnAdd.setText("NEW");		
		}else{
			btnAdd.setText("+");
		}

	}
	//削除用メソッド
	public void setUpdateArraySimple(){
		if(al!=null&&!al.isEmpty()){
			ArrayList<EventListItem> list = new ArrayList<EventListItem>();
			EventListItem item = new EventListItem();
			try {
				MainActivity.mEventRecordDAO.deleteData(ut.getIntYmdFromSlashYmd(txtDate.getText().toString())
						, txtPlace.getText().toString());
				for(int i=0;i<al.size();i++){
					item = adSimple.al.get(i);
					MainActivity.mEventRecordDAO.insertEventRecord(item
							,ut.getIntYmdFromSlashYmd(txtDate.getText().toString())
							, txtPlace.getText().toString());
				}

			} catch (Exception e1) {
	
				e1.printStackTrace();
			}
			list = MainActivity.mEventRecordDAO.getEventRecords(ut.getIntYmdFromSlashYmd(txtDate.getText().toString()), txtPlace.getText().toString());
			adSimple.al = new ArrayList< EventListItem>();
			adSimple.al = list;
		}else{
			al = getEmptyArray(1);
			adSimple.al = new ArrayList< EventListItem>();
			adSimple.al = al;
		}
	}

	public void addEasiItemSimple(){
		al = getEmptyArray(1);
		adSimple = new EventListItemSimpleAdapter(getActivity(), al, editable, manager);
		lv.setAdapter(adSimple);
	}

	public void addListItemSimple(){

		if(al!=null&&!al.isEmpty()){
			al = adSimple.al;
			EventListItem i = new  EventListItem();
			i.setPay(getDefaultPrice());
			al.add(i);
			adSimple.al= al;
			lv.setAdapter(adSimple);
			lv.setSelection(adSimple.al.size()-1);
			labelReset();
		}else{
			//検索またはかんたんリスト作成を促す
			FragmentManager manager = getActivity().getFragmentManager();
			AlertDataFragment adf = new AlertDataFragment("選択画面");
			adf.setTargetFragment(EventListFragment.this, 0);
			adf.show(manager, "MyDialog");
		}
		if(al.isEmpty()){
			btnAdd.setText("NEW");		
		}else{
			btnAdd.setText("+");
		}
	}


	//レイアウトテンプレ作成用
	public ArrayList<EventListItem> getEmptyArray(int size){
		ArrayList<EventListItem> ar = new ArrayList<EventListItem>();
		EventListItem item = new EventListItem();
		//引数の数だけ配列を作成する
		for(int i = 0; i<size; i++){
			item = new EventListItem();
			//			item.setNameFi("名無し"+i+"");
			item.setPay(getDefaultPrice());
			ar.add(item);
		}

		return ar;
	}
	public void arraySetting(String ymd, String place){
		al = new ArrayList< EventListItem>();
		al= MainActivity.mEventRecordDAO.getEventRecords(Integer.parseInt(ymd),place);
	}


	public void setYmd(String ymd){
		txtDate.setText(ymd);		
	}
	public void setListSum(){
		if(al!=null&&!al.isEmpty()){
			lblListSum.setText("合計:"+getListSum()+"円");
		}else{
			lblListSum.setText("合計:");
		}
	}
	public void setAverage(){
		if(al!=null&&!al.isEmpty()){
			lblAverage.setText("平均:"+(Integer.parseInt(getListSum())/al.size())+"円");
		}else{
			lblAverage.setText("平均:");
		}
		setBtnRenew();
	}
	public void setListPayed(){
		lblListPayed.setText(getListPayed());
	}

	public String getListSum(){
		int sum =0;

		for(int i =0;i<adSimple.al.size();i++){
			sum += adSimple.al.get(i).getPay();
		}
		return sum +"";		
	}
	public String getListPayed(){
		int sum =0;
		int total = 0;

		total = adSimple.al.size();
		for(int i =0;i<adSimple.al.size();i++){
			if(adSimple.al.get(i).getPayFlg()==1)sum +=1;
		}

		return  sum+"/"+total;
	}


	public void setTextPlace(String s){
		txtPlace.setText(s);
	}
	public ArrayList<EventListItem> getAl() {
		return al;
	}
	public void setAl(ArrayList<EventListItem> al) {
		this.al = al;
	}
	public void setBtnRenew(){
		if(al.isEmpty()){
			btnRenew.setVisibility(View.INVISIBLE);
		}else{
			btnRenew.setVisibility(View.VISIBLE);
		}
	}
	public void shokikasuruyo(){
		al = new ArrayList<EventListItem>();
		adSimple.clear();
		txtDate.setText(ut.getTodsaySlashYmd());
		txtPlace.setText("");
		labelReset();
		btnAdd.setText("NEW");
		btnRenew.setVisibility(View.INVISIBLE);
	}
	public void labelReset(){
		setListPayed();
		setAverage();
		setListSum();
		
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
		setAverage();
		setListSum();
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
