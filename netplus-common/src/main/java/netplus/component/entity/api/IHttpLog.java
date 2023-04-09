package netplus.component.entity.api;


public interface IHttpLog {

    int insert(ApiLog record);

    int updateByPrimaryKeySelective(ApiLog record);

}
