package vttp.batch5.ssf.noticeboard.models;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class notice {

    @NotNull(message = "Title is Required")
    @Size(min = 3, max = 128)
    private String title;

    @Email(message = "must be a well-formed email address")
    @NotNull(message = "Email is Required")
    private String poster;

    @NotNull(message = "Date is Required")
    @Future(message = "Date cannot be set before today")
    private Date postDate;

    @NotNull(message = "Required")
    @Size(min = 1)
    private String categories;
    
    @NotNull(message = "Please Input Notice")
    private String text;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
    public Date getPostDate() {
        return postDate;
    }
    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
    public String getCategories() {
        return categories;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public notice() {}
    public notice(String title, String poster, Date postDate, String categories, String text) {
        this.title = title;
        this.poster = poster;
        this.postDate = postDate;
        this.categories = categories;
        this.text = text;
    }
    public static notice tonotice(String json){
        notice notice = new notice();
        
      JsonReader reader = Json.createReader(new StringReader(json));
      JsonObject jsonObj = reader.readObject();

      notice.setTitle(jsonObj.getString("title"));
      notice.setPoster(jsonObj.getString("poster"));
      notice.setCategories(jsonObj.getString("categories"));
      notice.setText(jsonObj.getString("text"));

      Date deliveryDate = new Date();
      try {
         deliveryDate = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObj.getString("deliveryDate"));
      } catch (Exception ex) {
         ex.printStackTrace();
      }
      notice.setPostDate(deliveryDate);
      return notice;
    }

}
