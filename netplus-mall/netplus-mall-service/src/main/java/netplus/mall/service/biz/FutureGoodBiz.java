package netplus.mall.service.biz;

import netplus.component.entity.enums.YesNoEnum;
import netplus.component.entity.iface.SysCodeEnum;
import netplus.mall.api.enums.GoodsStatusEnum;
import netplus.mall.api.pojo.ygmalluser.Futuregood;
import netplus.mall.api.pojo.ygmalluser.Tbmqq430;
import netplus.mall.service.dao.FuturegoodMapper;
import netplus.utils.date.NowDate;
import netplus.utils.pager.Pager;
import netplus.utils.uuid.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FutureGoodBiz {

    @Autowired
    private FuturegoodMapper futuregoodMapper;

    /**
     * 根据协议号删除中间表商品
     * @param poNoPK
     * @return
     */
    public int deleteFutureGoodByPoNoPk(String poNoPK){
        return futuregoodMapper.deleteFutureGoodByPoNoPk(poNoPK);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchImport(List<Tbmqq430> list) {
        //删除中间表中的旧数据
        String ponoPk = list.get(0).getPonopk();
        futuregoodMapper.deleteFutureGoodByPoNoPk(ponoPk);

        //构建需插入的对象
        NowDate nowDate = new NowDate();
        List<Futuregood> insertList = list.stream().map(e -> {
            Futuregood futuregood = new Futuregood();
            BeanUtils.copyProperties(e, futuregood);
            futuregood.setUpdatedate(nowDate.getDateStr());
            futuregood.setUpdatetime(nowDate.getTimeStr());
            futuregood.setUpdateuser(SysCodeEnum.CK.code);
            futuregood.setGoodid(UuidUtil.getUuid());
            futuregood.setCreatedate(nowDate.getDateStr());
            futuregood.setCreatetime(nowDate.getTimeStr());
            futuregood.setCreateuser(SysCodeEnum.CK.code);
            futuregood.setSourcefrom(SysCodeEnum.CK.code);
            futuregood.setDeleted(YesNoEnum.No.getValue());
            futuregood.setIstaxexception("ZC");
            futuregood.setStatus(GoodsStatusEnum.status_10.getCode());
            return futuregood;
        }).collect(Collectors.toList());

        //分批插入表中
        if(!ObjectUtils.isEmpty(insertList)){
            Pager<Futuregood> futuregoodPager = new Pager(insertList, 100);
            for (int i=1; i<=futuregoodPager.getTotalPage(); i++) {
                futuregoodMapper.batchInsert(futuregoodPager.getPageData(i));
            }
        }
    }

    public void batchInsert(List<Futuregood> list) {
        futuregoodMapper.batchInsert(list);
    }

}
