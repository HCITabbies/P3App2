package com.p3app2.Chat_Window;

import java.util.List;

/**
 * Created by rohan on 17-05-2017.
 */

public class ChatRecord {
    private int ChatID;
    private List<ChatMessage> chatMessages;
    private int numberOfMessages;
    private String dateTimeStarted;
    private String dateTimeEnded;

    public int getChatID() {
        return ChatID;
    }

    public void setChatID(int chatID) {
        ChatID = chatID;
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public int getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setNumberOfMessages(int numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }

    public String getDateTimeStarted() {
        return dateTimeStarted;
    }

    public void setDateTimeStarted(String dateTimeStarted) {
        this.dateTimeStarted = dateTimeStarted;
    }

    public String getDateTimeEnded() {
        return dateTimeEnded;
    }

    public void setDateTimeEnded(String dateTimeEnded) {
        this.dateTimeEnded = dateTimeEnded;
    }





}
