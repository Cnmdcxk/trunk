package netplus.mall.api.request.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class AfterApprovalRequest implements Serializable {
    private String businessId; //业务系统提交审批业务主键
    private String approveNodeIndex; //审批节点序号
    private String approveNodeStatus; //审批节点状态，Y：同意，N：驳回。
    private String approveNodeDate; //审批节点审批时间
    private String approveNodeOpinion; //审批节点意见
    private String approveStatus; //整个审批流程状态，F：流程结束，D：流程进行中，N，流程被驳回
    private String approveCode; //审批人工号
    private String approvePeople; //审批人姓名
}
