package netplus.mall.service.controller;

import lombok.extern.slf4j.Slf4j;
import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.mall.api.Urls;
import netplus.mall.api.pojo.ygmalluser.Tbmqq435;
import netplus.mall.api.request.GoodsPictureListRequest;
import netplus.mall.api.rest.GoodsPictureRest;
import netplus.mall.api.vo.Tbmqq435Vo;
import netplus.mall.service.biz.GoodsPictureBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 商品图片数据
 * @author: yxw
 * @date: 2021/7/1 15:21
 */
@RestController
@Slf4j
public class GoodsPictureController extends BasedController implements GoodsPictureRest {

    @Autowired
    private GoodsPictureBiz goodsPictureBiz;

    /*查询商品图片*/
    @PostMapping(Urls.GoodsPicture.getGoodsPictureList)
    public List<Tbmqq435Vo> getGoodPicList(@RequestBody GoodsPictureListRequest request) {
        return goodsPictureBiz.getGoodPicList(request);
    }

    @Override
    @Anonymous
    @PostMapping(Urls.GoodsPicture.addGoodsPictureList)
    public int addGoodsPictureList(@RequestBody List<Tbmqq435> request) {
       return goodsPictureBiz.addGoodsPictureList(request);
    }
}
