package com.frenzy.ebook.dgbook2.Tutorial;

/**
 * Created by SeungHyeonPark on 2016. 11. 10..
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.frenzy.ebook.dgbook2.Activity.LoginActivity;
import com.frenzy.ebook.dgbook2.Activity.IntroActivity;
import com.frenzy.ebook.dgbook2.R;

import static android.content.Context.MODE_PRIVATE;
/**
 *  튜토리얼을 이루고있는 프레그먼트는 전부 xml파일에서 실질적인 데이터를 가져옵니다.
 *  하지만 버튼을 눌러 액티비티를 빠져나와야하므로 getActivity()를 통해 본래의 액티비티를 얻고 다음 액티비티로 전환
 */
//끝나는페이지로, 자기정보 등록하기버튼 누름으로서, 다음 개인정보 입력하는액티비티로 넘어가기

public class EndTutorial extends Fragment {
    //Sqlite sqlite;
    IntroActivity startActivity;
    private Button btnStart;

    public static EndTutorial newInstance() {
        EndTutorial fragment = new EndTutorial();
        return fragment;
    }

    public EndTutorial() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.end_tutorial, null);
        Button btnStart = (Button)root.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return root;
    }
}
