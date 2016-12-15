package com.frenzy.ebook.dgbook2.GetnSet;

/**
 * Created by YoungHoonKim on 11/12/16.
 */

/**
 *
 */


public class UserID {
    private int ID;
    private String user_ID;
    private String passwd;
    private String phone_Num;
    private String student_ID;

    public UserID(String user_ID, String passwd, String phone_Num, String student_ID) {
        this.user_ID = user_ID;
        this.passwd = passwd;
        this.phone_Num = phone_Num;
        this.student_ID = student_ID;
    }

    public UserID(String user_ID, String passwd) {
        this.user_ID = user_ID;
        this.passwd = passwd;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPhone_Num() {
        return phone_Num;
    }

    public void setPhone_Num(String phone_Num) {
        this.phone_Num = phone_Num;
    }

    public String getStudent_ID() {
        return student_ID;
    }

    public void setStudent_ID(String student_ID) {
        this.student_ID = student_ID;
    }
}
