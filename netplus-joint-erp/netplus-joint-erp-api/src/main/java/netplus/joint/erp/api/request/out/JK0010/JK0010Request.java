package netplus.joint.erp.api.request.out.JK0010;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class JK0010Request implements Serializable {

     private  List<JK0010SubRequest> data;

}
