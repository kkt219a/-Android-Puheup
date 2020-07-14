package ph.puheup;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ph.puheup.db.TestDataBaseActivity;

import static ph.puheup.Update.string_ud;

/*
        * Base Programming : Android Studio(JAVA)
        * Team Name : 푸흡(Pu heup)
        * App Name : 푸흡(Puheup)
        * Developer : KueTaeKim
        * Stage : remain indentify
        *  explain : 제일 처음뜨는 레이아웃 , 탭레이아웃을 정의하고 , 각 탭에서의 버튼들을 처리하는 역할을 한다.
*/

public class MainActivity extends AppCompatActivity {

    public String LOG="MainActivity";
    //탭바//
    private TabLayout tabLayout;
    //탭바//

    String deviceVersion;
    String storeVersion;

    String url ="https://play.google.com/store/apps/details?id=ph.puheup";

    private BackgroundThread mBackgroundThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //oncreate 함수선언, super로 상속하고 activity_main을 실행하겠다.

        // 탭바 관련함수 시작부
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // id.toolbar는 activity_main에 있는 상단바.
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);           // 상단바에 뒤로가기 버튼


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        //activity_main >> viewpager 아이디 참조.
        //뷰페이저를 실행하겠다.tabs의 수를 적절한 프레그먼트와 tab 이름을 설정함으로부터 정의한다.


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager); //tabLayout에서 viewpager를 할당한다.
        setupTabIcons();
        //tabLayout은 activity_main >> tabs 아이디를 참조하겠다.
        //탭 레이아웃에대해 뷰페이저실행
        //탭아이콘함수 실행, 밑에부터시작

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.decision_select);
                        tabLayout.getTabAt(1).setIcon(R.drawable.money_notselect);
                        tabLayout.getTabAt(2).setIcon(R.drawable.setting_notselect);
                        break;
                    case 1:
                        tabLayout.getTabAt(0).setIcon(R.drawable.decision_notselect);
                        tabLayout.getTabAt(1).setIcon(R.drawable.money_select);
                        tabLayout.getTabAt(2).setIcon(R.drawable.setting_notselect);
                        break;
                    case 2:
                        tabLayout.getTabAt(0).setIcon(R.drawable.decision_notselect);
                        tabLayout.getTabAt(1).setIcon(R.drawable.money_notselect);
                        tabLayout.getTabAt(2).setIcon(R.drawable.setting_select);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //////////////////////////////////////////////////////////////////////TAB관련 함수끝 //////////////////////////////////////////////////////

        //액티비티에서는 체크가 가능하다
        oudigaPreference pref = new oudigaPreference(this);
        //앱실행이 처음인지 체크해보자
        if(oudigaPreference.start.equals("start_key")){ //oudigaPreference.start의 value는 null이니까 최초실행
            pref.put(oudigaPreference.start, "true"); // 다음에 실행했을 때 처음이 아니다로 가기 위해
            //임시의 값을 oudigaPreference.start의 value에  넣어준다
            Intent intent = new Intent(this,Help.class);
            startActivity(intent);
        }
        else{//아니면 아무일도 안생김 나중에여기 손한번 더보기로
        }


        //앱실행이 처음인지 체크해보자 끝



        mBackgroundThread = new BackgroundThread(); // 업데이트 확인, 나중에필요하면쓰기
        mBackgroundThread.start(); // 업데이트확인

    }


    private void setupTabIcons() { // 제일 초기Tab바 선택부분.


        //TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        //R.layout custom_tab.xml을 객체화 시켜서 우선 소스로 가져오겠다. TextView로 우선 만들거임.
        //tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.decision_notselect, 0, 0);
        //아이콘정렬 >> tabOne.setCompoundDrawablesWithIntrinsicBounds(왼쪽,위,오른쪽,아래)
        //tabLayout.getTabAt(1).setCustomView(tabTwo);
        tabLayout.getTabAt(0).setText("결정");
        tabLayout.getTabAt(0).setIcon(R.drawable.decision_select);
        //getTabAt(0)으로저장, 메뉴에 표시하겠다 tabOne을 , tabOne이라는 뷰를 actionBar에 설정

        //TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        //tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.money_notselect, 0, 0);
        //tabLayout.getTabAt(2).setCustomView(tabThree);
        tabLayout.getTabAt(1).setText("돈관리");
        tabLayout.getTabAt(1).setIcon(R.drawable.money_notselect);

        //TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        //tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.setting_notselect, 0, 0);
        //tabLayout.getTabAt(3).setCustomView(tabFour);
        tabLayout.getTabAt(2).setText("설정");
        tabLayout.getTabAt(2).setIcon(R.drawable.setting_notselect);

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //어댑터 클래스의 객체를 생성
        adapter.addFrag(new Tab2());
        adapter.addFrag(new Tab3());
        adapter.addFrag(new Tab4());
        //얘네 함수는 viewpagerAdapter 클래스에 존재
        viewPager.setAdapter(adapter);
        //setAdapter로 넘어가자! 이것을 뷰페이저 안으로 설정 부르는걸 setAdapter로
        }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        // viewpager,이 adapter 클래스는 veiwpager로부터 필요한 프레그먼트를 제공한다.
        private final List<Fragment> mFragmentList = new ArrayList<>();
        //위 arraylist 객체 생성, 새로운 ArrayList를 mFragmentList에 추가

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        //상속문

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
            //viewpage에 보여질 총 페이지 수
            //mFragmentList에 addFrag한것을 position 0,1,2,3 차례로 들고온다(get이 가져오겠다는거)
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
            //size는 4 , 0,1,2,3이렇게 4개이니까.
        }

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
            //one,two,three,four fragment를 이 ArrayList에 차례대로 배열처럼 넣는다 [0],[1],[2],[3]
        }
    }

    //탭함수 여기까지끝

    public class BackgroundThread extends Thread {
        @Override
        public void run() {

            // 패키지 네임 전달
            storeVersion = MarketVersionChecker.getMarketVersion(getPackageName());
            Log.e(LOG,storeVersion);

            // 디바이스 버전 가져옴
            try {
                deviceVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                Log.e(LOG,deviceVersion);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            deviceVersionCheckHandler.sendMessage(deviceVersionCheckHandler.obtainMessage());
            // 핸들러로 메세지 전달
        }
    }

    private final DeviceVersionCheckHandler deviceVersionCheckHandler = new DeviceVersionCheckHandler(this);

    // 핸들러 객체 만들기
    private static class DeviceVersionCheckHandler extends Handler {
        private final WeakReference<MainActivity> mainActivityWeakReference;
        public DeviceVersionCheckHandler(MainActivity mainActivity) {
            mainActivityWeakReference = new WeakReference<MainActivity>(mainActivity);
        }
        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mainActivityWeakReference.get();
            if (activity != null) {
                activity.handleMessage(msg);
                // 핸들메세지로 결과값 전달
            }
        }
    }

    private void handleMessage(Message msg) {
        //핸들러에서 넘어온 값 체크
        if (storeVersion.compareTo(deviceVersion) > 0) {
            string_ud="현재 사용하고계신 버전은"+deviceVersion+"입니다. 더나은 기능을 위해 버전"+storeVersion+"으로 업데이트 하시겠습니까?";
            // 업데이트 필요
            Intent intent = new Intent(this,Update.class);
            startActivity(intent);


        } else { // 버전 같으면 업데이트 불필요
            Log.e(LOG,"업데이트 불필요");
        }
    }



    public void onClick00 (View v) {
        Intent intent = new Intent(getApplicationContext(),Dine.class);
        startActivity(intent);
        //onClick00 버튼 실행시 Dine.java 실행
    }
    public void onClick01 (View v) {
        Intent intent = new Intent(getApplicationContext(),Sidedish.class);
        startActivity(intent);
        //onClick01 버튼 실행시 Sidedish.java 실행
    }
    public void onClick02 (View v) {
        Intent intent = new Intent(getApplicationContext(),Playing.class);
        startActivity(intent);
        //onClick02 버튼 실행시 Playing.java 실행
    }
    public void onClick03 (View v) {
        Intent intent = new Intent(getApplicationContext(),Minigame.class);
        startActivity(intent);
        //onClick03 버튼 실행시 Minigame.java 실행
    }
    public void onClick04 (View v) {
        Intent intent = new Intent(getApplicationContext(),TestDataBaseActivity.class);
        startActivity(intent);
        //onClick03 버튼 실행시 Minigame.java 실행
    }

    public void onClick05 (View v) {
        Intent intent = new Intent(getApplicationContext(),quetion.class);
        startActivity(intent);
        //onClick03 버튼 실행시 Minigame.java 실행
    }

}


