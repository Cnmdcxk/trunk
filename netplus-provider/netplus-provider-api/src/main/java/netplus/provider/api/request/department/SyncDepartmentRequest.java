package netplus.provider.api.request.department;

import lombok.Getter;
import lombok.Setter;
import netplus.provider.api.pojo.ygmalluser.Department;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class SyncDepartmentRequest implements Serializable {

    private List<Department> departmentList;
}
