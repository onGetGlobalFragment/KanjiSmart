package com.ongetglobalfragment.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/** Database Object
 * @author Y.A
 * @since 2014/09/10
 * @version 1.0
 * @update 
 */

public class SqliteDB extends SQLiteOpenHelper {
	//DB名
	private static final String DBNAME = "com.willness.forfurture.sqlite";
	//DataBaseのバージョン（更新するときはバージョンを変えること)
	private static final int VERSION = 4;
	//Table名
	public static final String EVENT_RECORDS =" EVENT_RECORDS";
	public static final String PASSWORD =" PASSWORD";
	//SQL文
	public static final String SQL_CREATE = "CREATE TABLE ";
	public static final String SQL_PRIMARY = " PRIMARY KEY(";
	public static final String SQL_DROP = "DROP TABLE IF EXISTS ";
//	private static final String SQL_SELECT = "SELECT ";
//	private static final String SQL_FROM = " FROM ";
//	private static final String SQL_WHERE = " WHERE ";
//	private static final String SQL_GROUP = " GROUP BY ";
//	private static final String SQL_ORDER = " ORDER BY ";
	//列の型
	public static final String TEXT = " TEXT";
	public static final String INTEGER = " INTEGER";
	public static final String REAL = " REAL";
	public static final String BLOB = " BLOB"; 
	public static final String NUMERIC = " NUMERIC";

//	private Context con;


	//作成したコンストラクタ
	public SqliteDB(Context context) {
		super(context, DBNAME, null, VERSION);
//		this.con = context;
	}
	public SqliteDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public SqliteDB(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//CreateTable
		StringBuilder mEvents = new StringBuilder();
		mEvents.append(SQL_CREATE);
		mEvents.append(EVENT_RECORDS+" (");
		mEvents.append(DBconst.NAME_LAST+TEXT+",");
		mEvents.append(DBconst.NAME_FIRST+TEXT+",");
		mEvents.append(DBconst.KANA_LAST+TEXT+",");
		mEvents.append(DBconst.KANA_FIRST+TEXT+",");
		mEvents.append(DBconst.YMD+INTEGER+",");
		mEvents.append(DBconst.AGE+INTEGER+",");
		mEvents.append(DBconst.SEX+INTEGER+",");
		mEvents.append(DBconst.ATTENDANCE+INTEGER+",");
		mEvents.append(DBconst.PAY+INTEGER+",");
		mEvents.append(DBconst.PAY_FLAG+INTEGER+",");
		mEvents.append(DBconst.PLACE+TEXT+",");
		mEvents.append(DBconst.POSITION+TEXT+",");
		mEvents.append(DBconst.PHONE+TEXT+",");
		mEvents.append(DBconst.ANOTHER+TEXT+",");
		mEvents.append(SQL_PRIMARY);
		mEvents.append(DBconst.YMD);
		mEvents.append(", "+DBconst.PLACE);
		mEvents.append(", "+DBconst.NAME_LAST);
		mEvents.append(", "+DBconst.NAME_FIRST);
		mEvents.append("))");
		db.execSQL(mEvents.toString());
		
		StringBuilder mPass = new StringBuilder();
		mPass.append(SQL_CREATE);
		mPass.append(PASSWORD+"(");
		mPass.append(DBconst.IDM+TEXT+",");
		mPass.append(DBconst.MAIL_ADRESS+TEXT+",");
		mPass.append(DBconst.LOCK_FLAG+INTEGER+",");
		mPass.append(SQL_PRIMARY);
		mPass.append(DBconst.IDM);

		mPass.append("))");
		db.execSQL(mPass.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//テーブル削除
		db.execSQL(SQL_DROP + EVENT_RECORDS);
		db.execSQL(SQL_DROP + PASSWORD);
		//onCreateメソッドを実行
		onCreate(db);
	}


}
