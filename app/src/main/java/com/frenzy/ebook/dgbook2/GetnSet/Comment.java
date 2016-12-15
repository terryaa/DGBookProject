package com.frenzy.ebook.dgbook2.GetnSet;

/**
 * Created by YoungHoonKim on 11/28/16.
 */

public class Comment {

    private int commentid;
    private String author;
    private String name;
    private String owner;
    private String content;
    private String time;
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

    public Comment(String author, String owner, String content, int bookid,String name) {
        this.author = author;
        this.owner = owner;
        this.content = content;
        this.bookid = bookid;
        this.name=name;
    }

    public Comment() {
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
