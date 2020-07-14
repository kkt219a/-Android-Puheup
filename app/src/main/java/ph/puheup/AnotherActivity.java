package ph.puheup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 새로운 액티비티
 *
 * @author Mike
 */
public class AnotherActivity extends Activity {

    final String TAG = "AnotherActivity";
    Button backButton;
    Button location;
    TextView textView;
    static String string;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.another1);

        backButton = (Button) findViewById(R.id.button06);
        location = (Button) findViewById(R.id.button07);
        textView = (TextView) findViewById(R.id.textView4);

        textView.setText(RandomCalc.real);

        // 버튼을 눌렀을 때 메인 액티비티로 돌아갑니다.
        backButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                backButton.setBackgroundResource(R.drawable.location_back_on);
                finish();
            }
        });
        location.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                location.setBackgroundResource(R.drawable.location_search_on);
                finish();
                Intent intent = new Intent(getApplicationContext(), Gps01.class);
                startActivity(intent);
            }
        });





    }

}
