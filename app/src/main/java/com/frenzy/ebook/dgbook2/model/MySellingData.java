package com.frenzy.ebook.dgbook2.model;

import com.frenzy.ebook.dgbook2.GetnSet.MySellingListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 민경 on 2016-11-13.
 * DB값 받는 ..
 */

/**
 클래스명 : public class  MySellingData
 주된기능 : 내가 판 책정보인 책제목, 저자, 출판사를 DB로부터 직접적으로 받는 기능

 멤버변수 :
 private static final String[] titles : 제목 문자배열
 private static final String[] writer : 저자 문자배열
 private static final String[] company : 출판사 문자배열

 메소드 :
 public static List<MySellingData> getListData()
 * 매개변수:
 * 기능 : MySellingData 객체에 데이터를 설정(set)한고 반환한다.
 */



public class MySellingData {
    private static final String[] titles={"안드로이드","자료구조","시스템소프트웨어"};
    private static final String[] writer={"Bruce Lee","Jtm","lINDAk"};
    private static final String[] company={"생릉출판사","책방","한샘"};

    private static int icons =(android.R.drawable.ic_popup_reminder);
    public static List<MySellingListItem> getListData(){
        List<MySellingListItem>data=new ArrayList<>();

        //4번 반복 스크롤뷰 하려고
        //recyclerView
        for(int x=0;x<4;x++){
            //create Listitem with dummy data, then add to list
            for(int i=0;i<titles.length;i++){
                MySellingListItem item=new MySellingListItem();
                item.setImageResId(icons);
                item.setTitle(titles[i]);
                item.setWriter(writer[i]);
                item.setCompany(company[i]);
                data.add(item);
            }
        }
        return data;
    }

}
