package myTest;

import com.ruoyi.justForTest.MailSubjectConfig;

public class MailSubjectTest {

    public static void main(String[] args) {

        String defaultSubject = MailSubjectConfig.MailSubject.getSubject();
        System.out.println(defaultSubject);

    }
}
