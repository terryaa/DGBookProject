package com.frenzy.ebook.dgbook2.GetnSet;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by YoungHoonKim on 11/12/16.
 */

/**
 * 클래스 이름 : public class Book implements Serializable
 * 주요 기능 : 책의 정보를 외부 매개변수를 통해 입력받아 책 객체를 형성하는 클래스
 * 멤버변수
 * private int BookID; : 데이터베이스에 저장될 책의 고유한 아이디를 받는 변수
 * private String Title; : 데이터베이스에 저장될 책의 이름을 받는 변수
 * private String Company; : 데이터베이스에 저장될 책의 출판사를 받는 변수
 * private String Author; : 데이터베이스에 저장될 책의 저자를 받는 변수
 * private String professor; : 데이터베이스에 저장될 교수님 성함을 받는 변수
 * private String Course; : 데이터베이스에 저장될 강좌의 이름을 받는 변수
 * private int Price; : 데이터베이스에 저장될 책의 가격을 받는 변수
 * private int Type; : 데이터베이스에 저장될 책의 타입을 받는 변수
 * private String image : 데이터베이스에 저장죌 책의 이미지를 스트링값으로 받아 저장
 * private Bitmap bitmap=null; 데이터베이스에 저장될 비트맵이미지를 초기화시킴
 * private int Progress; // 상태를 나타내는 변수
 */

/**
 * Created by YoungHoonKim on 11/12/16.
 */

public class Book implements Serializable {
    private int BookID;
    private String Title;
    private String Company;
    private String Author;
    private String professor;
    private String Course;
    private int Price;
    private int Type;
    private String image;
    private Bitmap bitmap=null;
    private int Progress;
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public Book(String title, String company, String author, String professor, String course, int price, int type,String image,
                Bitmap bitmap,String owner) {
        Title = title;
        Company = company;
        Author = author;
        this.professor = professor;
        Course = course;
        Price = price;
        Type = type;
        this.image=image;
        this.bitmap=bitmap;
        this.owner=owner;
    }

    public int getBookID() {
        return BookID;
    }

    public void setBookID(int bookID) {
        BookID = bookID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getProgress() {
        return Progress;
    }

    public void setProgress(int progress) {
        Progress = progress;
    }
}
