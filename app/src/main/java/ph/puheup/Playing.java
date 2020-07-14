package ph.puheup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.ToggleButton;

/*
        * Base Programming : Android Studio(JAVA)
        * Team Name : 푸흡(Pu heup)
        * App Name : 푸흡(Puheup)
        * Developer : KueTaeKim
        * Stage : remain identify
        *  explain : 놀거리를 나열하고 설정완료시 MiniAct2와 연결된다.
*/

public class Playing extends AppCompatActivity {

    String[] playing = {"당구","술","아이쇼핑","클럽","감주","스티커사진","탁구", "포켓볼","피시방", "노래방", "코인노래방", "오락실", "스크린야구", "스크린골프","스크린사격","볼링", "인형뽑기", "놀이공원", "마사지", "영화", "연극","산책", "방탈출", "카페","관광지", "DVD방","룸 카페", "보드게임","모텔" ,"렌트카",
            "만화카페", "실내낚시", "트릭아트"
    };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playing);
        GridView gridview = (GridView) findViewById(R.id.gridview2);
        MyAdapter adapter = new MyAdapter(getApplicationContext());
        gridview.setAdapter(adapter);
        gridview.setNumColumns(2); // gridview의 칼럼( 한줄에몇개씩)
    }

    //설정완료버튼 누르면 .
    public void settingClick1(View v){
        if(RandomCalc.count_Sideplay==0){ //count = 0 이면 엄청난 오류 발생하기에 밑에꺼 실행안하는거.
            Toast.makeText(this, "메뉴를 선택해 주세요", Toast.LENGTH_LONG).show();
        }
        else {
            RandomCalc.Sideplay=1;
            finish();
            Intent intent = new Intent(this, RandomCalc.class); //MiniAct1.class로 이동할꺼
            startActivity(intent);
        }
    }
    //설정 완료버튼 누르면

    //체크했을때 그 배열값들 저장할 함수
    public void wantarray(int a){
        RandomCalc.copySideplay[a]=playing[a]; //MiniAct1 클래스의 copyDine은 다 null값이니 체크하면 여기 DIne클래스의 diner02값들이 들어감
        RandomCalc.count_Sideplay++; // 몇개 들어오는지 세기위해서 count 올려줌
    }
    //체크했을때 그배열값들 저장할 함수

    //체크 해제 했을때 배열값 해제 하고 count 줄이기
    public void nowantarray(int a){
        RandomCalc.copySideplay[a]=null; // MiniAct1 클래스의 copyDine값을 null로초기화
        RandomCalc.count_Sideplay--; // 카운트 다시줄여주고
    }
    //취소!


    public class MyAdapter extends BaseAdapter {

        private Context mContext;
        private Integer[] mThumbids_ = { //체크 했을시 아이콘, 갯수 확실하지않아서 대량으로 만들어둠
                R.drawable.icon__, R.drawable.icon__,R.drawable.icon__, R.drawable.icon__,R.drawable.icon__, R.drawable.icon__,
                R.drawable.icon__, R.drawable.icon__,R.drawable.icon__, R.drawable.icon__,R.drawable.icon__, R.drawable.icon__,
                R.drawable.icon__, R.drawable.icon__,R.drawable.icon__, R.drawable.icon__,R.drawable.icon__, R.drawable.icon__,
                R.drawable.icon__, R.drawable.icon__,R.drawable.icon__, R.drawable.icon__,R.drawable.icon__, R.drawable.icon__,
                R.drawable.icon__, R.drawable.icon__,R.drawable.icon__, R.drawable.icon__,R.drawable.icon__, R.drawable.icon__,
                R.drawable.icon__, R.drawable.icon__,R.drawable.icon__, R.drawable.icon__,R.drawable.icon__, R.drawable.icon__,
                R.drawable.icon__, R.drawable.icon__,R.drawable.icon__, R.drawable.icon__,R.drawable.icon__, R.drawable.icon__,
                R.drawable.icon__, R.drawable.icon__,R.drawable.icon__, R.drawable.icon__,R.drawable.icon__, R.drawable.icon__,};
        private Integer[] mThumbIds = { //체크해제 됬을시 아이콘, 갯수 확실하지않아서 대량으로 만들어둠
                R.drawable.icon___, R.drawable.icon___,R.drawable.icon___, R.drawable.icon___,R.drawable.icon___, R.drawable.icon___,
                R.drawable.icon___, R.drawable.icon___,R.drawable.icon___, R.drawable.icon___,R.drawable.icon___, R.drawable.icon___,
                R.drawable.icon___, R.drawable.icon___,R.drawable.icon___, R.drawable.icon___,R.drawable.icon___, R.drawable.icon___,
                R.drawable.icon___, R.drawable.icon___,R.drawable.icon___, R.drawable.icon___,R.drawable.icon___, R.drawable.icon___,
                R.drawable.icon___, R.drawable.icon___,R.drawable.icon___, R.drawable.icon___,R.drawable.icon___, R.drawable.icon___,
                R.drawable.icon___, R.drawable.icon___,R.drawable.icon___, R.drawable.icon___,R.drawable.icon___, R.drawable.icon___,
                R.drawable.icon___, R.drawable.icon___,R.drawable.icon___, R.drawable.icon___,R.drawable.icon___, R.drawable.icon___,
                R.drawable.icon___, R.drawable.icon___,R.drawable.icon___, R.drawable.icon___,R.drawable.icon___, R.drawable.icon___,
        };

        public MyAdapter(Context c) {
            mContext = c; // 폴더 우클릭하면 뜨는 내용(열기,속성,복사 등)은 다똑같은데 상세내용(용량)은 다 다르다.
        }

        @Override
        public int getCount() {
            return playing.length; // 전체  리스트가 총 몇개인지! 리턴값에 의해 결정된다
        }

        @Override
        public Object getItem(int arg0) {
            return mThumbIds[arg0]; //전달되는 포지션에 해당하는 요소를 리턴시켜주도록 구현
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View grid;

            if (convertView == null) { // convertView? 해당리스트의 항목! 비어있으면 만들어줌.
                grid = new View(mContext);
                LayoutInflater inflater = getLayoutInflater();
                grid = inflater.inflate(R.layout.custom_mygrid, parent, false);
            } else {
                grid = (View) convertView;
            }
            final ToggleButton button = (ToggleButton)grid.findViewById(R.id.togglebutton);
            button.setBackgroundResource(mThumbIds[position]);
            button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            String strColor = "#FFFFFF";
            button.setTextColor(Color.parseColor(strColor));
            button.setText(playing[position]);

            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { // 토글버튼 체크리스너
                    if (isChecked == true){ // 만약 체크됫으면
                        button.setBackgroundResource(mThumbids_[position]); // 체크된 이미지로 바꾸고
                        button.setTextOn(playing[position]); // 이름은 똑같이
                        wantarray(position); // 생성해뒀던 함수 실행
                    }
                    else {
                        button.setBackgroundResource(mThumbIds[position]); // 체크안된 이미지로 바꾸고
                        button.setTextOff(playing[position]); // 이름은 똑같이
                        nowantarray(position); // 생성해뒀던 함수 실행
                    }

                }
            });


            return grid;
        }
    }

}
