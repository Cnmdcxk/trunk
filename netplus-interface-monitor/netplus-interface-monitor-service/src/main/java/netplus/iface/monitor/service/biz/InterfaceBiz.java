package netplus.iface.monitor.service.biz;

import com.google.gson.Gson;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.iface.BaseResponse;
import netplus.component.entity.iface.SysCodeEnum;
import netplus.component.http.EasyHttpClient;
import netplus.component.http.HttpApiRequest;
import netplus.component.http.HttpApiResponse;
import netplus.iface.monitor.api.pojo.db.Tbmqq460Key;
import netplus.iface.monitor.api.pojo.db.Tbmqq460WithBLOBs;
import netplus.iface.monitor.api.request.InterFaceRequest;
import netplus.iface.monitor.api.request.RestartReqRequest;
import netplus.iface.monitor.api.vo.Tbmqq460Vo;
import netplus.iface.monitor.service.dao.Tbmqq460Mapper;
import netplus.utils.date.DateUtil;
import netplus.utils.object.ObjectUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class InterfaceBiz {


    private static Log logger = LogFactory.getLog(InterfaceBiz.class);

    /*
    获取接口管理的集合
    * */

    @Autowired
    EasyHttpClient easyHttpClient;


    @Autowired
    Tbmqq460Mapper tbmqq460Mapper;
        public PageBean<Tbmqq460Vo> getInterFaceDataList(InterFaceRequest request){

            Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
            //获取全部数据
            List<Tbmqq460Vo> InterFaceDataList = tbmqq460Mapper.getInterFaceDataList(mapParam);
            //获取总页数
            int count = tbmqq460Mapper.getCount(mapParam);
            //将求得的总页数值返还给前端页面

//            List<KeyValue> callerKV = tbmqq460Mapper.getCallerKV(mapParam);
//            List<KeyValue> calleeKV = tbmqq460Mapper.getCalleeKV(mapParam);
//            List<KeyValue> reqNameKV = tbmqq460Mapper.getReqNameKV(mapParam);

            PageBean<Tbmqq460Vo> pageBean = new PageBean();
            pageBean.setTotalCount(count);
            pageBean.setItems(InterFaceDataList);
//            pageBean.addResultMap("callerKV",callerKV);
//            pageBean.addResultMap("calleeKV",calleeKV);
//            pageBean.addResultMap("reqNameKV",reqNameKV);
            return pageBean;
    }


    public Tbmqq460Vo getRespData(String id){

            return tbmqq460Mapper.selectRespData(id);
    }

    public void restartReq (RestartReqRequest request) {


        Tbmqq460Key tbmqq460Key = new Tbmqq460Key();
        tbmqq460Key.setReqid(request.getReqId());
        tbmqq460Key.setCaller(request.getCaller());
        tbmqq460Key.setCallee(request.getCallee());

        Tbmqq460WithBLOBs tbmqq460 = tbmqq460Mapper.selectByPrimaryKey(tbmqq460Key);
        if (ObjectUtil.nonEmpty(tbmqq460)) {

            if (tbmqq460.getStatus().equals("OK")) {
                throw new NetPlusException("请求已处理成功");
            }

            if (tbmqq460.getTimes().intValue() >= 10) {
                throw new NetPlusException("请求已重新处理10次都失败，请联系系统管理员！");
            }


            String reqData  = setIsRestart(tbmqq460.getReqdata());

            HttpApiRequest httpApiRequest = new HttpApiRequest();
            httpApiRequest.setContent(reqData);
            httpApiRequest.setUrl(tbmqq460.getRequrl());
            httpApiRequest.setApiName(tbmqq460.getReqname());
            HttpApiResponse httpApiResponse = easyHttpClient.post(httpApiRequest);


            Tbmqq460WithBLOBs t = new Tbmqq460WithBLOBs();
            t.setReqid(request.getReqId());
            t.setCallee(request.getCallee());
            t.setCaller(request.getCaller());
            t.setResptime(DateUtil.getTimeStampStr());
            t.setRespdata(httpApiResponse.getData());


            if (httpApiResponse.getStatus().equals(200)) {

                try {


                    logger.info(String.format("httpApiResponse.getData: %s", httpApiResponse.getData()));

                    if (SysCodeEnum.ZKH.code.equals(tbmqq460.getCallee())) {

                        netplus.joint.zkh.api.response.BaseResponse baseResponse = new Gson().fromJson(httpApiResponse.getData(), netplus.joint.zkh.api.response.BaseResponse.class);
                        if (baseResponse.getSuccess()) {
                            t.setStatus("OK");
                        }else{
                            t.setTimes(Short.parseShort(String.valueOf(tbmqq460.getTimes().intValue() + 1)));
                        }

                    }else{


                        BaseResponse baseResponse = new Gson().fromJson(httpApiResponse.getData(), BaseResponse.class);

                        if (baseResponse.getStatus().equals("1")) {
                            t.setStatus("OK");
                        }else{
                            t.setTimes(Short.parseShort(String.valueOf(tbmqq460.getTimes().intValue() + 1)));
                        }

                    }

                }catch (Exception e) {
                    logger.info(String.format("重发失败: %s", ExceptionUtils.getFullStackTrace(e)));
                }


                logger.info(String.format("tbmqq460Mapper.updateByPrimaryKeySelective........."));
                tbmqq460Mapper.updateByPrimaryKeySelective(t);

            }else{

                throw new NetPlusException(String.format("重发失败: %s",  new Gson().toJson(httpApiResponse)));
            }

        }else{

            throw new NetPlusException("请求id不存在");
        }

    }


    private String setIsRestart (String json) {

        Map<String, Object> map = new Gson().fromJson(json, Map.class);
        map.put("isRestart", "Y");

        return new Gson().toJson(map);


    }
    public int deleteInterFaceInformation() {

        Long currentTimestamp=System.currentTimeMillis();
        Long beforeOneMonth=currentTimestamp - 30 * 24 * 3600 * 1000L;
        return tbmqq460Mapper.deleteInformation(beforeOneMonth);

    }



}
