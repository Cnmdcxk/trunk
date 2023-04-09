package netplus.utils.uuid;

import java.util.UUID;

/**
 * Created by py on 2021/7/25.
 */
public class UuidUtil {

    public static String getUuid () {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static String getUuid (String prefix) {
        return String.format("%s%s", prefix, UuidUtil.getUuid());
    }


    public static String getUuid2 () {
        return UUID.randomUUID().toString();
    }
}
