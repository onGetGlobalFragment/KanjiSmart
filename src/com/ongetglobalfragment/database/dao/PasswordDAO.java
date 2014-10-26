package com.ongetglobalfragment.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ongetglobalfragment.database.DBconst;
import com.ongetglobalfragment.database.SqliteDB;
import com.ongetglobalfragment.database.model.PasswordItem;

public class PasswordDAO {


	private SqliteDB sdb;
	public PasswordDAO(Context con) {

		sdb = new SqliteDB(con);
	}

	//データが存在するかどうかを返す
	public boolean isData(){
		boolean flag =false;
		SQLiteDatabase db =sdb.getReadableDatabase();
		db.beginTransaction();

		Cursor cs = db.query(SqliteDB.PASSWORD				
				, null, null, null, null, null, null, null);
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
	//かざしたIDMが登録されているかどうかを返す
	public boolean isData(String idm){
		boolean flag =false;
		SQLiteDatabase db =sdb.getReadableDatabase();
		db.beginTransaction();
		String[] cols = {DBconst.IDM};
		String where = DBconst.IDM+"=?";
		String[] param = {idm};
		Cursor cs = db.query(SqliteDB.PASSWORD				
				,cols, where, param
				, null, null, null, null);
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

	//パスワード・アドレス・ロック状態を返す
	public PasswordItem getPasswordItem(String idm){
		PasswordItem item = new PasswordItem();
		SQLiteDatabase db =sdb.getReadableDatabase();
		db.beginTransaction();
		String where = DBconst.IDM+"=?";
		String[] param = {idm};
		Cursor cs = db.query(SqliteDB.PASSWORD				
				,null, where, param
				, null, null, null, null);
		int ixIDM = cs.getColumnIndex(DBconst.IDM);
		int ixMAIL = cs.getColumnIndex(DBconst.MAIL_ADRESS);
		int ixFLAG = cs.getColumnIndex(DBconst.LOCK_FLAG);
		try{
			if (cs.getCount()>0){
				cs.moveToFirst();
				item.setPassWord(cs.getString(ixIDM));
				item.setMailAdress(cs.getString(ixMAIL));
				item.setLockFlag(cs.getInt(ixFLAG));
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
			cs.close();
			db.close();
		}
		return item;
	}
	//ロック状態を返す
	public PasswordItem getPasswordItem(){
		PasswordItem item = new PasswordItem();
		SQLiteDatabase db =sdb.getReadableDatabase();
		db.beginTransaction();
//		String[] cols = {DBconst.LOCK_FLAG};

		Cursor cs = db.query(SqliteDB.PASSWORD				
				,null, null, null
				, null, null, null, null);
					int ixIDM = cs.getColumnIndex(DBconst.IDM);
					int ixMAIL = cs.getColumnIndex(DBconst.MAIL_ADRESS);
		int ixFLAG = cs.getColumnIndex(DBconst.LOCK_FLAG);
		try{
			if (cs.getCount()>0){
				cs.moveToFirst();
									item.setPassWord(cs.getString(ixIDM));
									item.setMailAdress(cs.getString(ixMAIL));
				item.setLockFlag(cs.getInt(ixFLAG));
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
			cs.close();
			db.close();
		}
		return item;
	}
	//新規登録
	public boolean isInsert(PasswordItem item){
		boolean flag =false;
		SQLiteDatabase db = sdb.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(DBconst.IDM, item.getPassWord());
		cv.put(DBconst.MAIL_ADRESS, item.getMailAdress());
		cv.put(DBconst.LOCK_FLAG, item.getLockFlag());
		db.beginTransaction();
		try{
			db.insert(SqliteDB.PASSWORD,null, cv);
			db.setTransactionSuccessful();
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
			db.close();
		}

		return flag;
	}
	//更新
	public boolean isUpdate(PasswordItem item,String oldIdm){
		boolean flag =false;
		SQLiteDatabase db = sdb.getWritableDatabase();
		String where = DBconst.IDM+"=?";
		String[] param = {oldIdm};
		ContentValues cv = new ContentValues();
		cv.put(DBconst.IDM, item.getPassWord());
		cv.put(DBconst.MAIL_ADRESS, item.getMailAdress());
		cv.put(DBconst.LOCK_FLAG, item.getLockFlag());
		db.beginTransaction();
		try{
			db.update(SqliteDB.PASSWORD, cv, where, param);
			db.setTransactionSuccessful();
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
			db.close();
		}
		return flag;
	}
}