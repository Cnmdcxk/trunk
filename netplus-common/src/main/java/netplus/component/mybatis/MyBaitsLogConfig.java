package netplus.component.mybatis;

import org.apache.ibatis.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author comet
 * @version 1.0
 * @description 用于MyBaits 日志输出SQL
 *              如果不使用 请在 application.yml
 *              mybatis-plus.configuration.log-impl = org.apache.ibatis.logging.stdout.StdOutImpl
 * @date 2021-10-02
 */
public class MyBaitsLogConfig implements Log {
    private Logger log;

    public MyBaitsLogConfig(String clazz) {
        log = LoggerFactory.getLogger(clazz);
    }

    @Override
    public boolean isDebugEnabled() {
        // 默认开启 Debug
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        // 默认开启 Trace
        return true;
    }

    @Override
    public void error(String s, Throwable e) {
        log.error(s,e);
    }

    @Override
    public void error(String s) {
        log.error(s);
    }

    @Override
    public void debug(String s) {
        // SQL 获取参数输出至 info
        log.info(s);
    }

    @Override
    public void trace(String s) {
        //SQL 信息输出至 info
        log.info(s);
    }

    @Override
    public void warn(String s) {
        log.info(s);
    }
}
