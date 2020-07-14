package ph.puheup.db;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ph.puheup.R;

import static ph.puheup.db.InsertActivity.doWhileCursorToArray;

public class TestDataBaseActivity extends Activity {

	public int n=1;
	private static final String TAG = "TestDataBaseActivity";
	public static DbOpenHelper mDbOpenHelper;
	public static ListView mListView;
	public static Cursor mCursor;
	public static InfoClass mInfoClass;
	public static ArrayList<InfoClass> mInfoArray;
	public static CustomAdapter mAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		mListView = (ListView) findViewById(R.id.lv_list);

        // DB Create and Open
        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();

		//예시
       // mDbOpenHelper.insertColumn("김진혁","25000원" , "삼구포차","20160915"); //
        
//        startManagingCursor(mCursor);
        
        
        mInfoArray = new ArrayList<InfoClass>();
        
        doWhileCursorToArray();
        
        for(InfoClass i : mInfoArray){
        	Log.d(TAG, "ID = " + i._id);
        	Log.d(TAG, "name = " + i.name);
        	Log.d(TAG, "money = " + i.money);
        	Log.d(TAG, "content = " + i.content);
			Log.d(TAG, "day = " + i.day);
        }

        mAdapter = new CustomAdapter(this, mInfoArray);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemLongClickListener(longClickListener);
    }
    
    @Override
    protected void onDestroy() {
    	mDbOpenHelper.close();
    	super.onDestroy();
    }
    
    
    /**
     * ListView의 Item을 롱클릭 할때 호출 ( 선택한 아이템의 DB 컬럼과 Data를 삭제 한다. )
     */
    private OnItemLongClickListener longClickListener = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int position, long arg3) {
			
			Log.e(TAG, "position = " + position);

			boolean a=false;
			for(int i=1;i<50000;i++) {
				boolean result = mDbOpenHelper.deleteColumn(position + i);
				if(result) {
					a=true;
					break;
				}
			}
			Log.e(TAG, "result = " + a);
			
			if(a){
				mInfoArray.remove(position);
				mAdapter.setArrayList(mInfoArray);
				mAdapter.notifyDataSetChanged();
			}else {
				Toast.makeText(getApplicationContext(), "INDEX를 확인해 주세요.", 
						Toast.LENGTH_LONG).show();
			}
			
			return false;
		}
	};
	
	
	/**
	 * DB에서 받아온 값을 ArrayList에 Add
	 */

    public void onClick001(View v){
		Intent intent = new Intent(this, ph.puheup.db.InsertActivity.class);
		startActivity(intent);
	}

}




