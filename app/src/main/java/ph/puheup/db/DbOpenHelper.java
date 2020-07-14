package ph.puheup.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper {

	private static final String DATABASE_NAME = "moneysupervise.db";
	private static final int DATABASE_VERSION = 1;
	public static SQLiteDatabase mDB; // 실제 데이터베이스 mDB 생성
	private DatabaseHelper mDBHelper; // DB생성,관리 도와줌
	private Context mCtx;

	private class DatabaseHelper extends SQLiteOpenHelper{

		// 생성자
		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		// 최초 DB를 만들때 한번만 호출된다.
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DataBases.CreateDB._CREATE);

		}

		// 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.DATABASE_VERSION = 1, 2, 3 이런식으로 수정해 주시면 자동
		//으로 기존의 TABLE을 삭제하고 새로운 TABLE을 만들어 줍니다
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS "+DataBases.CreateDB._TABLENAME);
			onCreate(db);
		}
	}

	public DbOpenHelper(Context context){
		this.mCtx = context;
	}

	public DbOpenHelper open() throws SQLException{
		mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
		//DB를 사용하기위해 생성하거나 열어 줍니다.DB를 읽거나 쓸수 있는 권한을 부여 합니다
		mDB = mDBHelper.getWritableDatabase();
		return this;
	}

	public void close(){
		mDB.close();
	}

	// DB입력,코드 -> 데이터베이스에 자료 입력함 >> values
	public long insertColumn(String name, String money, String content, String day){
		ContentValues values = new ContentValues();
		//자료입력후
		values.put(DataBases.CreateDB.NAME, name);
		values.put(DataBases.CreateDB.MONEY, money);
		values.put(DataBases.CreateDB.CONTENT, content);
		values.put(DataBases.CreateDB.DAY, day);
		//DB추가
		return mDB.insert(DataBases.CreateDB._TABLENAME, null, values);
	}

	// DB업데이트시
	public boolean updateColumn(long id , String name, String money, String content, String day){
		ContentValues values = new ContentValues();
		//자료입력후
		values.put(DataBases.CreateDB.NAME, name);
		values.put(DataBases.CreateDB.MONEY, money);
		values.put(DataBases.CreateDB.CONTENT, content);
		values.put(DataBases.CreateDB.DAY, day);
		//DB추가, id값 넣어주면서
		return mDB.update(DataBases.CreateDB._TABLENAME, values, "_id="+id, null) > 0;
	}

	// 칼럼삭제, 테이터베이스에서 id삭제
	public boolean deleteColumn(long id){
		return mDB.delete(DataBases.CreateDB._TABLENAME, "_id="+id, null) > 0;
	}
	
	// 칼럼삭제, 데이터베이스에서 내용싹다삭제,contact 숫자만큼
	public boolean deleteColumn(String number){
		return mDB.delete(DataBases.CreateDB._TABLENAME, "contact="+number, null) > 0;
	}
	
	// 전체선택
	//데이터베이스 -> 코드 데이터 가져오기
	public Cursor getAllColumns(){
		return mDB.query(DataBases.CreateDB._TABLENAME, null, null, null, null, null, null);
	}

	// ID 컬럼 얻어 오기
	public Cursor getColumn(long id){
		Cursor c = mDB.query(DataBases.CreateDB._TABLENAME, null, 
				"_id="+id, null, null, null, null);
		if(c != null && c.getCount() != 0)
			c.moveToFirst();
		return c;
	}

	// 이름 검색 하기 (rawQuery)
	public Cursor getMatchName(String name){
		Cursor c = mDB.rawQuery( "select * from address where name=" + "'" + name + "'" , null);
		return c;
	}


}






