package com.edu.thanhnam.noteweek3.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ThanhNam on 4/12/2017.
 */

public class Note implements Serializable {

  public String id;
  public String title;
  public String time;
  public String content;
  @Exclude
  public boolean isSelected;


  public Note() {
  }

  public Note(String title, String time, String content) {

    this.title = title;
    this.time = time;
    this.content = content;
  }

  public Note(String id, String title, String time, String content) {
    this.id = id;
    this.title = title;
    this.time = time;
    this.content = content;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    isSelected = selected;
  }

  public Map<String, Object> toMap() {
    HashMap<String, Object> result = new HashMap<>();
    result.put("id", id);
    result.put("title", title);
    result.put("time", time);
    result.put("content", content);
    return result;

  }
}
