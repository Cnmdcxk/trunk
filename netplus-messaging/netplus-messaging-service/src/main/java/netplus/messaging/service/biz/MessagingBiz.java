package netplus.messaging.service.biz;

import netplus.component.entity.data.KeyValue;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.enums.MsgTypeEnum;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.messaging.api.pojo.ygmalluser.Messaging;
import netplus.messaging.api.request.MessagingRequest;
import netplus.messaging.api.request.SendMsgRequest;
import netplus.messaging.api.vo.MessagingVo;
import netplus.messaging.api.vo.NumCountVo;
import netplus.messaging.service.dao.MessagingMapper;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.vo.LoginUserBean;
import netplus.utils.date.NowDate;
import netplus.utils.object.ObjectUtil;
import netplus.utils.uuid.UuidUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by sy on 2020/12/30.
 */
@Service
public class MessagingBiz {

    protected Log logger = LogFactory.getLog(MessagingBiz.class);


    @Autowired
    IdentityRest identityRest;

    @Autowired
    MessagingMapper messagingMapper;

    public PageBean<MessagingVo> query(MessagingRequest messagingRequest){
        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        Map<String, Object> params = ObjectUtil.transBeanToMap(messagingRequest);
        params.put("userid", loginUserBean.getUserCode());

        List<MessagingVo> items = messagingMapper.querPage(params);

        //将消息类型由枚举值改为名字
        Map<Integer, String> typeNameMap = Arrays.stream(MsgTypeEnum.values()).collect(Collectors.toMap(MsgTypeEnum::getTypeCode, MsgTypeEnum::getTypeName, (v1, v2) -> v2));
        List<MessagingVo> list = items.stream().map(item -> {
            item.setMessagetypeStr(typeNameMap.get(item.getMessagetype()));
            return item;
        }).collect(Collectors.toList());

        Integer totalCount = messagingMapper.listPageCount(params);

        PageBean<MessagingVo> result = new PageBean<>();
        result.setItems(list);
        result.setTotalCount(totalCount);
        return result;
    }

    public NumCountVo getAllCount(){
        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> params = new HashMap<>();
        params.put("userid", loginUserBean.getUserCode());
        return messagingMapper.getAllCount(params);
    }

    public Map<String, String> getCountWithGroup() {
        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> params = new HashMap<>();
        params.put("userid", loginUserBean.getUserCode());
        List<KeyValue> countWithGroupList=messagingMapper.getCountWithGroup(params);
        Map<String, String> countWithGroupMap = countWithGroupList.stream().collect(Collectors.toMap(KeyValue::getKey, KeyValue::getValue, (k1, k2) -> k2));
        return countWithGroupMap;
    }

    public int updateState(String id){
        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> params =new HashMap<>();
        params.put("id",id);
        params.put("userid", loginUserBean.getUserCode());
        return messagingMapper.updateState(params);
    }

    public int updateAllState() {
        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        return messagingMapper.updateAllState(loginUserBean.getUserCode());
    }

    @Transactional(rollbackFor = Exception.class)
    public void sendMsg(List<SendMsgRequest> request) {
        if(ObjectUtils.isEmpty(request)){
            throw new NetPlusException("消息不能为空");
        }
        NowDate nowDate = new NowDate();
        List<Messaging> messagingList = request.stream().map(req -> {
            if (StringUtils.isEmpty(req.getSendUserNo())) {
                throw new NetPlusException("发送人不能为空");
            }

            if (StringUtils.isEmpty(req.getReceiveUserNo())) {
                throw new NetPlusException("接收人不能为空");
            }

            MsgTypeEnum type = req.getMsgType();
            if (ObjectUtils.isEmpty(type)) {
                throw new NetPlusException("消息类型不能为空");
            }

            if (type.isNeedUrl() && StringUtils.isEmpty(req.getUrl())) {
                throw new NetPlusException("url不能为空");
            }

            Messaging messaging = new Messaging();
            messaging.setId(UuidUtil.getUuid());
            messaging.setSenduserno(req.getSendUserNo());
            messaging.setReceiveuserno(req.getReceiveUserNo());
            messaging.setMessagetype(type.getTypeCode());
            messaging.setMessagegroup(type.getGroup().getGroupCode());
            messaging.setIsread(0);
            messaging.setMessagecontent(type.getContent(req.getUrl(),req.getParams()));

            messaging.setReceivetime(nowDate.getDateTimeStr2());
            messaging.setUrl(req.getUrl());
            return messaging;
        }).collect(Collectors.toList());

        List<String> receiveUserNoList = messagingList.stream().map(Messaging::getReceiveuserno).distinct().collect(Collectors.toList());
        //TODO:校验接收人是否存在

        int insertCount=messagingMapper.batchInsert(messagingList);
        if(insertCount != messagingList.size()){
            throw new NetPlusException("创建消息失败,请重试!");
        }
    }

    public List<KeyValue> getMyMsgGroupList() {
        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        Map<String, String> groupMap = Arrays.stream(MsgTypeEnum.MsgGroupEnum.values()).collect(Collectors.toMap(MsgTypeEnum.MsgGroupEnum::getGroupCode, MsgTypeEnum.MsgGroupEnum::getGroupName));
        List<String> goups=messagingMapper.getMsgGroupByUserId(loginUserBean.getUserCode());
        List<KeyValue> myMsgGroupList = goups.stream().map(groupCode -> {
            KeyValue keyValue = new KeyValue();
            keyValue.setKey(groupCode);
            keyValue.setValue(groupMap.get(groupCode));
            return keyValue;
        }).filter(kv -> !StringUtils.isEmpty(kv)).collect(Collectors.toList());
        return myMsgGroupList;
    }
}
