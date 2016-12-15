package com.frenzy.ebook.dgbook2.Tutorial;

/**
 * Created by SeungHyeonPark on 2016. 11. 10..
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.frenzy.ebook.dgbook2.R;
/**
 *  튜토리얼을 이루고있는 프레그먼트는 전부 xml파일에서 실질적인 데이터를 가져옵니다.
 */

public class MessageTutorial extends Fragment {

    public static MessageTutorial newInstance() {
        MessageTutorial fragment = new MessageTutorial();
        return fragment;
    }

    public MessageTutorial() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.message_tutorial, null);
        return root;
    }
}