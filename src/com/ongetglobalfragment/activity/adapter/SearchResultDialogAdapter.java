package com.ongetglobalfragment.activity.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ongetglobalfragment.R;
import com.ongetglobalfragment.database.model.EventListSumItem;


public class SearchResultDialogAdapter extends ArrayAdapter<EventListSumItem> {

	private LayoutInflater inflater;
	public ArrayList<EventListSumItem> al;
	private EventListSumItem item;
//	private Context con;


	public class ViewHolder {
		//items of a row of list
		private TextView txtYmd;
		private TextView txtPlace;
		private TextView txtCount;


		public ViewHolder(View base) {	
			this.txtYmd = (TextView)base.findViewById(R.id.txtResultYmd);
			this.txtPlace = (TextView)base.findViewById(R.id.txtResultPlace);
			this.txtCount = (TextView)base.findViewById(R.id.txtResultCount);

		}
	}

	public SearchResultDialogAdapter(Context context,ArrayList<EventListSumItem> list){
		super(context,R.layout.list_item_searchresult,list);
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.item = new EventListSumItem();
		this.al = list;
//		this.con = context;

	}



	@SuppressLint("ViewHolder")
	public View getView(final int position,  View convertView, ViewGroup parent) {
		 
		item = al.get(position);
		//行viewの取得
		View view = convertView;
		view = inflater.inflate(R.layout.list_item_searchresult, parent, false);

		//ホルダの取得
		ViewHolder vh = (ViewHolder) view.getTag();

		vh = new ViewHolder(view);
		view.setTag(vh);

		String ymd = item.getYmd()+"";
		if(ymd.length()==8){
			CharSequence cs = ymd.subSequence(0, ymd.length());
			StringBuilder sb = new StringBuilder();
			sb.append(cs.charAt(0)).append(cs.charAt(1)).append(cs.charAt(2)).append(cs.charAt(3))
			.append("/").append(cs.charAt(4)).append(cs.charAt(5)).append("/").append(cs.charAt(6)).append(cs.charAt(7));
			vh.txtYmd.setText(sb.toString());
		}else{
			vh.txtYmd.setText("");
		}
		vh.txtPlace.setText(item.getPlace());
		vh.txtCount.setText(item.getListSum()+"");

		return(view);	
	}	


}
