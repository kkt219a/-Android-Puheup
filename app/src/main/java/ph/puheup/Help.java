package ph.puheup;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by 김규태 on 2016-11-30.
 */

public class Help extends Activity {
    @Override
    public void onStart() {
        super.onStart();
        setContentView(R.layout.help);


        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0) {
                finish();
            }
        });
    }
    @Override
    public void onStop(){
        super.onStop();
    }
}
