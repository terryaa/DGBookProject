package com.frenzy.ebook.dgbook2.Crawler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;


/**
 * 클래스 이름 : public abstract class Crawler
 * 주요 기능 : Jsoup html Parser를 이용, 학교 Eclass 홈페이지를 파싱하면서 아이디와 비밀번호에 맞는 폼의 아이디를 활용
 *           사용자, 특히 이 앱은 오로지 동국대학교 학생들만 이용이 가능하게 제작이 되었으므로, eclass에 접속하는 ID,PW을 이용, 별다른 회원가입없이
 *           서비스를 이용 할 수 있게 함
 *           실질적으로 이클래스에 실제로 로그인 되지않지만, 계정정보만 불러와 일치하는지,안하는지의 여부만 따지게하므로 얻어온 계정정보를 SharedPreference를 이용해
 *           자동로그인에 구현할수있게 함
 * 멤버함수
 * public static final int LENGTH_DONGGUK = 10; //학번의 길이는 10자리 이므로 10으로 지정
 * protected String userName = "";  사용자의 이름을 저장하는 공간
 * protected Document document;
 * protected String URL_AUTH; DonggukCrawler 에서 받아올 URL_AUTH를 저장
 * protected String URL_HOME; DonggukCrawler 에서 받아올 URL_HOME를 저장
 * protected String FORM_ID;  DonggukCrawler 에서 받아올 FORM_ID를 저장, 웹페이지 상의 입력된 아이디
 * protected String FORM_PW;  DonggukCrawler 에서 받아올 FORM_PW를 저장, 웹페이지 상의 입력된 비밀번호
 * protected String CSS;      DonggukCrawler 에서 받아올 CSS를 저장, 웹페이지 상의 <span><strong> name </strong></span>의 형식에서 가져옴
 * protected String ID;       DonggukCrawler 에서 받아온 ID를 저장
 * protected String PW;       DonggukCrawler 에서 받아온 PW를 저장
 *
 * 메소드
 * public void verify(final Handler h)
 * 매개변수 : final Handler h
 * 주요기능 : 스레드를 이용해서 로그인 기능을 구현한것
 * public String getAuth()
 * 매개변수
 * 주요기능 : 로그인할떄 쓰는 주소를 리턴
 *
 */



public abstract class Crawler {

    public static final int LENGTH_DONGGUK = 10;

    protected String userName = "";

    protected Document document;

    protected String URL_AUTH;
    protected String URL_HOME;
    protected String FORM_ID;
    protected String FORM_PW;
    protected String CSS;
    protected String ID;
    protected String PW;

    public String getAuth(){
        return URL_AUTH;
    }

    public void verify(final Handler h){
        Thread t = new Thread() {
            public void run() {
                boolean result = false;

                try{
                    Connection.Response res = Jsoup.connect(URL_AUTH)
                            .followRedirects(true)
                            .data(FORM_ID, ID)
                            .data(FORM_PW, PW)
                            .method(Connection.Method.POST)
                            .timeout(TIMEOUT)
                            .execute();// 아이디와 비밀번호 입력받았을때 리액션
                    document = Jsoup.connect(URL_HOME) //
                            .followRedirects(true)
                            .cookies(res.cookies())
                            .timeout(TIMEOUT)
                            .get();
                    Element getUser = document.select(CSS).first(); //이름을 웹페이지에서 가져옴
                    if(getUser == null) {
                        result = false;
                    }else{
                        result = true;
                        userName = getUser.text().trim(); // 이름 맞춤
                    }
                }catch (IOException e){
                    result = false;
                }

                Message msg = h.obtainMessage();
                Bundle bundle = new Bundle();

                bundle.putBoolean("result", result);
                bundle.putString("id", ID);
                bundle.putString("pw", PW);
                bundle.putString("name", userName); // 번들에 개인정보 삽입

                msg.setData(bundle);
                h.sendMessage(msg);
            }
        };
        t.start();
    }

    protected static final int TIMEOUT = 15000;

}
