package com.frenzy.ebook.dgbook2.GetnSet;

/**
 * Created by 민경 on 2016-11-26.
 */

/**
 클래스명 : public class CommentListItem
 주된기능 : 댓글 등록 시 Recyclerview에 출력되는 발신자와 댓글내용 data를 반환해 보이게하는 기능

 멤버변수 :
 String sender:발신자 이름문자열
 String mailbox:댓글 내용 문자열

 메소드 :
 public String getsender()
 * 매개변수:
 * 기능 : sender 문자열을 반환한다
 public void setsender(String sender)
 * 매개변수 :String sender
 * 기능 : sender를 설정한다
 public String getmailbox()
 * 매개변수 :
 * 기능 : 댓글 내용 문자열을 반환한다.
 public void setmailbox(String mailbox)
 * 매개변수 : String mailbox
 *기능 : 댓글내용을 설정한다.
 */


public class CommentListItem {

    private int commentid;
    private String author;
    private String owner;
    private String content;
    private String time;
    private String name;
    private int bookid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCommentid() {
        return commentid;
    }

    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }
}

