package com.frenzy.ebook.dgbook2.Crawler;

/**
 * 클래스 이름 : public class DonggukCrawler extends Crawler
 * 주요 기능 : 학교에서 계정정보를 불러와야하는데 이의 데이터를 따로 보관중.
 */


public class DonggukCrawler extends Crawler {


    public DonggukCrawler(final String userId, final String userPw) {
        URL_AUTH = "https://eclass.dongguk.edu/User.do?cmd=loginUser"; // Login Link
        URL_HOME = "https://eclass.dongguk.edu/Main.do?cmd=viewEclassMain"; // Link for retrieving user Name
        FORM_ID = "userDTO.userId";
        FORM_PW = "userDTO.password";
        ID = userId;
        PW = userPw;
        CSS = "SPAN>strong";
    }
}
