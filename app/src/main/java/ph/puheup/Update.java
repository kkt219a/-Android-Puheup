package ph.puheup;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 새로운 액티비티

 */
public class Update extends Activity {

    final String TAG = "Update";
    Button backButton;
    Button update;
    TextView textView;
    static String string_ud;
    String url ="https://play.google.com/store/apps/details?id=ph.puheup";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.update);

        backButton = (Button) findViewById(R.id.button_c);
        update = (Button) findViewById(R.id.button_ud);
        textView = (TextView) findViewById(R.id.textView_v);

        textView.setText(string_ud);

        // 버튼을 눌렀을 때 메인 액티비티로 돌아갑니다.
        backButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                backButton.setBackgroundResource(R.drawable.location_back_on);
                finish();
            }
        });
        update.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                update.setBackgroundResource(R.drawable.location_search_on);
                finish();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });





    }

}
