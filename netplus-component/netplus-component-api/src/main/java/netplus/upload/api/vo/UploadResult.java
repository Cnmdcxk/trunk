package netplus.upload.api.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadResult {

    private Integer code;
    private String url;
    private String batchCode;

    // 兼容ueditor
    private String error;
    private String state;

}
