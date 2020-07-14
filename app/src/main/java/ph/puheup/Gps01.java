package ph.puheup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.nmapmodel.NMapPlacemark;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapCalloutCustomOverlay;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static ph.puheup.NaverSearch.top_tab;
import static ph.puheup.R.layout.gps01;

public class Gps01 extends NMapActivity{

    private static final String LOG_TAG = "Gps01"; // 오류 찾기위해서
    private static final String CLIENT_ID = "Gj0rYUI0Fbr48Lo69qkk"; // CLIENT_ID 설정

    //지도 관련된것 지정
    //지도 이미지 외에도 지도 위에 표시되는 오버레이 객체를 포함.내부적으로 터치 및 키보드 이벤트를 처리, 오버레이 객체에도 이벤트가 전달됩니다.
    private NMapView mMapView;
    //지도의 상태를 변경하고 컨트롤한다.지도 중심, 축척 레벨 변경, 지도 확대, 축소, 패닝 등 수행.
    private NMapController mMapController;
    //맵뷰를 회전시키기 위한 클래스!
    private MapContainerView mMapContainerView;
    //단말기의 현재 위치 탐색 기능, 내부적으로 시스템에서 제공하는 GPS및 네트워크를 모두 사용하여 현재 위치를 탐색.
    private NMapLocationManager mMapLocationManager;
    //단말기의 나침반 기능을 사용하기 위하여.
    private NMapCompassManager mMapCompassManager;
    //지도 위에 현재 위치를 표시하는 오버레이 클래스이며 NMapOverlay 클래스를 상속
    private NMapMyLocationOverlay mMyLocationOverlay;
    //지도 위에 표시되는 오버레이 객체를 관리
    private NMapOverlayManager mOverlayManager;
    //이건 오버레이 아이템의 마커를 표시하기 위한 객체를 반환한다.
    private NMapViewerResourceProvider mMapViewerResourceProvider;
    //지도 관련된것 지정

    //gps01.xml을 객체화 시키고 참조하기 위한것들.
    public RelativeLayout gps01_Layout;
    private Button button = null;
    private TextView textView_top = null;
    private TextView textView_bottom = null;
    private Button button2 = null;
    //gps01.xml을 객체화 시키고 참조하기 위한것들.

    //기타 사용하는 변수
    private static final boolean DEBUG = false; // 디버그 , 실패나타내기위해 임시적으로 기호화
    public static String[][] real_search=new String[11][14]; // 이 클래스가 켜질쯤에는 값들이다생성되있음(검색 항목들에대한 정보)
    public static double [] real_x=new double[11];// 이클래스가 켜질쯤에는 값들이 다 생성되어있음 (검색항목들에대한 위,경도 값)
    public static double [] real_y=new double[11];
    double a,b; // 내 위치 위도 경도
    public static String mmylocation=null; // 내위치 시구동까지
    public int buttoncount=1; //카운트 초기값 1 , 현재위치 버튼누를때마다 달라지는 경우를 위해
    NMapPOIdata poiData;
    //기타 사용하는 변수
    public pleaselocation gps;
    public NGeoPoint ngeopoint;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        poiData =null;
        real_y=new double[11];
        real_x=new double[11];
        real_search=new String[11][14];
        top_tab=null;
        this.finish();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        this.finish();
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        //gps01.xml을 객체화
        gps01_Layout = (RelativeLayout) LayoutInflater.from(this).inflate(gps01, null);


        // 제일 기초 맵뷰생성
        mMapView = new NMapView(this);
        // 부모 뷰인 mapcontainerview 생성 , 역할은 나침반기능으로서 맵뷰를 회전시키는 역할을함
        mMapContainerView = new MapContainerView(this);
        //이때 mMapView는 자식 뷰라서 부모뷰가 흡수
        mMapContainerView.addView(mMapView);
        //내마음대로 맵뷰위에 항목들을 집어넣기 위해서 객체화시킨 gps01에 mapcontainerview 를흡수시킴
        gps01_Layout.addView(mMapContainerView);
        // 밑에껄 실행하면 풀화면인 gps01.xml 제일위에 풀화면 맵뷰가뜨기때문에 원래의 gps.1.xml의 항목들은 다가려짐
        setContentView(gps01_Layout);
        //gps01에 있는 이것들을 우선참조
        button = (Button) findViewById(R.id.button4);
        button2 = (Button) findViewById(R.id.button5);
        button.setBackgroundResource(R.drawable.mylocation_on);
        textView_top = (TextView) findViewById(R.id.textview_Top);

        //아래 항목들을 제일 상단으로 끌어온다!
        button.bringToFront();
        textView_top.bringToFront();
        button2.bringToFront();

        //반영이안될수도있기때문에 반영해주기위해 뷰를 다시그리는 역할임, ondraw함수 재호출을위해 view를 초기화
        //setViewInvalidate(textView_bottom, gps01_Layout);

        //키 설정
        mMapView.setClientId(CLIENT_ID);
        mMapView.setLogoImageOffset(0,250);

        if(top_tab!=null){
            textView_top.setText(top_tab);
        }
        else{
            textView_top.setText("푸흡 - 지도");
        }



        //각종 이벤트 설정//
        mMapView.setClickable(true); //클릭가능하게.
        //각종 이벤트 설정//

        mMapController = mMapView.getMapController();
        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);

        mMapLocationManager = new NMapLocationManager(this);
        mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);
        mMapCompassManager = new NMapCompassManager(this);
        mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager, mMapCompassManager);


        Toast.makeText(Gps01.this, "현재위치를 탐색중입니다.", Toast.LENGTH_LONG).show();
        startMyLocation();


        //현재위치 버튼의 리스너
        findViewById(R.id.button4).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        switch (buttoncount) {
                            case 0: // 아무것도 아닌상태 , 이때 누르면 현재위치 나타내고 다음카운트로 넘어감.
                                button.setBackgroundResource(R.drawable.mylocation_on);
                                buttoncount = 1;
                                Toast.makeText(Gps01.this, "현재위치를 탐색중입니다.", Toast.LENGTH_LONG).show();
                                break;
                            case 1: //처음 초기값이 1이라 여기부터시작 , 1인상태는 현재위치만 나타내고 있으므로 한번더누르면 로테이트 뜨고카운트 다음꺼로
                                button.setBackgroundResource(R.drawable.mylocation_on_rotate);
                                buttoncount = 2;
                                break;
                            case 2: // 로테이트상태 , 이때 버튼누르면 꺼지므로 로케이션 오프png 키고 카운트 0으로
                                button.setBackgroundResource(R.drawable.mylocation_off);
                                buttoncount = 0;
                                break;
                        }
                        startMyLocation();
                    }
                }
        );
        findViewById(R.id.button5).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if(mMapLocationManager.isMyLocationFixed()==true) { // 내위치를 구한상태면
                            findAddress(a, b); // 이것과관련해 네이버서치까지 가서 값들을 다계산.
                            mOverlayManager.addOverlay(mMyLocationOverlay); // 내위치 오버레이 표시
                        }
                    }
                }
        );

        //좌표 >> 지도로 불러오는 과정
        super.setMapDataProviderListener(onDataProviderListener);
        mOverlayManager.setOnCalloutOverlayListener(onCalloutOverlayListener);
        testPOIdataOverlay();


    }


    //단말기의 현재 위치 상태 변경 시 호출되는 콜백 인터페이스를 정의.
    private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener = new NMapLocationManager.OnLocationChangeListener() {


        @Override
        public boolean onLocationChanged(NMapLocationManager locationManager, NGeoPoint nGeoPoint) {

            if (mMapController != null) {
                mMapController.animateTo(nGeoPoint);} //지도 중심점을 내 지역으로 변경
                a = nGeoPoint.getLatitude(); // 내위치 위도경도 불러오기 a,b값으로
                b = nGeoPoint.getLongitude();
            return true;
        }

        @Override
        public void onLocationUpdateTimeout(NMapLocationManager locationManager) { //위치 업데이트 시간이 초과되면
            Toast.makeText(Gps01.this, "현재위치를 불러올 수 없습니다.", Toast.LENGTH_LONG).show();
        }
        @Override
        public void onLocationUnavailableArea(NMapLocationManager locationManager, NGeoPoint myLocation) { //위치가 어딘지 알수없다면

            Toast.makeText(Gps01.this, "현재지역은 불러올 수 없는 지역입니다.", Toast.LENGTH_LONG).show();

            stopMyLocation(); //
        }

    }; //끝

    private void stopMyLocation() {
        if (mMyLocationOverlay != null) { // 현재위치를 표시할수 없다면
            mMapLocationManager.disableMyLocation(); // 현재위치 탐색 종료

            if (mMapView.isAutoRotateEnabled()) { // 지도회전기능이 활성화 상태라면
                mMyLocationOverlay.setCompassHeadingVisible(false); // 나침반 각도 표시여부 설정 >> 안하는거로

                mMapCompassManager.disableCompass(); //나침반 모니터링종료

                mMapView.setAutoRotateEnabled(false, false); //지도 회전 기능 활성화 여부를 설정한다. (회전활성화여부 , 회전해제시 애니메이션 여부)

                gps01_Layout.requestLayout(); // 컨테이너뷰를 다시 가져온다.
            }
        }
    }//끝

    //좌표>> 지도불러오는 과정
    private final OnDataProviderListener onDataProviderListener = new OnDataProviderListener() {

        //좌표를 주소를 변환하는 API 호출 시 서버 응답에 대한 콜백 인터페이스이다.
        @Override
        public void onReverseGeocoderResponse(NMapPlacemark placeMark, NMapError errInfo) {

            if (DEBUG) { // 실패시
                Log.i(LOG_TAG, "onReverseGeocoderResponse: placeMark=" + ((placeMark != null) ? placeMark.toString() : null)); // 로그기록
            }

            if (errInfo != null) { // 같은실패
                Log.e(LOG_TAG, "Failed to findPlacemarkAtLocation: error=" + errInfo.toString());
                Toast.makeText(Gps01.this, errInfo.toString(), Toast.LENGTH_LONG).show();
                // 오류 로그를 기록한다.
                return;
            }
        }
    }; //끝

    private void startMyLocation() {


        if (mMyLocationOverlay != null) { // 마이 로케이션이 안비어있으면
            if (!mOverlayManager.hasOverlay(mMyLocationOverlay)) { // 객체의 존재여부 반환
                mOverlayManager.addOverlay(mMyLocationOverlay); // 내위치를 표시하는 오버레이를 추가하겠다.
            }

            if (mMapLocationManager.isMyLocationEnabled()) { //현재위치 탐색중이라면

                if (!mMapView.isAutoRotateEnabled()) { //지도 회전가능상태가 활성화가 아니라면
                    mMyLocationOverlay.setCompassHeadingVisible(true); // 지도회전가능상태를 활성화해주고

                    mMapCompassManager.enableCompass(); //singleton 객체 반환
                    //싱글톤이란? 프로젝트 진행시 어플리케이션 전 영역의 걸쳐 하나의 클래스의 단 하나의 인스턴스만을 생성하는 것을 싱글톤 패턴이라고 한다.

                    mMapView.setAutoRotateEnabled(true, false); // 지도 회전가능을 활성화 , 애니메이션은 없게

                    gps01_Layout.requestLayout(); // 그리고 컨테이너뷰 레이아웃을 가져온다!
                } else {
                    stopMyLocation(); // 아니면 탐색중단.
                }

                mMapView.postInvalidate(); // ondraw 함수 호출을 위해 , 자세한건 나중에

            } else { //현재위치 탐색중이아니면
                boolean isMyLocationEnabled = mMapLocationManager.enableMyLocation(true); // 현재위치 탐색 허용하게
                if (!isMyLocationEnabled) { // 만약안되면
                    Toast.makeText(Gps01.this, "시스템 설정에서 위치 권한을 허용해주세요.", Toast.LENGTH_LONG).show();
                    Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(goToSettings);
                    return;
                }
            }
        }
    }//끝



    //여러 개의 오버레이 아이템을 하나의 오버레이 객체에서 관리하기 위한 오버레이 클래스
    private void testPOIdataOverlay() {

        // Markers for POI item
        int markerId = NMapPOIflagType.PIN;

        // POI data 설정
        poiData = new NMapPOIdata(10, mMapViewerResourceProvider); // 10개 생성
        poiData.beginPOIdata(10); // data 10개 설정시작

        NMapPOIitem item = poiData.addPOIitem(real_y[1], real_x[1], real_search[1][0], markerId, 0); //1번

        for(int i=2;i<11;i++) {
            poiData.addPOIitem(real_y[i], real_x[i], real_search[i][0], markerId, 0);
        }
        item.setRightAccessory(true, NMapPOIflagType.CLICKABLE_ARROW); // 클릭되면 화살표표시 하는거 참조!


        poiData.endPOIdata();

        // create POI data overlay
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);

        // set event listener to the overlay
        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);

        // select an item
        poiDataOverlay.selectPOIitem(0, true);

        // show all POI data
        //poiDataOverlay.showAllPOIdata(0);
    }
///끝끝끝

    private final NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener = new NMapPOIdataOverlay.OnStateChangeListener() {

        @Override
        public void onCalloutClick(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {


            if (DEBUG) {
                Log.i(LOG_TAG, " " + item.getTitle());
            }


            // [[TEMP]] handle a click event of the callout
            Toast.makeText(Gps01.this, " " + item.getTitle(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFocusChanged(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
            if (DEBUG) {
                if (item != null) {
                    Log.i(LOG_TAG, " " + item.toString());
                } else {
                    Log.i(LOG_TAG, " ");
                }
            }
        }
    };
    ///끝

    private final NMapOverlayManager.OnCalloutOverlayListener onCalloutOverlayListener = new NMapOverlayManager.OnCalloutOverlayListener() {

        @Override
        public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay itemOverlay, NMapOverlayItem overlayItem,
                                                         Rect itemBounds) {

            // handle overlapped items
            if (itemOverlay instanceof NMapPOIdataOverlay) {
                NMapPOIdataOverlay poiDataOverlay = (NMapPOIdataOverlay)itemOverlay;

                // check if it is selected by touch event
                if (!poiDataOverlay.isFocusedBySelectItem()) {
                    int countOfOverlappedItems = 1;

                    NMapPOIdata poiData = poiDataOverlay.getPOIdata();
                    for (int i = 0; i < poiData.count(); i++) {
                        NMapPOIitem poiItem = poiData.getPOIitem(i);

                        // skip selected item
                        if (poiItem == overlayItem) {
                            continue;
                        }

                        // check if overlapped or not
                        if (Rect.intersects(poiItem.getBoundsInScreen(), overlayItem.getBoundsInScreen())) {
                            countOfOverlappedItems++;
                        }
                    }

                    if (countOfOverlappedItems > 1) {
                        String text = countOfOverlappedItems + "개의 아이템이 " + overlayItem.getTitle()+ "와 겹쳐있습니다. 지도를 확대해주세요.";
                        Toast.makeText(Gps01.this, text, Toast.LENGTH_LONG).show();
                        return null;
                    }
                }
            }

            // use custom old callout overlay
            if (overlayItem instanceof NMapPOIitem) {
                NMapPOIitem poiItem = (NMapPOIitem)overlayItem;

                if (poiItem.showRightButton()) {
                    return new NMapCalloutCustomOldOverlay(itemOverlay, overlayItem, itemBounds,
                            mMapViewerResourceProvider);
                }
            }

            // use custom callout overlay
            return new NMapCalloutCustomOverlay(itemOverlay, overlayItem, itemBounds, mMapViewerResourceProvider);

            // set basic callout overlay
            //return new NMapCalloutBasicOverlay(itemOverlay, overlayItem, itemBounds);
        }

    };
    //끝


    //좌표를 주소로!
    private String findAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(this, Locale.KOREA);
        List<Address> address;
        try {
            if (geocoder != null) {
                address = geocoder.getFromLocation(lat, lng, 1);
                // 세번째 인수는 최대결과값인데 하나만 리턴받도록 설정했다
                if (address != null && address.size() > 0) {
                    // 주소
                    Address a = address.get(0);
                    finish();
                    mmylocation=a.getAdminArea()+" "+a.getLocality()+" "+a.getThoroughfare()+" "; //mmylocation 이란 스트링에 시,구,동을 입력받음
                    Intent intent = new Intent(getApplicationContext(),NaverSearch.class); // 이거가지고 네이버 서치 키기!
                    startActivity(intent);
                }
            }

        } catch (IOException e) {
            Log.e(LOG_TAG,e+"*****************좌표>>주소 변환중 오류");
        }
        return mmylocation;
    }



    // 나침반효과로 지도 돌리기위하여 설정하는곳, 건드리기XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    public class MapContainerView extends ViewGroup {

        public MapContainerView(Context context) {
            super(context);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            final int width = getWidth();
            final int height = getHeight();
            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                final View view = getChildAt(i);
                final int childWidth = view.getMeasuredWidth();
                final int childHeight = view.getMeasuredHeight();
                final int childLeft = (width - childWidth) / 2;
                final int childTop = (height - childHeight) / 2;
                view.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            }


            if (changed) {
                mOverlayManager.onSizeChanged(width, height);
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int w = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
            int h = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
            int sizeSpecWidth = widthMeasureSpec;
            int sizeSpecHeight = heightMeasureSpec;

            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                final View view = getChildAt(i);

                if (view instanceof NMapView) {
                    if (mMapView.isAutoRotateEnabled()) {
                        int diag = (((int)(Math.sqrt(w * w + h * h)) + 1) / 2 * 2);
                        sizeSpecWidth = MeasureSpec.makeMeasureSpec(diag, MeasureSpec.EXACTLY);
                        sizeSpecHeight = sizeSpecWidth;
                    }
                }

                view.measure(sizeSpecWidth, sizeSpecHeight);
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

/*
    private void setViewInvalidate(View... views) { // invalidate 사용할거면 이거로사용
        for (View v : views) {
            v.invalidate();
        }
    }
*/

}