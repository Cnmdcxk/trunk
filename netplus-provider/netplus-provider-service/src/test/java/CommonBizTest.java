import netplus.provider.api.request.LoginRequest;
import netplus.provider.service.Application;
import netplus.provider.service.biz.IdentityBiz;
import netplus.provider.service.biz.ProviderBiz;
import netplus.provider.service.biz.RoleBiz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class CommonBizTest {

    @Autowired
    RoleBiz roleBiz;

    @Autowired
    IdentityBiz identityBiz;

    @Test
    public void getUserDataPrivilegeTest(){

        LoginRequest request =new LoginRequest();
        request.setAppId("500000141");
        request.setTicket("");
        identityBiz.chatLogin(request);

    }

}
