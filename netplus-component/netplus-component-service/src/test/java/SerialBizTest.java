import netplus.serial.api.request.SerialParam;
import netplus.serial.service.biz.SerialBiz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SerialBizTest {

    @Autowired
    private SerialBiz serialBiz;

    @Test
    public void getSerial() {

        SerialParam param = new SerialParam("lhp", "333");

        String code = serialBiz.getSerial(param);

        System.out.println(code);

        code = serialBiz.getSerial(param);

        System.out.println(code);
    }


    @Test
    public void getRg() {

        SerialParam param = new SerialParam("lhp", "333");

        String code = serialBiz.getSerial(param);

        System.out.println(code);

        code = serialBiz.getRg(param);

        System.out.println(code);
    }
}