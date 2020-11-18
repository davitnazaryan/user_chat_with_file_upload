package am.gitc.chat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Message {

  private int id;

  @JsonProperty("sender_id")
  private int senderId;

  @JsonProperty("receiver_id")
  private int receiverId;
  private String message;

  @JsonProperty("created_at")
  private Date createdAt;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getSenderId() {
    return senderId;
  }

  public void setSenderId(int senderId) {
    this.senderId = senderId;
  }

  public int getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(int receiverId) {
    this.receiverId = receiverId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }
}
