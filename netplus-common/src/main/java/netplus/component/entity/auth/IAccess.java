package netplus.component.entity.auth;


public interface IAccess {

    String selectAccessTokenByDomain(String domain);

    ApplicationVo selectByAccessToken(String accessToken);

}
