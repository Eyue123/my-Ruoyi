package com.ruoyi.justForTest;

public enum MailSubjectConfig {
    MailSubject("xx银行信用卡电子账单");

    private String subject;
    MailSubjectConfig(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
}
