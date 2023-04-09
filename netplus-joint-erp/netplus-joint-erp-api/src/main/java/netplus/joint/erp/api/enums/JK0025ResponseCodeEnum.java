package netplus.joint.erp.api.enums;

public enum JK0025ResponseCodeEnum {

    UNKNOWN_ERROR("UNKNOWN_ERROR","未知异常"),
    OTHER_ERROR("OTHER_ERROR","其他异常"),
    REQUEST_PARAM_ERROR("REQUEST_PARAM_ERROR","请求参数错误"),
    INTERFACE_RESP_DATA_NULL("INTERFACE_RESP_DATA_NULL","接口返回信息为空"),
    USER_NOT_EXISTS("USER_NOT_EXISTS","用户不存在");


    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    JK0025ResponseCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
