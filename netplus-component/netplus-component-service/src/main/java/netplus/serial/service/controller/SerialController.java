package netplus.serial.service.controller;

import io.swagger.annotations.Api;
import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.AccessAnonymous;
import netplus.component.entity.auth.Anonymous;
import netplus.serial.api.request.SerialParam;
import netplus.serial.api.rest.SerialConstants;
import netplus.serial.api.rest.SerialRest;
import netplus.serial.service.biz.SerialBiz;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "serial接口文档")
@RestController
public class SerialController extends BasedController implements SerialRest {

    private static Log logger = LogFactory.getLog(SerialController.class);

    @Autowired
    private SerialBiz serialBiz;

    /**
     * core
     */
    @Anonymous
    @AccessAnonymous
    @PostMapping(SerialConstants.core)
    public String core(@RequestBody @Validated SerialParam serialParam) {
        return serialBiz.getSerial(serialParam);
    }

    /**
     * get
     */
    @Anonymous
    @AccessAnonymous
    @PostMapping(SerialConstants.get)
    public String get(@RequestBody String prefix) {
        SerialParam param = new SerialParam(prefix, getAppID());
        return serialBiz.getSerial(param);
    }


    @Anonymous
    @AccessAnonymous
    @PostMapping(SerialConstants.getRg)
    public String getRg(@RequestBody String prefix) {
        SerialParam param = new SerialParam(prefix, getAppID());
        return serialBiz.getRg(param);
    }

}
