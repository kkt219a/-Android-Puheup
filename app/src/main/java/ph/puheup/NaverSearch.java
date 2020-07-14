package ph.puheup;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.nhn.android.maps.maplib.NGeoPoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import static ph.puheup.Gps01.mmylocation;
import static ph.puheup.Gps01.real_search;
import static ph.puheup.Gps01.real_x;
import static ph.puheup.Gps01.real_y;


//네이버 지역검색 시작
public class NaverSearch extends Activity{


    int count =0; // 위,경도바꿀때 배열설정위해
    private static final String LOG_TAG = "NaverSearch";
    public static String top_tab=null;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naversearch);

        new Thread() {
            public void run() {
                callmelocation();
                //위의 검색값들 다 분류하고 정하고 나서!
                for(int item=1;item<11;item++) {
                    if(real_search[item][5]==null)
                        break;
                    findGeoPoint(real_search[item][5]);// real_xy[0] 은 비어있음. 이렇게되면 0은 필요없는내용.
                }

                top_tab= mmylocation+RandomCalc.real;
                finish();
                Intent intent = new Intent(getApplicationContext(),Gps01.class);
                startActivity(intent);

            }
        }.start();

    }

    public static String callmelocation(){
        String good = "";
        String clientId = "Gj0rYUI0Fbr48Lo69qkk";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "vBWaisv9Hy";//애플리케이션 클라이언트 시크릿값";
        try {

            String text = URLEncoder.encode(mmylocation+RandomCalc.real, "UTF-8"); // 검색어 + 한글지원
            Log.e(LOG_TAG, mmylocation+RandomCalc.real+" 검색 과정에서 오류 발생");
            String apiURL = "https://openapi.naver.com/v1/search/local.xml?query="+ text; // 완성된 url. 저렇게 검색할거임
            URL url = new URL(apiURL); // url 생성 // 새로운 url 생성, api를 담을거임
            HttpURLConnection con = (HttpURLConnection)url.openConnection(); // 네이버로 쿼리요청, 위의 URL이 완전히 들어감
            BufferedReader br; // 버퍼 , 얘네가 문자열을 읽어들일거임.

            con.setRequestProperty("User-Agent", "curl/7.43.0");
            con.setRequestProperty("Content-Type", "application/xml");
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId); // 아이디 설정
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret); // 시크릿 설정

            String getLine;
            int responseCode = con.getResponseCode(); // 검색 반응을 여기로 넎음

            if(responseCode==200) // 200떠야 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                Log.e(LOG_TAG,br+"******************responseCode 200이아님*******************");
            }
            while ((getLine = br.readLine()) != null)
                good+= getLine + "\n";

            String[] item = good.split("<item>");
            String[] split;

            Log.e(LOG_TAG, mmylocation+RandomCalc.real+" 잘왔나?");
            for(int i=1; i<item.length;i++){
                split = item[i].split("</");
                for(int k=0;k<split.length;k++){
                    real_search[i][k] = split[k];
                    real_search[i][k]= real_search[i][k].replaceAll(">", "");
                    real_search[i][k]= real_search[i][k].replaceAll("<", "");
                    real_search[i][k]= real_search[i][k].replaceAll("title", "");
                    real_search[i][k]= real_search[i][k].replaceAll("link", "");
                    real_search[i][k]= real_search[i][k].replaceAll("category", "");
                    real_search[i][k]= real_search[i][k].replaceAll("description", "");
                    real_search[i][k]= real_search[i][k].replaceAll("telephone", "");
                    real_search[i][k]= real_search[i][k].replaceAll("address", "");
                    real_search[i][k]= real_search[i][k].replaceAll("roadAddress", "");
                    real_search[i][k]= real_search[i][k].replaceAll("mapx", "");
                    real_search[i][k]= real_search[i][k].replaceAll("mapy", "");
                    Log.e(LOG_TAG,real_search[i][k]+"******************잘돌아가니*******************"+i+","+k);
                }
            }
            //real[item.length][split.length][10]
            //item.length - 찾는 가게 숫자, split.length >> 0:가게이름 1:가게홈페이지 2:카테고리 3:업체,기관설명 4:전화번호 5:주소 6:도로명주소 7:mapx 8:mapy


            br.close();

        } catch (Exception e){
            Log.e(LOG_TAG,e+" 검색 과정에서 오류 발생");}

        return good;
    }


    /**
     * 주소로부터 위치정보 취득
     * @param address 주소
     */
    private  NGeoPoint findGeoPoint(String address) {
        Geocoder geocoder = new Geocoder(getApplicationContext());
        Address addr;
        NGeoPoint location = null;
        try {
            List<Address> listAddress = geocoder.getFromLocationName(address, 1);
            if (listAddress.size() > 0) { // 주소값이 존재 하면
                addr = listAddress.get(0); // Address형태로
                count++;
                real_x[count] = (addr.getLatitude());
                real_y[count] = (addr.getLongitude());
                location = new NGeoPoint(real_x[count], real_y[count]);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG,e+"주소>>위,경도 변환중 오류발생");
        }
        return location;
    }

}