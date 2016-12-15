package com.frenzy.ebook.dgbook2.Tutorial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.frenzy.ebook.dgbook2.R;
/**
 * Created by SeungHyeonPark on 2016. 11. 10..
 */


/**
 *  튜토리얼을 이루고있는 프레그먼트는 전부 xml파일에서 실질적인 데이터를 가져옵니다.
 */

public class EnrollTutorial extends Fragment {

    public static EnrollTutorial newInstance() {
        EnrollTutorial fragment = new EnrollTutorial();
        return fragment;
    }

    public EnrollTutorial() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.enroll_tutorial, null);
        return root;
    }
}
