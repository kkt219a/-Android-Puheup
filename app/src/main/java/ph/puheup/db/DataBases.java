package ph.puheup.db;

import android.provider.BaseColumns;

// DataBase Table
public final class DataBases {
	
	public static final class CreateDB implements BaseColumns{
		public static final String NAME = "name";
		public static final String MONEY = "money"; // contact>> money
		public static final String CONTENT = "content"; // email >> content
		public static final String DAY = "day"; // 날짜

		public static final String _TABLENAME = "moneysupervise"; // 테이블명 - 돈관리
		public static final String _CREATE = 
			"create table "+_TABLENAME+"("
					+_ID+" integer primary key autoincrement, "
					+NAME+" text not null , "
					+MONEY+" text not null , "
					+CONTENT+" text not null , "
					+DAY+" text not null );";
	}
}
