package myTest;

import com.ruoyi.justForTest.Sysconfig;

public class SysconfigTest {

    public static void main(String[] args) {
        // 正常情况，资源键存在
        String result = Sysconfig.getString("existingKey");
        System.out.println(result);

        String name = Sysconfig.getString("name");
        System.out.println(name);

        String nameUnicode = Sysconfig.getString("nameUnicode");
        System.out.println(nameUnicode);

        // 资源键不存在，应返回 "0"
        String nonExistingKeyResult = Sysconfig.getString("nonExistingKey");
        System.out.println(nonExistingKeyResult);
    }
}
