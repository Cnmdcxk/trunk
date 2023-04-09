package netplus.joint.zkh.api.request.out;

import lombok.Data;

@Data
public class InvoiceInfo {

    //发票类型
    private int type;

    //发票内容
    private String content;

    //发票抬头
    private String title;

    //纳税⼈识别号
    private String enterpriseTaxpayer;

    //开户⾏
    private String bank;

    //开户⾏银⾏账户
    private String account;

    //开户⾏银⾏电话
    private String tel;

    //开户⾏银⾏地址
    private String address;
}

