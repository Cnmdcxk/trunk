package netplus.joint.erp.api.response.out.JK0007;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
public class JK0007Response  implements Serializable{

  private String code;
  private String msg;
  private int total;
  private List<JK0007SubResponse> rows;

}