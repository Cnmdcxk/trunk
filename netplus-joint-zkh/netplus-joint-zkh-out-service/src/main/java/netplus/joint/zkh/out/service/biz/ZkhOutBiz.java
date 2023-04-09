package netplus.joint.zkh.out.service.biz;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.http.EasyHttpClient;
import netplus.component.http.HttpApiResponse;
import netplus.joint.zkh.api.pojo.*;
import netplus.joint.zkh.api.request.out.*;
import netplus.joint.zkh.api.response.BaseResponse;
import netplus.provider.api.pojo.ygmalluser.Tbdu01;
import netplus.provider.api.rest.CommonRest;
import netplus.utils.json.JsonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ZkhOutBiz {

    public static final Gson gson = JsonUtil.JsonInstance;

    @Autowired
    EasyHttpClient easyHttpClient;

    @Autowired
    ZkhMessageBiz zkhMessageBiz;

    @Autowired
    ZkhTokenBiz zkhTokenBiz;

    @Autowired
    CommonRest commonRest;

    @Value("${zkh.clientId}")
    private String clientId;

    @Value("${zkh.clientSecret}")
    private String clientSecret;


    public Tbdu01 getZkhSupplierNo () {

        Tbdu01 tbmqg001 = new Tbdu01();
        return tbmqg001;

    }

    //获取zkh商品详情
    public GoodsDetailVo getGoodsDetails(String sku) {

        GoodsRequest goodsRequest = new GoodsRequest();
        goodsRequest.setSku(sku);
        goodsRequest.setToken(zkhTokenBiz.getAccessToken());

        HttpApiResponse httpApiResponse = zkhTokenBiz.getHttpApiResponse("getDetail", "getDetail", gson.toJson(goodsRequest));
        Type type =  new TypeToken<BaseResponse<GoodsDetailVo>>() {}.getType();
        BaseResponse<GoodsDetailVo> response = gson.fromJson(httpApiResponse.getData(), type);

        if (!response.getSuccess()) {
            throw new NetPlusException(response.getResultMessage());
        }

        return response.getResult();
    }



    //得到商品图片详情
    public List<GoodsImageVo> getGoodsImage(List<String> sku) {
        //设置请求参数
        GoodsListRequest goodsImageRequest = new GoodsListRequest();
        goodsImageRequest.setSku(sku);
        goodsImageRequest.setToken(zkhTokenBiz.getAccessToken());

        //访问震坤行请求
        HttpApiResponse httpApiResponse = zkhTokenBiz.getHttpApiResponse("skuImage", "skuImage", gson.toJson(goodsImageRequest));

        BaseResponse<List<GoodsImageVo>> response = gson.fromJson(httpApiResponse.getData(),
                new TypeToken<BaseResponse<List<GoodsImageVo>>>() {
                }.getType());

        if (!response.getSuccess()) {
            throw new NetPlusException(response.getResultMessage());
        }
        return response.getResult();
    }

    //批量查询商品库存
    public List<GoodsInventoryVo> getProductInventory(List<String> sku) {
        List<GoodsInventory> goodsInventories = new ArrayList<>();
        for (String s : sku) {
            GoodsInventory goodsInventorie = new GoodsInventory();
            goodsInventorie.setSkuId(s);
            goodsInventorie.setNum(0);
            goodsInventories.add(goodsInventorie);
        }

        GoodsInventoryRequest request = new GoodsInventoryRequest();

        request.setToken(zkhTokenBiz.getAccessToken());
        request.setSkuNums(goodsInventories);

        HttpApiResponse httpApiResponse = zkhTokenBiz.getHttpApiResponse("product/inventory", "inventory", gson.toJson(request));
        BaseResponse<List<GoodsInventoryVo>> response = gson.fromJson(httpApiResponse.getData(),
                new TypeToken<BaseResponse<List<GoodsInventoryVo>>>() {
                }.getType());

        if (!response.getSuccess()) {
            throw new NetPlusException(response.getResultMessage());
        }
        return response.getResult();
    }

    //获取商品上下架状态
    public List<GoodsStatusVo> getGoodsState(List<String> sku) {

        GoodsListRequest goodsStateRequest = new GoodsListRequest();
        goodsStateRequest.setSku(sku);
        goodsStateRequest.setToken(zkhTokenBiz.getAccessToken());

        HttpApiResponse httpApiResponse = zkhTokenBiz.getHttpApiResponse("skuState", "skuState", gson.toJson(goodsStateRequest));

        Type type = new TypeToken<BaseResponse<List<GoodsStatusVo>>>() {}.getType();
        BaseResponse<List<GoodsStatusVo>> response = gson.fromJson(httpApiResponse.getData(), type);

        if (!response.getSuccess()) {
            throw new NetPlusException(response.getResultMessage());
        }
        return response.getResult();
    }

    //获取商品价格
    public List<GoodsPriceVo> getSellPrice(List<String> sku) {
        //设置请求参数
        GoodsListRequest goodsPriceRequest = new GoodsListRequest();
        goodsPriceRequest.setSku(sku);
        goodsPriceRequest.setToken(zkhTokenBiz.getAccessToken());
        //访问震坤行accessToken请求
        HttpApiResponse httpApiResponse = zkhTokenBiz.getHttpApiResponse("getSellPrice", "getSellPrice", gson.toJson(goodsPriceRequest));

        BaseResponse<List<GoodsPriceVo>> response = gson.fromJson(httpApiResponse.getData(),
                new TypeToken<BaseResponse<List<GoodsPriceVo>>>() {
                }.getType());

        if (!response.getSuccess()) {
            throw new NetPlusException(response.getResultMessage());
        }
        return response.getResult();
    }

    //商品状态反向同步
    public void getGoodsThirdState(List<SkuThirdState> skuThirdStates) {
        //设置请求参数
        GoodsThirdStateRequest request = new GoodsThirdStateRequest();
        request.setToken(zkhTokenBiz.getAccessToken());
        request.setSkuThirdState(skuThirdStates);

        //访问震坤行accessToken请求
        HttpApiResponse httpApiResponse = zkhTokenBiz.getHttpApiResponse("thirdState", "thirdState", gson.toJson(request));
        BaseResponse response = gson.fromJson(httpApiResponse.getData(), BaseResponse.class);

        if (!response.getSuccess()) {
            throw new NetPlusException(response.getResultMessage());
        }
    }

    //统一下单
    public SubmitOrderVo submitOrder(SubmitOrderRequest request) {
        //设置请求参数
        request.setToken(zkhTokenBiz.getAccessToken());
        request.setPurchaseAccount(clientId);
        request.setPurchaseMobile(clientId);

        //访问震坤行accessToken请求
        HttpApiResponse httpApiResponse = zkhTokenBiz.getHttpApiResponse("submitOrder", "submitOrder", gson.toJson(request));

        BaseResponse<SubmitOrderVo> response = gson.fromJson(httpApiResponse.getData(),
                new TypeToken<BaseResponse<SubmitOrderVo>>() {
                }.getType());

        if (!response.getSuccess()) {
            throw new NetPlusException(response.getResultMessage());
        }
        return response.getResult();
    }

    //确认订单
    public void confirmOrder(String orderId, String referenceNumber) {
        //设置请求参数
        OrderRequest request = new OrderRequest();
        request.setToken(zkhTokenBiz.getAccessToken());
        request.setOrderId(orderId);
        request.setReferenceNumber(referenceNumber);

        //访问震坤行accessToken请求
        HttpApiResponse httpApiResponse = zkhTokenBiz.getHttpApiResponse("confirmOrder", "confirmOrder", gson.toJson(request));

        BaseResponse response = gson.fromJson(httpApiResponse.getData(),
                new TypeToken<BaseResponse>() {
                }.getType());

        if (!response.getSuccess()) {
            throw new NetPlusException(response.getResultMessage());
        }
    }

    //取消订单
    public void cancelOrder(String orderId) {
        //设置请求参数
        OrderRequest request = new OrderRequest();
        request.setToken(zkhTokenBiz.getAccessToken());
        request.setOrderId(orderId);

        //访问震坤行accessToken请求
        HttpApiResponse httpApiResponse = zkhTokenBiz.getHttpApiResponse("cancel", "cancel", gson.toJson(request));

        BaseResponse response = gson.fromJson(httpApiResponse.getData(),
                new TypeToken<BaseResponse>() {
                }.getType());

        if (!response.getSuccess()) {
            throw new NetPlusException(response.getResultMessage());
        }
    }

    //取消订单部分明细
    public void itemCancel(String orderId, List<String> skuNoList) {
        //设置请求参数
        OrderRequest request = new OrderRequest();
        request.setToken(zkhTokenBiz.getAccessToken());
        request.setOrderId(orderId);
        request.setSkuNoList(skuNoList);

        //访问震坤行accessToken请求
        HttpApiResponse httpApiResponse = zkhTokenBiz.getHttpApiResponse("item/cancel", "zkh-itemCancel", gson.toJson(request));

        BaseResponse response = gson.fromJson(httpApiResponse.getData(),
                new TypeToken<BaseResponse>() {
                }.getType());

        if (!response.getSuccess()) {
            throw new NetPlusException(response.getResultMessage());
        }
    }

    //订单完结通知接⼝
    public void orderCompletion(String orderId) {
        //设置请求参数
        OrderRequest request = new OrderRequest();
        request.setToken(zkhTokenBiz.getAccessToken());
        request.setOrderId(orderId);

        //访问震坤行accessToken请求
        HttpApiResponse httpApiResponse = zkhTokenBiz.getHttpApiResponse("orderCompletion", "orderCompletion", gson.toJson(request));

        BaseResponse response = gson.fromJson(httpApiResponse.getData(),
                new TypeToken<BaseResponse>() {
                }.getType());

        if (!response.getSuccess()) {
            throw new NetPlusException(response.getResultMessage());
        }
    }

    //售后单申请
    public String serviceOrder(ServiceOrderRequest request) {
        //设置请求参数
        request.setToken(zkhTokenBiz.getAccessToken());

        //访问震坤行accessToken请求
        HttpApiResponse httpApiResponse = zkhTokenBiz.getHttpApiResponse("serviceOrder", "serviceOrder", gson.toJson(request));

        BaseResponse<String> response = gson.fromJson(httpApiResponse.getData(),
                new TypeToken<BaseResponse<String>>() {
                }.getType());

        if (!response.getSuccess()) {
            throw new NetPlusException(response.getResultMessage());
        }
        return response.getResult();
    }

    //查询发货单物流轨迹信息接⼝
    public PackageVo orderTrack(String packageId) {
        //设置请求参数
        PackageRequest request = new PackageRequest();
        request.setToken(zkhTokenBiz.getAccessToken());
        request.setPackageId(packageId);

        //访问震坤行accessToken请求
        HttpApiResponse httpApiResponse = zkhTokenBiz.getHttpApiResponse("orderTrack", "orderTrack", gson.toJson(request));

        BaseResponse<PackageVo> response = gson.fromJson(httpApiResponse.getData(),
                new TypeToken<BaseResponse<PackageVo>>() {
                }.getType());

        if (!response.getSuccess()) {
            throw new NetPlusException(response.getResultMessage());
        }

        return response.getResult();
    }
}
