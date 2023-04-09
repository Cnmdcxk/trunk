package netplus.provider.service.biz;

import netplus.component.entity.data.PageBean;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.response.ApiResponse;
import netplus.provider.api.pojo.ygmalluser.Tbmqq461;
import netplus.provider.api.request.AddServiceConfigRequest;
import netplus.provider.api.request.ServiceConfigRequest;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.vo.LoginUserBean;
import netplus.provider.api.vo.Tbmqq461Vo;
import netplus.provider.service.dao.Tbmqq461Mapper;
import netplus.utils.date.DateUtil;
import netplus.utils.object.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceConfigBiz {

    @Autowired
    private Tbmqq461Mapper tbmqq461Mapper;

    @Autowired
    IdentityRest identityRest;

    /**
     * 分页查询
     *
     * @param request
     * @return
     */
    public PageBean<Tbmqq461Vo> getListService(ServiceConfigRequest request) {

            PageBean<Tbmqq461Vo> pageBean = new PageBean();

            Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);

            // 获取业务配置表数据集合
            List<Tbmqq461Vo> tbmqq461VoList = tbmqq461Mapper.getAll(mapParam);

            int count461 = tbmqq461Mapper.getCount(mapParam);

            pageBean.setTotalCount(count461);
            pageBean.setItems(tbmqq461VoList);

            return pageBean;
        }

    /**
     * 添加
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse addServiceConfig(AddServiceConfigRequest request) {

        Date now = new Date();

        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        Map<String, Object> mapParam = new HashMap<String , Object>();

        mapParam.put("code", request.getCode());

        // 根据code查找业务配置表
        Tbmqq461 existTbmqq461 = tbmqq461Mapper.getByCode(mapParam);

        // 不存在时，新增
        if ((ObjectUtil.isEmpty(existTbmqq461)) ) {
            Tbmqq461 Tbmqq461 = new Tbmqq461();
            Tbmqq461.setCode(request.getCode());
            Tbmqq461.setName(request.getName());
            Tbmqq461.setVal(request.getVal());
            Tbmqq461.setCreateuser(loginUserBean.getUserCode());
            Tbmqq461.setCreatedate(DateUtil.format(now, "yyyyMMdd"));
            Tbmqq461.setCreatetime(DateUtil.format(now, "HHmmss"));
            Tbmqq461.setUpdateuser(loginUserBean.getUserCode());
            Tbmqq461.setUpdatedate(DateUtil.format(now, "yyyyMMdd"));
            Tbmqq461.setUpdatetime(DateUtil.format(now, "HHmmss"));

            int add461count = tbmqq461Mapper.insertSelective(Tbmqq461);
            if (add461count != 1) {
                throw new NetPlusException("业务配置添加失败");
            }
        } else {
            throw new NetPlusException("业务已经存在，请重新填写信息");
        }
        return ApiResponse.success();
    }

    /**
     * 修改
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse updateServiceConfig(AddServiceConfigRequest request) {

        Date now = new Date();

        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        Tbmqq461 Tbmqq461 = new Tbmqq461();
        Tbmqq461.setCode(request.getCode());
        Tbmqq461.setName(request.getName());
        Tbmqq461.setVal(request.getVal());
        Tbmqq461.setUpdateuser(loginUserBean.getUserCode());
        Tbmqq461.setUpdatedate(DateUtil.format(now, "yyyyMMdd"));
        Tbmqq461.setUpdatetime(DateUtil.format(now, "HHmmss"));

        int update461count = tbmqq461Mapper.updateByPrimaryKeySelective(Tbmqq461);
        if (update461count != 1) {
            throw new NetPlusException("业务配置修改失败");
        }
        return ApiResponse.success();
    }

    /**
     * 删除
     *
     * @param request
     * @return
     */
    public ApiResponse deleteServiceConfig(AddServiceConfigRequest request) {

        String code=request.getCode();

        int count461 = tbmqq461Mapper.deleteByPrimaryKey(code);

        if ( count461 != 1) {
            throw new NetPlusException("业务配置删除失败");
        }
        return ApiResponse.success();
    }


    public Tbmqq461 getServiceConfigByCode (String code) {

        Map<String, Object> mapParam = new HashMap<String , Object>();

        mapParam.put("code", code);

        // 根据code查找业务配置表
        Tbmqq461 tbmqq461 = tbmqq461Mapper.getByCode(mapParam);
        if (ObjectUtil.isEmpty(tbmqq461)) {
            throw new NetPlusException("业务配置代码不存在");
        }

        return tbmqq461;

    }
}
