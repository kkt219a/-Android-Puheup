package ph.puheup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

/*
        * Base Programming : Android Studio(JAVA)
        * Team Name : 푸흡(Pu heup)
        * App Name : 푸흡(Puheup)
        * Developer : KueTaeKim
        * Stage : remain indentify
        * explain : 음식들을 스피너별로 정의하고 선택을 완료하고 설정완료를 누르면 MiniAct1 으로 넘어간다.
*/

public class Dine extends Activity{

    public static Activity dineactivity;

    //public MiniAct1 miniact1;
    //컴플리케이션 쓸거면 활성화

    String[] diner01 = {"전체","한식","일식","서양식","중식","동양식","분식","디저트","패스트푸드"};
    //스피너

    //버튼뷰에 연관시킬것들 //
    static String[][] diner02 = {
            {"한식","일식","서양식","중식","동양식","분식","디저트","패스트푸드"},
            {"비빔밥","낙지찜","소갈비","청국장","곰국","칼국수","오므라이스","비빔국수","찜닭","감자탕","죽","떡국","미역국","삼계탕","국밥","편육","육회","생선","김치전","파전","전골", "제육볶음", "김치찌개","순두부찌개","볶음밥","된장찌개","수육","불고기","곱창","막창","삼겹살","치킨","닭발","갈비찜","밀면","냉면","국밥"},
            {"회", "초밥","우동","낫토","라멘","연어","타코야키","치킨","가라아게","벤또","가츠동","해물야끼우동","나가사키짬뽕","회덮밥", "고로케", "알밥", "닭꼬치","야끼소바", "오니기리", "규동", "돈까스", "가끼아게동", "덴동", "사케동", "차슈", "야끼우동", "스키야키", "유부초밥", "치킨데리야끼"},
            {"스테이크","파스타","스파게티","샌드위치","핫도그","리조또","피자","햄버거","빠네","부리또","샐러드","봉골레","스프","필라프","브리또", "퀘사디아","찹스테이크", "그라탕","낫쵸","콘버터", "바베큐","오믈렛","커틀릿","크로켓","타코","까르보나라","케비어","푸아그라","버팔로 윙","새우구이"  },
            {"짜장면","탕수육","짬뽕","깐풍기","팔보채","마파두부","취두부","양장피","간소새우","라조기","라조육","비파두부","짜춘권","울면"},
            {"카레", "케밥" , "누들", "톰얌꿍","탄두리치킨","쌀국수","딤섬","고추잡채","꽃빵"},
            {"김밥","순대","떡볶이","어묵","라볶이","쫄면","라면","튀김","분식","튀김","떡꼬지","닭꼬지","족발","편육","보쌈"},
            {"케익","아이스크림","빵","커피","스무디","버블티","팥빙수","마카롱","타르트","브라우니","푸딩","머핀","퐁듀","요거트","와플","초콜릿","과일","젤라또","츄러스","마카롱","핫도그","패스츄리","후레첼","티라미수","차","쿠키슈","에이드"},
            {"삼각김밥","햄버거","샌드위치","라면","주먹밥","떡볶이","컵밥","컵라면","푸딩","닭강정","핫바","냉동만두","순대","족발","보쌈","오뎅탕","볶음우동","편육"}
    };
    // 전체[0][] 한식[1][] 일식[2][] 서양식[3][] 중식[4][] 동양식[5][] 분식[6][] 디저트[7][] 패스트푸드[8][]
    //버튼뷰에 연관시킬것들 //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dine);

        dineactivity = Dine.this;

        GridView gridview = (GridView) findViewById(R.id.gridview); // dine.xml의  grid뷰 참조
        MyAdapter adapter = new MyAdapter(getApplicationContext()); //MyAdapter 클래스에 쓸 새로운 이름생성
        adapter.addCount(0); // adapter 내부의 addCount함수에 0을 집어넣고
        gridview.setAdapter(adapter); // adapter실행
        gridview.setNumColumns(2); // gridview의 칼럼( 한줄에몇개씩)
        //Button button = (Button)findViewById(R.id.button);




        ///스피너 관련함수 시작 //
        Spinner spinner = (Spinner) findViewById(R.id.spinner);// 스피너 객체 참조
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, diner01); // adapter1 값 넣기
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //어댑터 객체 생성
        spinner.setAdapter(adapter1); //어댑터 설정

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // 스피너의 리스너, 선택했을때 뭘 어떻게 뜨게할것인가.
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) { // position 위치마다 다르게 뜨게하기위해서
                switch(position){
                    case 0: // 0이 한식이니 한식눌렀을 때 어떻게 뜨게할건지
                        GridView gridview00 = (GridView) findViewById(R.id.gridview); //gridview 다시참조해서 00으로 생성
                        MyAdapter adapter00 = new MyAdapter(getApplicationContext()); // Myadapter 클래스의 이름지정
                        adapter00.addCount(0); // MyAdapter의 함수 addCount에 0값넣어줌
                        gridview00.setAdapter(adapter00); //설정한거실행 밑에꺼도 다 똑같은원리
                        break;
                    case 1:
                        GridView gridview01 = (GridView) findViewById(R.id.gridview);
                        MyAdapter adapter01 = new MyAdapter(getApplicationContext());
                        adapter01.addCount(1);
                        gridview01.setAdapter(adapter01);
                        break;
                    case 2:
                        GridView gridview02 = (GridView) findViewById(R.id.gridview);
                        MyAdapter adapter02 = new MyAdapter(getApplicationContext());
                        adapter02.addCount(2);
                        gridview02.setAdapter(adapter02);
                        break;
                    case 3:
                        GridView gridview03 = (GridView) findViewById(R.id.gridview);
                        MyAdapter adapter03 = new MyAdapter(getApplicationContext());
                        adapter03.addCount(3);
                        gridview03.setAdapter(adapter03);
                        break;
                    case 4:
                        GridView gridview04 = (GridView) findViewById(R.id.gridview);
                        MyAdapter adapter04 = new MyAdapter(getApplicationContext());
                        adapter04.addCount(4);
                        gridview04.setAdapter(adapter04);
                        break;
                    case 5:
                        GridView gridview05 = (GridView) findViewById(R.id.gridview);
                        MyAdapter adapter05 = new MyAdapter(getApplicationContext());
                        adapter05.addCount(5);
                        gridview05.setAdapter(adapter05);
                        break;
                    case 6:
                        GridView gridview06 = (GridView) findViewById(R.id.gridview);
                        MyAdapter adapter06 = new MyAdapter(getApplicationContext());
                        adapter06.addCount(6);
                        gridview06.setAdapter(adapter06);
                        break;
                    case 7:
                        GridView gridview07 = (GridView) findViewById(R.id.gridview);
                        MyAdapter adapter07 = new MyAdapter(getApplicationContext());
                        adapter07.addCount(7);
                        gridview07.setAdapter(adapter07);
                        break;
                    case 8:
                        GridView gridview08 = (GridView) findViewById(R.id.gridview);
                        MyAdapter adapter08 = new MyAdapter(getApplicationContext());
                        adapter08.addCount(8);
                        gridview08.setAdapter(adapter08);
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {//아무것도 선택되지 않았을때 , 지금 필요없음
            }
        });
    }
    //스피너 종료 //

    //설정완료버튼 누르면 .
    public void settingClick(View v){
        if(RandomCalc.count_Dine==0){ //count = 0 이면 엄청난 오류 발생하기에 밑에꺼 실행안하는거.
            Toast.makeText(this, "메뉴를 선택해 주세요", Toast.LENGTH_LONG).show();
        }

        else {
            RandomCalc.Dine=1;
            finish();
            Intent intent = new Intent(this, RandomCalc.class); //MiniAct1.class로 이동할꺼
            startActivity(intent);
        }
    }
    //설정 완료버튼 누르면

    //체크했을때 그 배열값들 저장할 함수
    public void wantarray(int a, int b){
        RandomCalc.copyDine[a][b]=diner02[a][b]; //MiniAct1 클래스의 copyDine은 다 null값이니 체크하면 여기 DIne클래스의 diner02값들이 들어감
        RandomCalc.count_Dine++; // 몇개 들어오는지 세기위해서 count 올려줌
    }
    //체크했을때 그배열값들 저장할 함수

    //체크 해제 했을때 배열값 해제 하고 count 줄이기
    public void nowantarray(int a, int b){
        RandomCalc.copyDine[a][b]=null; // MiniAct1 클래스의 copyDine값을 null로초기화
        RandomCalc.count_Dine--; // 카운트 다시줄여주고
    }
    //취소!

    public class MyAdapter extends BaseAdapter{

        private int Count; // 배열 앞번호의 구분을위해
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
            mContext = c; // 폴더 우클릭하면 뜨는 내용(열기,속성,복사 등)은 다똑같은데 상세내용(용량)은 다 다르다. 이걸로이해하면 쉬움
        }
        public int addCount(int i) {
            for(int j=0;j<i;j++) {
                Count++; // 배열 앞번호 구분위해서 생성한 함수
            }
            return Count;
        }
        @Override
        public int getCount()
        {
            return diner02[Count].length; // 전체  리스트가 총 몇개인지! 리턴값에 의해 결정된다
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

            if(convertView==null){ // convertView? 해당리스트의 항목, 비어있으면 만들어줌.
                grid = new View(mContext);
                LayoutInflater inflater=getLayoutInflater(); // 레이아웃 객체화
                grid=inflater.inflate(R.layout.custom_mygrid, parent, false); // 커스톰 마이그리드xml을
            }else{
                grid = (View)convertView;
            }

            final ToggleButton button = (ToggleButton)grid.findViewById(R.id.togglebutton); // togglebutton 참조해둠
            button.setText(diner02[Count][position]); // text는 count 앞번호 정해 뒀으니 뒤에 포지션 값마다 달라짐 , 포지션은 내용안에몇개있는지
            button.setBackgroundResource(mThumbIds[position]); // 아이콘은 스트링 두번째 배열의 숫자만큼
            button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25); // 텍스트 크기
            String strColor = "#FFFFFF"; // 글자 색깔을 스트링으로 우선넣어두고
            button.setTextColor(Color.parseColor(strColor)); // 이걸로 활성화

            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { // 토글버튼 체크리스너
                    if (isChecked == true){ // 만약 체크됫으면
                        button.setBackgroundResource(mThumbids_[position]); // 체크된 이미지로 바꾸고
                        button.setTextOn(diner02[Count][position]); // 이름은 똑같이
                        wantarray(Count,position); // 생성해뒀던 함수 실행
                    }
                    else {
                        button.setBackgroundResource(mThumbIds[position]); // 체크안된 이미지로 바꾸고
                        button.setTextOff(diner02[Count][position]); // 이름은 똑같이
                        nowantarray(Count,position); // 생성해뒀던 함수 실행
                    }

                }
            });

            return grid;
        }
    }


}




