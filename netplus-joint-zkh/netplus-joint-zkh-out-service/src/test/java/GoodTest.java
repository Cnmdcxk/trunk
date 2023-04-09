import netplus.joint.zkh.api.rest.ZkhOutRest;
import netplus.joint.zkh.out.service.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class GoodTest {

    @Autowired
    ZkhOutRest zkhOutRest;

    @Test
    public void goodPriceSyncTest () {
        zkhOutRest.goodPriceSync();
    }

    @Test
    public void goodDetailSyncTest () {
        zkhOutRest.goodDetailSync();
    }

}
