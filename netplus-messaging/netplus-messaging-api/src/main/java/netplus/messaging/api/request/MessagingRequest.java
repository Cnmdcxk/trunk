package netplus.messaging.api.request;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.request.RequestBase;

import java.util.List;

/**
 * Created by sy on 2020/12/30.
 */
@Getter
@Setter
public class MessagingRequest extends RequestBase{
    private List<String> typeList;
    private String createTime;
    private String workItemState;
    private String keyFieldName;
}
