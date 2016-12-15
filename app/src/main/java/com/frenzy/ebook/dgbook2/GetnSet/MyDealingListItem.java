package com.frenzy.ebook.dgbook2.GetnSet;

/**
 * Created by 민경 on 2016-11-15.
 */

import android.graphics.Bitmap;

/**
 클래스명 : public class MyDealingListItem
 주된기능 : 내가 거래중인 책 Recyclerview에 출력되는 제목 저자 출판사 판매,대여 여부 data를 반환해 보이게하는 기능
 멤버변수 :
 String title:책 제목 문자열
 String writer:책 저자 문자열
 String company:책 출판사 문자열
 Bitmap bitmap:책 미리보기 이미지
 int type:책 대여/판매 여부

 메소드 :
 public int getType()
 * 매개변수:
 * 기능 : 책의 대여 판매 여부를 정수로 구분해 반환한다.
 public void setType(int type)
 * 매개변수 :int type
 * 기능 : 책의 판매/대여 여부를 설정한다
 public Bitmap getBitmap()
 * 매개변수 :
 * 기능 : 책 이미지 비트맵을 반환한다
 public void setBitmap(Bitmap bitmap)
 * 매개변수 : Bitmap bitmap
 * 기능 : 비트맵 이미지를 설정한다
 public String getCompany()
 * 매개변수 :
 * 기능 : 출판사 문자열을 반환한다
 public void setCompany(String company)
 * 매개변수 : String company
 * 기능 : 출판사 문자열을 설정한다.
 public String getWriter()
 * 매개변수 :
 * 기능 : 저자 문자열을 반환한다
 public void setWriter(String writer)
 * 매개변수 : String writer
 * 기능 : 저자 문자열을 설정한다
 public String getTitle()
 * 매개변수 :
 * 기능 : 제목 문자열을 반환한다
 public void setTitle(String title)
 * 매개변수 : String title
 * 기능 : 제목 문자열을 설정한다.
 */

public class MyDealingListItem {

    private int bookid;
    private String title;
    private String writer;
    private String company;
    private int type;
    private int imageResId;
    private Bitmap bitmap;
    private int progress;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }



    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle(){return title;}

    public void setTitle(String title) {this.title = title;}

    public int getImageResId(){return imageResId;}

    public void setImageResId(int imageResId) { this.imageResId = imageResId;}
}

