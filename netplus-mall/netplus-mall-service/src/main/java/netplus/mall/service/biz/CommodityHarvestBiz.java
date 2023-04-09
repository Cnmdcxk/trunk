package netplus.mall.service.biz;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.response.ApiResponse;
import netplus.mall.api.pojo.ygmalluser.Tbmqq439;
import netplus.mall.api.request.HarvesterManagement.CommodityHarvestRequest;
import netplus.mall.service.dao.Tbmqq439Mapper;
import netplus.utils.date.DateUtil;
import netplus.utils.date.NowDate;
import netplus.utils.object.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommodityHarvestBiz extends ServiceImpl<Tbmqq439Mapper, Tbmqq439> {
    @Autowired
    Tbmqq439Mapper tbmqq439Mapper;
    /*
    查询所有信息
     */
    public PageBean<Tbmqq439> getAllList(CommodityHarvestRequest request){
        // 通过放射方法将 传入参数转换成map
        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);

        //查询所有订单和总记录数
        List<Tbmqq439> allList = tbmqq439Mapper.getAllList(mapParam);
        int count = tbmqq439Mapper.getCount(mapParam);

        // 返回装填分页对象
        PageBean<Tbmqq439> pageBean = new PageBean();
        pageBean.setItems(allList);
        pageBean.setTotalCount(count);
        // 返回分页对象
        return pageBean;
    }

    //增加信息
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse addInformation(CommodityHarvestRequest request){
        // 当前时间
        Date now = new Date();

        // 通过code查询判断是否已存在收货地址
        Tbmqq439 tbmqq439List = tbmqq439Mapper.getByCode(request.getCode());
        if (ObjectUtil.isEmpty(tbmqq439List)) {
                NowDate nowDate = new NowDate();
                // 不存在时进入
                Tbmqq439 tbmqq439 = new Tbmqq439();
                // 设置字段值
                tbmqq439.setCode(request.getCode());
                tbmqq439.setProvince(request.getProvince());
                tbmqq439.setCity(request.getCity());
                tbmqq439.setConsigneeaddr(request.getConsigneeaddr());
                tbmqq439.setAddrtype(request.getAddrtype());
                tbmqq439.setCreatedate(nowDate.getDateStr());
                tbmqq439.setCreatetime(nowDate.getTimeStr());
                tbmqq439.setUpdatedate(nowDate.getDateStr());
                tbmqq439.setUpdatetime(nowDate.getTimeStr());
                // 执行添加
                int add439count = tbmqq439Mapper.insertSelective(tbmqq439);
                if (add439count != 1) {
                    throw new NetPlusException("地址数据添加失败");
                }
            } else {
                throw new NetPlusException("编码已经存在，请重新填写");
            }
        return ApiResponse.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResponse updateInformation(CommodityHarvestRequest request) {
        // 获取当前时间
        Date now = new Date();
        // 设置值
        Tbmqq439 tbmqq439 = new Tbmqq439();
        tbmqq439.setCode(request.getCode());
        tbmqq439.setProvince(request.getProvince());
        tbmqq439.setAddrtype(request.getAddrtype());
        tbmqq439.setCity(request.getCity());
        tbmqq439.setConsigneeaddr(request.getConsigneeaddr());
        tbmqq439.setUpdatedate(DateUtil.format(now, "yyyyMMdd"));
        tbmqq439.setUpdatetime(DateUtil.format(now, "HHmmss"));
        // 修改收货地址
        int modify439count = tbmqq439Mapper.updateByPrimaryKeySelective(tbmqq439);
        if(modify439count !=1){
            throw new NetPlusException("权限数据添加失败");
        }
        return ApiResponse.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResponse deleteByCode(CommodityHarvestRequest request) {
        // 通过code查询判断是否已存在收货地址
        Tbmqq439 tbmqq439 = tbmqq439Mapper.getByCode(request.getCode());
        if (tbmqq439 != null) {
            // 执行添加
            int del439count = tbmqq439Mapper.deleteByCode(request.getCode());
            if (del439count != 1) {
                throw new NetPlusException("地址数据删除失败");
            }
        } else {
            throw new NetPlusException("删除失败数据不存在");
        }
        return ApiResponse.success();
    }

}
