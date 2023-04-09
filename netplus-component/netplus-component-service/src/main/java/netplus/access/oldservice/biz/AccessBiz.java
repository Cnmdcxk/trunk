package netplus.access.oldservice.biz;

import netplus.component.entity.auth.ApplicationVo;
import netplus.component.entity.auth.IAccess;
import netplus.component.entity.enums.YesNoEnum;
import org.springframework.stereotype.Service;


@Service
public class AccessBiz implements IAccess {

    public String selectAccessTokenByDomain(String domain) {

        return "ygmall.v2";
    }


    public ApplicationVo selectByAccessToken(String accessToken) {

        ApplicationVo applicationVo = new ApplicationVo();
        applicationVo.setAccessToken(accessToken);
        applicationVo.setAppId(accessToken);
        applicationVo.setIsActive(YesNoEnum.Yes.getValue());

        return applicationVo;
    }
}
