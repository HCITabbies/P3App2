package com.p3app2.Chat_Window;

/**
 * Created by rohan on 15-05-2017.
 */

public class ChatMessage {
        private String id;
        private boolean isMe;
        private String message;
        private Long userId;
        private String dateTime;


        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public boolean getIsme() {
            return isMe;
        }
        public void setMe(boolean isMe) {
            this.isMe = isMe;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getDate() {
            return dateTime;
        }

        public void setDate(String dateTime) {
            this.dateTime = dateTime;
        }
}

