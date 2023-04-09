package netplus.mall.service.biz;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.response.ApiResponse;
import netplus.mall.api.enums.AdvertisingStatusEnum;
import netplus.mall.api.pojo.ygmalluser.Advertising;
import netplus.mall.api.request.advertising.AddAdvertisingRequest;
import netplus.mall.api.request.advertising.ChangeAdvertisingStatusRequest;
import netplus.mall.api.request.advertising.MoveAdvertisingRequest;
import netplus.mall.api.request.advertising.RemoveAdvertisingRequest;
import netplus.mall.service.dao.AdvertisingMapper;
import netplus.utils.date.NowDate;
import netplus.utils.uuid.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AdvertisingBiz extends ServiceImpl<AdvertisingMapper, Advertising> {

    @Autowired
    AdvertisingMapper advertisingMapper;

    /**
     * 查询广告列表
     * @return
     */
    public ApiResponse listAdvertising(){
        List<Advertising> list=advertisingMapper.listAdvertising();
        return ApiResponse.success(list);
    }

    public ApiResponse getPublishAdvertising(){
        List<Advertising> list=advertisingMapper.listAdvertisingByStatus(AdvertisingStatusEnum.PUBLISHED.getCode());
        return ApiResponse.success(list);
    }

    public ApiResponse getPublishAdvertisingCount() {
        int count = advertisingMapper.getAdvertisingCountByStatus(AdvertisingStatusEnum.PUBLISHED.getCode());
        return ApiResponse.success(count);
    }

    /**
     * 新增广告
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse addAdvertising(AddAdvertisingRequest request) {

        if(StringUtils.isEmpty(request.getImagePath())){
            throw new NetPlusException("广告图片不能为空");
        }
        if(StringUtils.isEmpty(request.getImageName())){
            throw new NetPlusException("广告名称不能为空");
        }

        NowDate nowDate = new NowDate();
        Advertising advertising = new Advertising();

        advertising.setImagename(request.getImageName());
        advertising.setImagepath(request.getImagePath());
        //设置图片ID
        advertising.setId(UuidUtil.getUuid());
        advertising.setStatus(AdvertisingStatusEnum.UNPUBLISHED.getCode());

        advertising.setCreatedate(nowDate.getDateStr());
        advertising.setCreatetime(nowDate.getTimeStr());

        //TODO:创建人需从登录用户获取
        advertising.setCreateuser("admin");

        //获取最大的排序
        List<Integer> statuses = new ArrayList<>();
        statuses.add(AdvertisingStatusEnum.PUBLISHED.getCode());
        statuses.add(AdvertisingStatusEnum.UNPUBLISHED.getCode());
        Integer num=advertisingMapper.getAdvertisingMaxOrder(statuses);
        advertising.setNum(++num);

        int insertCount = advertisingMapper.insertSelective(advertising);
        if(insertCount != 1){
            throw new NetPlusException("新增失败,请重试!");
        }
        return ApiResponse.success();
    }

    /**
     * 发布广告
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse publishAdvertising(ChangeAdvertisingStatusRequest request){
        if(request.getIds() == null || request.getIds().size() == 0){
            throw new NetPlusException("广告ID不能为空");
        }
        int count = request.getIds().size();
        if(count > 6){
            throw new NetPlusException("最多发布6条广告!");
        }
        count += advertisingMapper.getAdvertisingCountByStatus(AdvertisingStatusEnum.PUBLISHED.getCode());
        if(count > 6){
            throw new NetPlusException("最多发布6条广告!");
        }

        changeAdvertisingStatus(request, AdvertisingStatusEnum.PUBLISHED);
        return ApiResponse.success();
    }

    /**
     * 取消发布广告
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse cancelPublishAdvertising(ChangeAdvertisingStatusRequest request){
        changeAdvertisingStatus(request, AdvertisingStatusEnum.UNPUBLISHED);
        return ApiResponse.success();
    }

    /**
     * 设置广告发布状态
     * @param request
     * @param status
     */
    private void changeAdvertisingStatus(ChangeAdvertisingStatusRequest request, AdvertisingStatusEnum status) {
        if(request.getIds() == null || request.getIds().size() == 0){
            throw new NetPlusException("广告ID不能为空");
        }

        List<Advertising> advertisingList = advertisingMapper.selectByIds(request.getIds());
        if(advertisingList == null || advertisingList.size() == 0){
            throw new NetPlusException("广告不存在!");
        }

        if(advertisingList.size() != request.getIds().size()){
            throw new NetPlusException("部分广告发生变化,修改失败,请刷新后重试!");
        }

        int code = status.getCode();
        NowDate nowDate = new NowDate();

        Integer maxOrder = advertisingMapper.getAdvertisingMaxOrder(Arrays.asList(AdvertisingStatusEnum.PUBLISHED.getCode()));
        advertisingMapper.moveNumBack(maxOrder,advertisingList.size());
        for (Advertising item : advertisingList) {
            if(code == item.getStatus()){
                throw new NetPlusException(String.format("广告 %s 已经是 %s 状态", item.getImagename(), status.getName()));
            }

            Advertising advertising = new Advertising();
            advertising.setId(item.getId());
            advertising.setStatus(code);
            advertising.setNum(++maxOrder);

            advertising.setUpdatedate(nowDate.getDateStr());
            advertising.setUpdatetime(nowDate.getTimeStr());

            //TODO:修改人需从登录用户获取
            advertising.setUpdateuser("admin");
            int updateResult = advertisingMapper.updateByIdAndStatus(advertising);
            if(updateResult != 1){
                throw new NetPlusException("部分广告发生变化,修改失败,请刷新后重试!");
            }
        }
    }

    /**
     * 删除广告
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse removeAdvertising(RemoveAdvertisingRequest request){
        if(request.getIds() == null || request.getIds().size() == 0){
            throw new NetPlusException("广告ID不能为空");
        }

        List<Advertising> advertisingList = advertisingMapper.selectByIds(request.getIds());

        if(advertisingList.size() != request.getIds().size()){
            throw new NetPlusException("部分广告发生变化,删除失败,请刷新后重试!");
        }

        List<String> ids = advertisingList.stream().map(item -> {
            if (AdvertisingStatusEnum.PUBLISHED.getCode() == item.getStatus()) {
                throw new NetPlusException(String.format("广告 %s 已经发布,不能删除!", item.getImagename()));
            }

            return item.getId();
        }).collect(Collectors.toList());

        //TODO:删除图片文件

        int deleteCount = advertisingMapper.deleteByIds(ids);
        if(deleteCount != ids.size()){
            throw new NetPlusException("部分广告发生变化,删除失败,请刷新后重试!");
        }

        return ApiResponse.success();
    }

    /**
     * 广告左移一位
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse advertisingLeftMoveOne(MoveAdvertisingRequest request){
        Function<Integer,Advertising> leftFunction = (num) -> {
            Advertising leftAdvertising = advertisingMapper.getLeftPublishedAdvertising(num);
            if(leftAdvertising == null){
                throw new NetPlusException("广告左侧已经没有广告了,无法左移!");
            }
            return leftAdvertising;
        };
        return moveAdvertising(request,leftFunction);
    }

    /**
     * 广告右移一位
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse advertisingRightMoveOne(MoveAdvertisingRequest request){
        Function<Integer,Advertising> rightFunction = (num) -> {
            Advertising rightAdvertising = advertisingMapper.getRightPublishedAdvertising(num);
            if(ObjectUtils.isEmpty(rightAdvertising)){
                throw new NetPlusException("广告右侧已经没有已发布的广告了,无法右移!");
            }
            return rightAdvertising;
        };
        return moveAdvertising(request,rightFunction);
    }

    /**
     * 移动广告
     * @param request
     * @param advertisingFunction
     * @return
     */
    private ApiResponse moveAdvertising(MoveAdvertisingRequest request, Function<Integer,Advertising> advertisingFunction){
        if(StringUtils.isEmpty(request.getId())){
            throw new NetPlusException("广告ID不能为空");
        }
        Advertising advertising = advertisingMapper.selectByPrimaryKey(request.getId());
        if(AdvertisingStatusEnum.UNPUBLISHED.getCode() == advertising.getStatus()){
            throw new NetPlusException("广告未发布,无需移动!");
        }

        Integer num = advertising.getNum();
        Advertising leftAdvertising = advertisingFunction.apply(num);

        //序号互换
        NowDate nowDate = new NowDate();
        advertising.setNum(leftAdvertising.getNum());
        leftAdvertising.setNum(num);
        advertising.setUpdatedate(nowDate.getDateStr());
        advertising.setUpdatetime(nowDate.getTimeStr());

        //TODO:修改人需从登录用户获取
        advertising.setUpdateuser("admin");

        int updateCount = advertisingMapper.updateByPrimaryKeySelective(leftAdvertising);
        if(updateCount != 1){
            throw new NetPlusException("部分广告发生变化,修改失败,请刷新后重试!");
        }
        updateCount = advertisingMapper.updateByPrimaryKeySelective(advertising);
        if(updateCount != 1){
            throw new NetPlusException("部分广告发生变化,修改失败,请刷新后重试!");
        }
        return ApiResponse.success();
    }
}
