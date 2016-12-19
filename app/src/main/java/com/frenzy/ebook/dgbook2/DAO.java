package com.frenzy.ebook.dgbook2;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.frenzy.ebook.dgbook2.GetnSet.Book;
import com.frenzy.ebook.dgbook2.GetnSet.Comment;
import com.frenzy.ebook.dgbook2.GetnSet.UserID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by YoungHoonKim on 11/12/16.
 */

public class DAO {
    public static Bitmap[] bitmap;

    public boolean sellbook(int bookid) {

        try {
            String link = "http://52.78.227.248/sellbook.php";
            String data = URLEncoder.encode("bookid", "UTF-8")
                    + "=" + URLEncoder.encode(String.valueOf(bookid), "UTF-8");
            data+="&"+URLEncoder.encode("progress", "UTF-8")+ "=" + URLEncoder.encode(String.valueOf(1), "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            String result=sb.toString();
            if(result.contains("success"))
                return true;
            else
                return false;
        } catch (Exception e) {
            //return new String("Exception: " + e.getMessage());
            return false;
        }

    }

    public boolean lendfinishbook(int bookid) {

        try {
            String link = "http://52.78.227.248/sellbook.php";
            String data = URLEncoder.encode("bookid", "UTF-8")
                    + "=" + URLEncoder.encode(String.valueOf(bookid), "UTF-8");
            data+="&"+URLEncoder.encode("progress", "UTF-8")+ "=" + URLEncoder.encode(String.valueOf(0), "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            String result=sb.toString();
            if(result.contains("success"))
                return true;
            else
                return false;
        } catch (Exception e) {
            //return new String("Exception: " + e.getMessage());
            return false;
        }


    }
    public boolean lendstartbook(int bookid) {

        try {
            String link = "http://52.78.227.248/sellbook.php";
            String data = URLEncoder.encode("bookid", "UTF-8")
                    + "=" + URLEncoder.encode(String.valueOf(bookid), "UTF-8");
            data+="&"+URLEncoder.encode("progress", "UTF-8")+ "=" + URLEncoder.encode(String.valueOf(2), "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            String result=sb.toString();
            if(result.contains("success"))
                return true;
            else
                return false;
        } catch (Exception e) {
            //return new String("Exception: " + e.getMessage());
            return false;
        }

    }

    public ArrayList<Book> searchmybook(String owner)
    {
        ArrayList<Book> results=new ArrayList<>();
        try {
            String link = "http://52.78.227.248/searchmybook.php";
            String data = URLEncoder.encode("owner", "UTF-8") + "=" + URLEncoder.encode(owner, "UTF-8");
            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            String queryJson=sb.toString().trim();
            JSONObject jsonObj = new JSONObject(queryJson);
            JSONArray BookInfo=jsonObj.getJSONArray("result");
            Book books;
            for(int i=0;i<BookInfo.length();i++) {
                JSONObject temp = BookInfo.getJSONObject(i);
                int bookid=Integer.valueOf(temp.getString("BookID"));
                String title=temp.getString("title");
                String companyy=temp.getString("company");
                String author=temp.getString("author");
                int typee=Integer.valueOf(temp.getString("type"));
                int progress=Integer.valueOf(temp.getString("progress"));
                String image=temp.getString("url");
                bitmap=new Bitmap[1];
                try {
                    url = new URL(image);
                    bitmap[0] = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                books=new Book(title,companyy,author,null,null,0,typee,image,bitmap[0],null);
                books.setBookID(bookid);
                books.setProgress(progress);
                results.add(books);
            }
            return results;
        }
        catch (Exception e){
            return null;
        }
    }


    public boolean insertcomment(Comment comment) {
        String Author = comment.getAuthor();
        String Owner=comment.getOwner();
        String Content=comment.getContent();
        String BookID=String.valueOf(comment.getBookid());
        String name=comment.getName();

        try {

            String link = "http://52.78.227.248/insertcomment.php";
            String data = URLEncoder.encode("author", "UTF-8") + "=" + URLEncoder.encode(Author, "UTF-8");
            data += "&" + URLEncoder.encode("owner", "UTF-8") + "=" + URLEncoder.encode(Owner, "UTF-8");
            data += "&" + URLEncoder.encode("content", "UTF-8") + "=" + URLEncoder.encode(Content, "UTF-8");
            data += "&" + URLEncoder.encode("bookid", "UTF-8") + "=" + URLEncoder.encode(BookID, "UTF-8");
            data += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");


            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            String result=sb.toString();
            boolean flag;
            if(result.contains("success")) {
                flag = true;
            }
            else {
                flag = false;
            }
            return flag;
        }
        catch (Exception e) {
            return true;
        }

    }

    public ArrayList<Comment> selectcomment(int bookid)
    {
        ArrayList<Comment> comments=new ArrayList<>();
        try {
            String BookID=String.valueOf(bookid);

            String link = "http://52.78.227.248/selectcomment.php";
            String data = URLEncoder.encode("bookid", "UTF-8") + "=" + URLEncoder.encode(BookID, "UTF-8");


            URL url = new URL(link);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

            wr.write(data);
            wr.flush();

            StringBuilder sb = new StringBuilder();

            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(con.getInputStream()));

            String json;
            while((json = bufferedReader.readLine())!= null){
                sb.append(json+"\n");
            }

            String queryJson=sb.toString().trim();
            JSONObject jsonObj = new JSONObject(queryJson);
            JSONArray BookInfo=jsonObj.getJSONArray("result");

            Comment comment;
            for(int i=0;i<BookInfo.length();i++) {
                JSONObject temp = BookInfo.getJSONObject(i);
                int id=Integer.valueOf(temp.getString("commentid"));
                String author=temp.getString("author");
                String owner=temp.getString("owner");
                String content=temp.getString("content");
                String time=temp.getString("time");
                int bookid_c=Integer.valueOf(temp.getString("bookid"));
                String name=temp.getString("name");
                comment=new Comment(author,owner,content,bookid_c,name);
                comment.setCommentid(id);
                comment.setTime(time);
                comments.add(comment);
            }

            return comments;


        }catch (Exception e) {
            //return new String("Exception: " + e.getMessage());
            return null;
        }
    }

    public ArrayList<Integer> searchmycommentbook(String author)
    {
        try {
            String link = "http://52.78.227.248/selectmycomment.php";
            String data = URLEncoder.encode("author", "UTF-8") + "=" + URLEncoder.encode(author, "UTF-8");



            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }

            String queryJson=sb.toString().trim();
            JSONObject jsonObj = new JSONObject(queryJson);
            JSONArray Comment=jsonObj.getJSONArray("result");



            ArrayList<Integer> bookid= null;
            if(Comment.getJSONObject(0)==null)
            {
                return bookid;
            }
            bookid=new ArrayList<>();
            JSONObject temp = Comment.getJSONObject(0);
            bookid.add(Integer.valueOf(temp.getString("bookid")));
            for(int i=1;i<Comment.length();i++)
            {
                JSONObject temp1 = Comment.getJSONObject(i);
                if(bookid.get(bookid.size()-1)!=Integer.valueOf(temp1.getString("bookid")))
                {
                    bookid.add(Integer.valueOf(temp1.getString("bookid")));
                }
            }
            return bookid;
        }catch (Exception e) {
            //return new String("Exception: " + e.getMessage());
            return null;
        }

    }

    public ArrayList<Book> search(Book book)
    {
        ArrayList<Book> results=new ArrayList<>();
        try {
            String name = book.getTitle();
            String company = book.getCompany();
            String writer = book.getAuthor();
            String prof = book.getProfessor();
            String course = book.getCourse();
            String type=String.valueOf(book.getType());
            String link = "http://52.78.227.248/searchbook.php";
            String query="";
            if (name != "")
                query +=URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
            else
                query +=URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8");
            if (!company.contains(""))
                query +="&" + URLEncoder.encode("company", "UTF-8") + "=" + URLEncoder.encode(company, "UTF-8");
            else
                query +="&" + URLEncoder.encode("company", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8");
            if (!writer.contains(""))
                query +="&" + URLEncoder.encode("author", "UTF-8") + "=" + URLEncoder.encode(writer, "UTF-8");
            else
                query +="&" + URLEncoder.encode("author", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8");
            if (!prof.contains(""))
                query +="&" + URLEncoder.encode("professor", "UTF-8") + "=" + URLEncoder.encode(prof, "UTF-8");
            else
                query +="&" + URLEncoder.encode("professor", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8");
            if (!course.contains(""))
                query +="&" + URLEncoder.encode("course", "UTF-8") + "=" + URLEncoder.encode(course, "UTF-8");
            else
                query +="&" + URLEncoder.encode("course", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8");
            if (!type.contains(""))
                query +="&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");
            else
                query +="&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8");


            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(query);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }



            String queryJson=sb.toString().trim();
            JSONObject jsonObj = new JSONObject(queryJson);
            JSONArray BookInfo=jsonObj.getJSONArray("result");
            Book books;
            for(int i=0;i<BookInfo.length();i++) {
                JSONObject temp = BookInfo.getJSONObject(i);
                int id=Integer.valueOf(temp.getString("BookID"));
                String title= URLDecoder.decode(temp.getString("title"),"UTF-8");

                String companyy=temp.getString("company");
                String author=temp.getString("author");
                int typee=Integer.valueOf(temp.getString("type"));
                String image=temp.getString("url");
                int progress=Integer.valueOf(temp.getString("progress"));
                bitmap=new Bitmap[1];
                try {
                    url = new URL(image);
                    bitmap[0] = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                books=new Book(title,companyy,author,null,null,0,typee,image,bitmap[0],null);
                books.setBookID(id);
                books.setProgress(progress);
                results.add(books);
            }

            return results;
        }catch (Exception e) {
            //return new String("Exception: " + e.getMessage());
            return null;
        }

    }
    //책 정보를 데이터베이스에 입력
    public boolean insert(Book book) {

        try {
            String Title = book.getTitle();
            String Company = book.getCompany();
            String Author = book.getAuthor();
            String Professor = book.getProfessor();
            String Course = book.getCourse();
            String Price = String.valueOf(book.getPrice());
            String Type = String.valueOf(book.getType());
            String Image=book.getImage();
            String Owner=book.getOwner();

            String link = "http://52.78.227.248/insertbook.php";
            String data = URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(Title, "UTF-8");
            data += "&" + URLEncoder.encode("company", "UTF-8") + "=" + URLEncoder.encode(Company, "UTF-8");
            data += "&" + URLEncoder.encode("author", "UTF-8") + "=" + URLEncoder.encode(Author, "UTF-8");
            data += "&" + URLEncoder.encode("professor", "UTF-8") + "=" + URLEncoder.encode(Professor, "UTF-8");
            data += "&" + URLEncoder.encode("course", "UTF-8") + "=" + URLEncoder.encode(Course, "UTF-8");
            data += "&" + URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(Price, "UTF-8");
            data += "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(Type, "UTF-8");
            data += "&" + URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(Image, "UTF-8");
            data += "&" + URLEncoder.encode("owner", "UTF-8") + "=" + URLEncoder.encode(Owner, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            String result=sb.toString();
            if(result.contains("success"))
                return true;
            else
                return false;
        } catch (Exception e) {
            //return new String("Exception: " + e.getMessage());
            return false;
        }

    }

    public Book select(int bookID)
    {
        try {
            String BookID=String.valueOf(bookID);

            String link = "http://52.78.227.248/selectbookid.php";
            String data = URLEncoder.encode("bookid", "UTF-8") + "=" + URLEncoder.encode(BookID, "UTF-8");


            URL url = new URL(link);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

            wr.write(data);
            wr.flush();

            StringBuilder sb = new StringBuilder();

            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(con.getInputStream()));

            String json;
            while((json = bufferedReader.readLine())!= null){
                sb.append(json+"\n");
            }

            String queryJson=sb.toString().trim();
            JSONObject jsonObj = new JSONObject(queryJson);
            JSONArray BookInfo=jsonObj.getJSONArray("result");

            JSONObject temp=BookInfo.getJSONObject(0);
            bitmap=new Bitmap[1];
            for(int i=0;i<BookInfo.length();i++)
            {
                try {
                    url = new URL(temp.getString("url"));
                    bitmap[i] = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Book result=new Book(temp.getString("title"),temp.getString("company"),temp.getString("author"),
                    temp.getString("professor"),temp.getString("course"),Integer.valueOf(temp.getString("price")),
                    Integer.valueOf(temp.getString("type")),temp.getString("url"),bitmap[0],temp.getString("owner"));
            result.setBookID(Integer.valueOf(temp.getString("BookID")));
            result.setProgress(Integer.valueOf(temp.getString("progress")));
            return result;


        }catch (Exception e) {
            //return new String("Exception: " + e.getMessage());
            return null;
        }
    }

    public UserID select(UserID user)
    {
        try {
            String user_id = user.getUser_ID();
            String passwd = user.getPasswd();
            String phone_num = user.getPhone_Num();
            String student_id = user.getStudent_ID();
            String link = "http://52.78.227.248/selectuser.php";
            String data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");
            data += "&" + URLEncoder.encode("passwd", "UTF-8") + "=" + URLEncoder.encode(passwd, "UTF-8");

            URL url = new URL(link);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

            wr.write(data);
            wr.flush();

            StringBuilder sb = new StringBuilder();

            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(con.getInputStream()));

            String json;
            while((json = bufferedReader.readLine())!= null){
                sb.append(json+"\n");
            }

            String queryJson=sb.toString().trim();
            JSONObject jsonObj = new JSONObject(queryJson);
            JSONArray UserInfo=jsonObj.getJSONArray("result");

            JSONObject temp=UserInfo.getJSONObject(0);
            //int user_key=Integer.valueOf(temp.getString("UserKey"));
            user_id=temp.getString("UserID");
            passwd=temp.getString("PassWord");
            phone_num=temp.getString("PhoneNo");
            student_id=temp.getString("StudentID");

            return new UserID(user_id,passwd,phone_num,student_id);

        }catch (Exception e) {
            //return new String("Exception: " + e.getMessage());
            return null;
        }
    }
    public boolean insert(UserID user)
    {
        try {
            String user_id=user.getUser_ID();
            String passwd=user.getPasswd();
            String phone_num=user.getPhone_Num();
            String student_id=user.getStudent_ID();
            String link = "http://52.78.227.248/insertuser.php";
            String data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");
            data += "&" + URLEncoder.encode("passwd", "UTF-8") + "=" + URLEncoder.encode(passwd, "UTF-8");
            data += "&" + URLEncoder.encode("phonenum", "UTF-8") + "=" + URLEncoder.encode(phone_num, "UTF-8");
            data += "&" + URLEncoder.encode("studentid", "UTF-8") + "=" + URLEncoder.encode(student_id, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            String result=sb.toString();
            if(result.contains("success"))
                return true;
            else
                return false;
        } catch (Exception e) {
            //return new String("Exception: " + e.getMessage());
            return false;
        }
    }
}

