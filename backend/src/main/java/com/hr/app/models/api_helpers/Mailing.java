package com.hr.app.models.api_helpers;

public class Mailing {
    private String userMail;
    private String userName;

    public static class MailNewQuiz {
        private String announcementTitle;
        private String testCode;

        public String getAnnouncementTitle() {
            return announcementTitle;
        }

        public void setAnnouncementTitle(String announcementTitle) {
            this.announcementTitle = announcementTitle;
        }

        public String getTestCode() {
            return testCode;
        }

        public void setTestCode(String testCode) {
            this.testCode = testCode;
        }

        public MailNewQuiz(String announcementTitle, String testCode) {
            this.announcementTitle = announcementTitle;
            this.testCode = testCode;
        }
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Mailing(String userMail, String userName) {
        this.userMail = userMail;
        this.userName = userName;
    }
}
