package ph.puheup.db;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ph.puheup.R;

import static ph.puheup.db.TestDataBaseActivity.mAdapter;
import static ph.puheup.db.TestDataBaseActivity.mCursor;
import static ph.puheup.db.TestDataBaseActivity.mDbOpenHelper;
import static ph.puheup.db.TestDataBaseActivity.mInfoArray;
import static ph.puheup.db.TestDataBaseActivity.mInfoClass;

/**
 * Created by 김규태 on 2016-11-30.
 */

public class InsertActivity extends Activity{

    public static final String LOG_= "InsertActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert);
        setLayout();

    }

    public void onClick2(View v){
        Button button = (Button)findViewById(R.id.button07);
        button.setBackgroundResource(R.drawable.location_search_on);
        finish();
    }

    public void onClick(View v){
            switch (v.getId()) {
                case R.id.btn_add:
                    Button button1 = (Button)findViewById(R.id.btn_add);
                    button1.setBackgroundResource(R.drawable.location_search_on);
                    mDbOpenHelper.insertColumn
                            (
                                    mEditTexts[ph.puheup.db.Constants.NAME].getText().toString().trim(),
                                    mEditTexts[ph.puheup.db.Constants.MONEY].getText().toString().trim(),
                                    mEditTexts[ph.puheup.db.Constants.CONTENT].getText().toString().trim(),
                                    mEditTexts[ph.puheup.db.Constants.DAY].getText().toString().trim()
                            );


                    mInfoArray.clear();

                    doWhileCursorToArray();

                    mAdapter.setArrayList(mInfoArray);
                    mAdapter.notifyDataSetChanged();

                    mCursor.close();
                    finish();

                    break;

                default:
                    finish();
                    break;
            }
        }

    public static void doWhileCursorToArray(){

        mCursor = null;
        mCursor = mDbOpenHelper.getAllColumns();

        while (mCursor.moveToNext()) {

            mInfoClass = new InfoClass(
                    mCursor.getInt(mCursor.getColumnIndex("_id")),
                    mCursor.getString(mCursor.getColumnIndex("name")),
                    mCursor.getString(mCursor.getColumnIndex("money")),
                    mCursor.getString(mCursor.getColumnIndex("content")),
                    mCursor.getString(mCursor.getColumnIndex("day"))
            );

            mInfoArray.add(mInfoClass);
        }

        mCursor.close();
    }

    /*
     * Layout
     */
    private EditText[] mEditTexts;

    private void setLayout(){
        mEditTexts = new EditText[]{
                (EditText)findViewById(R.id.et_name),
                (EditText)findViewById(R.id.et_money),
                (EditText)findViewById(R.id.et_content),
                (EditText)findViewById(R.id.et_day)
        };
    }
}
