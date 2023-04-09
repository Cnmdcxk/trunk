package netplus.provider.api.enums;

public enum EnumLoginCode {
    OK("OK", "登录成功"),
    updOK("OK", "修改密码成功"),
    updNotExists("NotExists", "无修改密码数据"),
    NotExists("NotExists", "用户未找到"),
    TooManyRows("TooManyRows", "找到多个用户信息"),
    InvalidPassword("InvalidPassword", "用户密码不正确"),
    NoImgCode("NoImgCode", "未提供图片验证码"),
    InvalidImgCode("InvalidImgCode", "图片验证码不正确"),
    UserInActive("UserInActive", "用户被禁用"),
    CompanyInActive("CompanyInActive", "公司被禁用"),
    ProviderNotSync("ProviderNotSync", "供应商未同步"),
    LoginMethodErr("LoginMethodErr", "登录方式不存在"),
    ConsigneeDisallowLogin("ConsigneeDisallowLogin", "收货人不允许登录");


    private String value;
    private String desc;

    private EnumLoginCode(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return this.value;
    }

    public String getDesc() {
        return this.desc;
    }
}
