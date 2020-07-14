package ph.puheup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Random;

import static ph.puheup.Dine.dineactivity;

/*
        * Base Programming : Android Studio(JAVA)
        * Team Name : 푸흡(Pu heup)
        * App Name : 푸흡(Puheup)
        * Developer : KueTaeKim
        * Stage : remain indentify
        *  explain : Dine.java와 연관된다. 이 클래스의 제일 직접적인목적은 랜덤값을 추출하기위한 클래스 .
*/

public class RandomCalc extends Activity {
    final String TAG = "RandomCalc";

    public static int Dine=0,Sideplay=0; // Dine,나머지 구분위해
    //Dine에서만
    public static String[][] copyDine = new String[9][50]; // dine에서 복사해온걸 minidine으로 옮김 , 일단 두번쨰배열은 50개로 설정
    public String[][] miniDine = new String[1][100]; // 뒤에 저 100은 음식 총개수가 100개넘으면 알아서 수정
    public static int count_Dine = 0; // count는 dine.java에서 몇개오는지 수가 알아서 변환됨
    //Dine에서만

    //Dine 이외(스피너x)
    public static String[] copySideplay = new String[200];
    public String[] miniSideplay = new String[200]; // 뒤에 저 100은 음식 총개수가 100개넘으면 알아서 수정
    public static int count_Sideplay = 0; // count는 dine.java에서 몇개오는지 수가 알아서 변환됨
    //Dine 이외(스피너x)

    //공통
    Random r1 = new Random(); // 후보 음식들 랜덤으로 돌리기
    Random r2 = new Random(); // 일치시킬 숫자
    public static String real; //뽑힌 글자!
    int f = 0, d; //d는 랜덤에 맞출숫자 , f는 숫자 세기용
    public int[] c = new int[200]; // 랜덤 배열 설정할 숫자 , 최대숫자만큼 고쳐야됨
    //공통

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Dine DineAct = (Dine)dineactivity;

        //스피너있는 dine에서 계산
        if(Dine==1) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 50; j++) { // 한 이차원배열당 50넘을거같으면 이거수정
                    if (copyDine[i][j] != null) {
                        miniDine[0][f] = copyDine[i][j]; // copyDine에있는걸 miniDine으로 옮김
                        f++;
                    }//if문 닫는창
                } // j for문 닫는창
            } // i for문 닫는창

            for (int i = 0; i < f; i++) {
                c[i] = i+1; // 단어 갯수만큼 랜덤숫자 넣기
                /*
                for (int j = 0; j < i; j++) {
                    if (c[i] == c[j])
                        i--; // 숫자두개 겹치면 한개뒤로 밀어서 다시비교
                }
                */
            }


            d = r2.nextInt(count_Dine) + 1; // 비교할값을 랜덤값지정

            for (int i = 0; i < f; i++) {
                if (c[i] == d) // 비교할 값이랑 같은것을
                    real = miniDine[0][i]; // 나타낼 스트링으로 바꿔줌
            }

            copyDine = new String[9][50];
            miniDine = new String[1][100];
            count_Dine = 0;
            Dine=0;
        }

        //외에것들 계산
        else if(Sideplay==1){
            for (int j = 0; j < 100; j++) { // 한 이차원배열당 50넘을거같으면 이거수정
                if (copySideplay[j] != null) {
                    miniSideplay[f] = copySideplay[j]; // copyDine에있는걸 miniDine으로 옮김
                    f++;
                }//if문 닫는창
            } // j for문 닫는창

            for (int i = 0; i < f; i++) {
                c[i] = i+1;
                /*
                for (int j = 0; j < i; j++) {
                    Log.e(TAG,c[i]+" ");
                    if (c[i] == c[j])
                        i--; // 숫자두개 겹치면 한개뒤로 밀어서 다시비교
                }
                */
            }

            d = r2.nextInt(count_Sideplay) + 1; // 비교할값을 랜덤값지정

            for (int i = 0; i < f; i++) {
                if (c[i] == d) // 비교할 값이랑 같은것을
                    real = miniSideplay[i]; // 나타낼 스트링으로 바꿔줌
            }
            copySideplay = new String[100];
            miniSideplay = new String[100]; // 뒤에 저 100은 음식 총개수가 100개넘으면 알아서 수정
            count_Sideplay = 0; // count는 dine.java에서 몇개오는지 수가 알아서 변환됨
            Sideplay = 0;
        }

        this.finish();
        Log.e(TAG,real+" 선택된놈");
        Intent intent = new Intent(getBaseContext(), AnotherActivity.class);
        startActivity(intent);

    }
}

