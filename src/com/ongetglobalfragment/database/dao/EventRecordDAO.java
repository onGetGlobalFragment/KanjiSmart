package com.ongetglobalfragment.database.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ongetglobalfragment.database.DBconst;
import com.ongetglobalfragment.database.SqliteDB;
import com.ongetglobalfragment.database.model.EventListItem;
import com.ongetglobalfragment.database.model.EventListSumItem;

public class EventRecordDAO {

	private SqliteDB sdb;
	private static final String SQL_SELECT = "SELECT ";
	private static final String SQL_FROM = " FROM ";
	private static final String SQL_WHERE = " WHERE ";
	private static final String SQL_GROUP = " GROUP BY ";
	private static final String SQL_ORDER = " ORDER BY ";
	
	public EventRecordDAO(Context con) {
		sdb = new SqliteDB(con);
	}

	//データが存在するかどうかを返す
	public boolean isData(){
		boolean flag =false;
		SQLiteDatabase db =sdb.getReadableDatabase();
		db.beginTransaction();
		String[] cols = {DBconst.YMD
		};
		Cursor cs = db.query(SqliteDB.EVENT_RECORDS
				, cols
				, null, null, null, null, null, null);
		try{
			if (cs.getCount()>0)flag = true;

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
			cs.close();
			db.close();
		}

		return flag;
	}
	//指定の日付・場所のデータが存在するかどうかを返す
	public boolean isData(int ymd, String place){
		boolean flag =false;
		SQLiteDatabase db =sdb.getReadableDatabase();
		db.beginTransaction();
		String[] params = {Integer.toString(ymd)
				,place
		};
		String where = 
				DBconst.YMD + "=? and "+ 
						DBconst.PLACE + "=?";

		Cursor cs = db.query(SqliteDB.EVENT_RECORDS
				, null
				, where
				, params
				, null, null, null, null);
		try{
			if (cs.getCount()>0)flag = true;
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
			cs.close();
			db.close();
		}
		return flag;
	}
	//指定の日付より前
	public ArrayList<EventListSumItem> getEventListBefore(int ymd)throws Exception{
		ArrayList<EventListSumItem> list = new ArrayList<EventListSumItem>();

		EventListSumItem item = new EventListSumItem();

		SQLiteDatabase db = sdb.getReadableDatabase();
		db.beginTransaction();
		StringBuilder sql = new StringBuilder();
		sql.append(SQL_SELECT).append(DBconst.YMD).append(",").append(DBconst.PLACE+ " , ").append("count(*) as count")
		.append(SQL_FROM).append(SqliteDB.EVENT_RECORDS)
		.append(SQL_WHERE).append(DBconst.YMD+" <= ").append(ymd)
		.append(SQL_GROUP).append(DBconst.YMD+","+DBconst.PLACE)
		.append(SQL_ORDER+DBconst.YMD);
		Cursor cs = db.rawQuery(sql.toString(), null);
		if(cs.getCount()>0){
			int index_count = cs.getColumnIndex("count");
			int index_YMD = cs.getColumnIndex(DBconst.YMD);
			int index_PLACE = cs.getColumnIndex(DBconst.PLACE);
			cs.moveToFirst();
			for(int i=0;i<cs.getCount();i++){
				item = new EventListSumItem();
				item.setListSum(cs.getInt(index_count));
				item.setYmd(cs.getInt(index_YMD));
				item.setPlace(cs.getString(index_PLACE));
				list.add(item);
				cs.moveToNext();
			}
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
		return list;
	}
	//指定の日付より後
	public ArrayList<EventListSumItem> getEventListAfter(int ymd) throws Exception{
		ArrayList<EventListSumItem> list = new ArrayList<EventListSumItem>();
		EventListSumItem item = new EventListSumItem();
		SQLiteDatabase db =sdb.getReadableDatabase();
		db.beginTransaction();
		StringBuilder sql = new StringBuilder();
		sql.append(SQL_SELECT).append(DBconst.YMD).append(",").append(DBconst.PLACE+" , ").append("count(*) as count ")
		.append(SQL_FROM).append(SqliteDB.EVENT_RECORDS)
		.append(SQL_WHERE).append(DBconst.YMD+" >= ").append(ymd)
		.append(SQL_GROUP).append(DBconst.YMD+","+DBconst.PLACE)
		.append(SQL_ORDER+DBconst.YMD);
		Cursor cs = db.rawQuery(sql.toString(), null);
		if(cs.getCount()>0){
			int index_count = cs.getColumnIndex("count");
			int index_YMD = cs.getColumnIndex(DBconst.YMD);
			int index_PLACE = cs.getColumnIndex(DBconst.PLACE);
			cs.moveToFirst();
			for(int i=0;i<cs.getCount();i++){
				item = new EventListSumItem();
				item.setListSum(cs.getInt(index_count));
				item.setYmd(cs.getInt(index_YMD));
				item.setPlace(cs.getString(index_PLACE));
				list.add(item);
				cs.moveToNext();
			}
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
		return list;
	}
	//指定の日付の間
	public ArrayList<EventListSumItem> getEventListBetween(String a, String b)throws Exception{
		ArrayList<EventListSumItem> list = new ArrayList<EventListSumItem>();

		EventListSumItem item = new EventListSumItem();

		SQLiteDatabase db = sdb.getReadableDatabase();
		db.beginTransaction();
		StringBuilder sql = new StringBuilder();
		sql.append(SQL_SELECT).append(DBconst.YMD).append(",").append(DBconst.PLACE+", ").append("count(*) as count ")
		.append(SQL_FROM).append(SqliteDB.EVENT_RECORDS)
		.append(SQL_WHERE).append(DBconst.YMD+" between ").append(a+" and "+ b)
		.append(SQL_GROUP).append(DBconst.YMD+","+DBconst.PLACE)
		.append(SQL_ORDER+DBconst.YMD);
		Cursor cs = db.rawQuery(sql.toString(), null);
		if(cs.getCount()>0){
			int index_count = cs.getColumnIndex("count");
			int index_YMD = cs.getColumnIndex(DBconst.YMD);
			int index_PLACE = cs.getColumnIndex(DBconst.PLACE);
			cs.moveToFirst();
			for(int i=0;i<cs.getCount();i++){
				item = new EventListSumItem();
				item.setListSum(cs.getInt(index_count));
				item.setYmd(cs.getInt(index_YMD));
				item.setPlace(cs.getString(index_PLACE));
				list.add(item);
				cs.moveToNext();
			}
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
		return list;
	}
	//指定の日付、場所のレコード一覧を取得
	public ArrayList<EventListItem> getEventRecord(String ymd,String place)throws Exception{
		ArrayList<EventListItem> list = new ArrayList<EventListItem>();
		EventListItem item = new EventListItem();
		SQLiteDatabase db = sdb.getReadableDatabase();
		db.beginTransaction();
		String[] params = {ymd
				,place
		};
		String where = 
				DBconst.YMD + "=? and "+ 
						DBconst.PLACE + "=?";
		String order = DBconst.KANA_LAST+","+DBconst.KANA_FIRST;
		Cursor cs = db.query(SqliteDB.EVENT_RECORDS, null, where, params, null, null, order, null);
		if(cs.getCount()>0){

			int index_NAME_LAST = cs.getColumnIndex(DBconst.NAME_LAST);
			int index_NAME_FIRST = cs.getColumnIndex(DBconst.NAME_FIRST);
			int index_KANA_LAST = cs.getColumnIndex(DBconst.KANA_LAST);
			int index_KANA_FIRST = cs.getColumnIndex(DBconst.KANA_FIRST);
			int index_YMD = cs.getColumnIndex(DBconst.YMD);
			int index_AGE = cs.getColumnIndex(DBconst.AGE);
			int index_SEX = cs.getColumnIndex(DBconst.SEX);
			int index_ATTENDANCE = cs.getColumnIndex(DBconst.ATTENDANCE);
			int index_PAY = cs.getColumnIndex(DBconst.PAY);
			int index_PAY_FLAG = cs.getColumnIndex(DBconst.PAY_FLAG);
			int index_POSITION = cs.getColumnIndex(DBconst.POSITION);
			int index_PLACE = cs.getColumnIndex(DBconst.PLACE);
			int indexphone = cs.getColumnIndex(DBconst.PHONE);	
			int indexanother = cs.getColumnIndex(DBconst.ANOTHER);	
			
			cs.moveToFirst();
			for(int i=0;i<cs.getCount();i++){
				item = new EventListItem();
				item.setNameLa(cs.getString(index_NAME_LAST));
				item.setNameFi(cs.getString(index_NAME_FIRST));
				item.setKanaLa(cs.getString(index_KANA_LAST));
				item.setKanaFi(cs.getString(index_KANA_FIRST));
				item.setYmd(cs.getInt(index_YMD));
				item.setAge(cs.getInt(index_AGE));
				item.setSex(cs.getInt(index_SEX));
				item.setAttend(cs.getInt(index_ATTENDANCE));				
				item.setPay(cs.getInt(index_PAY));
				item.setPayFlg(cs.getInt(index_PAY_FLAG));
				item.setPosition(cs.getString(index_POSITION));
				item.setPlace(cs.getString(index_PLACE));
				item.setPhone(cs.getString(indexphone));
				item.setAnother(cs.getString(indexanother));
				list.add(item);
				cs.moveToNext();				
			}


		}
		return list;
	}
	//指定の日付のレコードを削除
		public void deleteData(int ymd)throws Exception{
			SQLiteDatabase db = sdb.getWritableDatabase();
			String[] delParams = {ymd+""				
			};
			String delWhere = DBconst.YMD + "=?";
			db.beginTransaction();
			db.delete(SqliteDB.EVENT_RECORDS, delWhere, delParams);
			db.setTransactionSuccessful();
			db.endTransaction();
			db.close();
		}
	//指定の日付・場所のレコードを削除
	public void deleteData(int ymd,String place)throws Exception{
		SQLiteDatabase db = sdb.getWritableDatabase();
		String[] delParams = {ymd+""
				,place
		};
		String delWhere = 
				DBconst.YMD + "=? and "+ 
						DBconst.PLACE + "=?";
		db.beginTransaction();
		db.delete(SqliteDB.EVENT_RECORDS, delWhere, delParams);
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
	}
	//新規登録
	public void insertEventRecord(EventListItem i,int ymd,String place)throws Exception{
		SQLiteDatabase db = sdb.getWritableDatabase();
		EventListItem item = new EventListItem();
		item = i;
		//削除フラグ＝１（ON）の場合、スキップ
		if(item.getDeleteFlg()==1)return;
		ContentValues cv = new ContentValues();
		db.beginTransaction();

			item.setYmd(ymd);
			item.setPlace(place);		
			cv.put(DBconst.KANA_LAST, item.getKanaLa());
			cv.put(DBconst.KANA_FIRST, item.getKanaFi());
			cv.put(DBconst.NAME_LAST, item.getNameLa());
			cv.put(DBconst.NAME_FIRST, item.getNameFi());
			cv.put(DBconst.SEX, item.getSex());
			cv.put(DBconst.AGE, item.getAge());
			cv.put(DBconst.PAY, item.getPay());
			cv.put(DBconst.PAY_FLAG, item.getPayFlg());
			cv.put(DBconst.PHONE, item.getPhone());
			cv.put(DBconst.POSITION, item.getPosition());
			cv.put(DBconst.ANOTHER,item.getAnother());
			cv.put(DBconst.YMD,item.getYmd());
			cv.put(DBconst.PLACE, item.getPlace());			
			db.insert(SqliteDB.EVENT_RECORDS, null, cv);	
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();	
	}
	//まとめて処理したい場合
	public void insertEventRecords(ArrayList<EventListItem> list)throws Exception{
		SQLiteDatabase db = sdb.getWritableDatabase();
		ContentValues cv = new ContentValues();
		db.beginTransaction();
		for(EventListItem item:list){
			//削除フラグ＝１（ON）の場合、スキップ
			if(item.getDeleteFlg()==1)continue;
			cv = new ContentValues();
			cv.put(DBconst.KANA_LAST, item.getKanaLa());
			cv.put(DBconst.KANA_FIRST, item.getKanaFi());
			cv.put(DBconst.NAME_LAST, item.getNameLa());
			cv.put(DBconst.NAME_FIRST, item.getNameFi());
			cv.put(DBconst.SEX, item.getSex());
			cv.put(DBconst.AGE, item.getAge());
			cv.put(DBconst.PAY, item.getPay());
			cv.put(DBconst.PAY_FLAG, item.getPayFlg());
			cv.put(DBconst.PHONE, item.getPhone());
			cv.put(DBconst.POSITION, item.getPosition());
			cv.put(DBconst.ANOTHER,item.getAnother());
			cv.put(DBconst.YMD,item.getYmd());
			cv.put(DBconst.PLACE, item.getPlace());			
			db.insert(SqliteDB.EVENT_RECORDS, null, cv);	
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
	}
	//上書きしない
	public void setData(ArrayList<EventListItem> array)throws Exception{
		SQLiteDatabase db = sdb.getWritableDatabase();
		EventListItem item = new EventListItem();
		ContentValues cv = new ContentValues();
		db.beginTransaction();
		String[] delParams = {item.getYmd()+""
				,item.getPlace()
		};
		String delWhere = 
				DBconst.YMD + "=? and "+ 
						DBconst.PLACE + "=?";

		db.delete(SqliteDB.EVENT_RECORDS, delWhere, delParams);
		for(int i=0;i<array.size();i++){
			item = array.get(i);
			cv.put(DBconst.KANA_LAST, item.getKanaLa());
			cv.put(DBconst.KANA_FIRST, item.getKanaFi());
			cv.put(DBconst.NAME_LAST, item.getNameLa());
			cv.put(DBconst.NAME_FIRST, item.getNameFi());
			cv.put(DBconst.SEX, item.getSex());
			cv.put(DBconst.AGE, item.getAge());
			cv.put(DBconst.PAY, item.getPay());
			cv.put(DBconst.PAY_FLAG, item.getPayFlg());
			cv.put(DBconst.PHONE, item.getPhone());
			cv.put(DBconst.POSITION, item.getPosition());
			cv.put(DBconst.ANOTHER,item.getAnother());
			cv.put(DBconst.YMD,item.getYmd());
			cv.put(DBconst.PLACE, item.getPlace());
			db.insert(SqliteDB.EVENT_RECORDS, null, cv);
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
	}
	
	public ArrayList<EventListItem> getEventRecords(int ymd, String place){
		ArrayList<EventListItem> list = new ArrayList<EventListItem>();
		SQLiteDatabase db = sdb.getReadableDatabase();
		db.beginTransaction();
		String[] cols = {
				DBconst.NAME_LAST
				,DBconst.NAME_FIRST
				,DBconst.KANA_LAST
				,DBconst.KANA_FIRST
				,DBconst.YMD
				,DBconst.AGE
				,DBconst.SEX
				,DBconst.ATTENDANCE
				,DBconst.PAY
				,DBconst.PAY_FLAG
				,DBconst.POSITION
				,DBconst.PLACE
				,DBconst.PHONE
				,DBconst.ANOTHER

		};
		String[] params = {Integer.toString(ymd),place};
		String where = DBconst.YMD + "=? and "+ DBconst.PLACE + "=?";
		String order = DBconst.KANA_LAST + ","+DBconst.KANA_FIRST;
		Cursor cs = db.query(SqliteDB.EVENT_RECORDS, cols, where, params, null, null, order, null);
		try{
			int csCount = cs.getCount();

			//レコード数が０以上の時のみ処理
			if (csCount > 0){
				//インデックス取得
				int nameLa = cs.getColumnIndex(DBconst.NAME_LAST);
				int nameFi = cs.getColumnIndex(DBconst.NAME_FIRST);
				int kanaLa = cs.getColumnIndex(DBconst.KANA_LAST);
				int kanaFi = cs.getColumnIndex(DBconst.KANA_FIRST);
				int indexymd = cs.getColumnIndex(DBconst.YMD);
				int age = cs.getColumnIndex(DBconst.AGE);
				int sex = cs.getColumnIndex(DBconst.SEX);
				int att = cs.getColumnIndex(DBconst.ATTENDANCE);
				int py = cs.getColumnIndex(DBconst.PAY);
				int pflg = cs.getColumnIndex(DBconst.PAY_FLAG);
				int posi = cs.getColumnIndex(DBconst.POSITION);
				int indexplace = cs.getColumnIndex(DBconst.PLACE);	
				int indexphone = cs.getColumnIndex(DBconst.PHONE);	
				int indexanother = cs.getColumnIndex(DBconst.ANOTHER);	
				//カーソルを先頭に持ってくる
				if(cs.moveToFirst()){
					for(int i = 0; i<csCount; i++){
						EventListItem li = new EventListItem();	
						li.setNameLa(cs.getString(nameLa));
						li.setNameFi(cs.getString(nameFi));
						li.setKanaLa(cs.getString(kanaLa));
						li.setKanaFi(cs.getString(kanaFi));
						li.setYmd(cs.getInt(indexymd));
						li.setAge(cs.getInt(age));
						li.setSex(cs.getInt(sex));
						li.setAttend(cs.getInt(att));
						li.setPay(cs.getInt(py));
						li.setPayFlg(cs.getInt(pflg));
						li.setPosition(cs.getString(posi));
						li.setPlace(cs.getString(indexplace));
						li.setPhone(cs.getString(indexphone));
						li.setAnother(cs.getString(indexanother));
						if(li.getKanaLa()==null)li.setKanaLa("");
						if(li.getKanaFi()==null)li.setKanaFi("");
						if(li.getNameLa()==null)li.setNameLa("");
						if(li.getNameFi()==null)li.setNameFi("");
						if(li.getPosition()==null)li.setPosition("");
						if(li.getPhone()==null)li.setPhone("");
						if(li.getAnother()==null)li.setAnother("");
						list.add(li);

						cs.moveToNext();				
					}
				}
			}			
			cs.close();
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
			db.close();
		}				
		return list;
	}
}
