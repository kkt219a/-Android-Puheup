package ph.puheup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by 김규태 on 2016-12-02.
 */

public class quetion extends Activity {
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_quetion);

        backButton = (Button) findViewById(R.id.button10);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();}
        });


    }
}
