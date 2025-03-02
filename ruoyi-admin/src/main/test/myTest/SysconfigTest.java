package myTest;

import com.ruoyi.justForTest.Sysconfig;

import java.time.ZoneId;

public class SysconfigTest {

    public static void main(String[] args) {
        // 正常情况，资源键存在
        String result = Sysconfig.getString("existingKey");
        System.out.println(result);

        String name = Sysconfig.getString("name");
        System.out.println(name);

        String direct = "直接上海农商";
        System.out.println(direct);

        String nameUnicode = Sysconfig.getString("nameUnicode");
        System.out.println(nameUnicode);

        ZoneId systemZone = ZoneId.systemDefault();
        System.out.println(systemZone);

        // 资源键不存在，应返回 "0"
        String nonExistingKeyResult = Sysconfig.getString("nonExistingKey");
        System.out.println(nonExistingKeyResult);
    }
}
