package ph.puheup;
/*
        * Base Programming : Android Studio(JAVA)
        * Team Name : 푸흡(Pu heup)
        * App Name : 푸흡(Puheup)
        * Developer : KueTaeKim
        * Stage : remain indentify
        * Explain : 탭레이아웃을 xml별로 나누는 역할 , Tab 1,2,3,4 에 맞게 xml을 객체화 시켜서 탭레이아웃에 맞게 xml을 배치한다.
*/
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class Tab3 extends Fragment{

    public Tab3() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tab03, container, false);
        //inflater.inflate >> R.layout.tab03라는 xml를 객체화 시켜서 가져오겠다! 리턴값을보내니
        //객체화된 tab03.xml이 메인문으로 다시 돌아가겠지 . ( viewpager 있는쪽!)
        //안드로이드에서 inflate 를 사용하면 xml 에 씌여져 있는 view 의 정의를 실제 view 객체로 만드는 역할을 하게된다.
        //마치 건물의 설계도( xml 정의 )를 쭉~ 그려놓고 inflate ( 부풀리다 ) 하면 펑~ 하고 실제 건물 ( view ) 가 완성된다는
        // 데서 inflate 라는 단어를 사용한 것 같다.
    }

}