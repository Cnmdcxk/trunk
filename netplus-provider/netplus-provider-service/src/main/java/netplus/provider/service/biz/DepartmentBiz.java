package netplus.provider.service.biz;

import lombok.extern.slf4j.Slf4j;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.iface.BaseRequest;
import netplus.component.entity.iface.BaseResponse;
import netplus.joint.erp.api.request.out.JK0003Request;
import netplus.joint.erp.api.response.out.JK0003.JK0003Response;
import netplus.joint.erp.api.rest.ErpOutRest;
import netplus.provider.api.pojo.ygmalluser.Department;
import netplus.provider.api.request.department.GetDepartmentRequest;
import netplus.provider.service.dao.DepartmentMapper;
import netplus.utils.date.NowDate;
import netplus.utils.uuid.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DepartmentBiz {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private ErpOutRest erpOutRest;


    @Transactional(rollbackFor = Exception.class)
    public void syncDepartment () {

        BaseRequest<JK0003Request> q = new BaseRequest();
        JK0003Request jk0003Request = new JK0003Request();
        q.setReqData(jk0003Request);
        q.setReqId(UuidUtil.getUuid());
        q.setReqTime(String.valueOf(new Date().getTime()));

        NowDate nowDate = new NowDate();

        BaseResponse<JK0003Response> resp = erpOutRest.JK0003(q);
        if (resp.getStatus().equals("1")) {

            if (resp.getRespData().getData().size() > 0) {

                log.info(String.format("部门同步接口: 获取数据共%d条", resp.getRespData().getData().size()));

                List<Department> departmentList = resp.getRespData().getData().stream().map(r -> {

                    Department department = new Department();
                    department.setDepno(r.getBmbm_pk());
                    department.setDepnum(r.getBmbh());
                    department.setDepname(r.getMc());
                    department.setComppk(r.getFgsbm_pk());
                    department.setCompname(r.getFgsmc());
                    department.setCompno(r.getDb());

                    department.setCompaddr(StringUtils.isEmpty(r.getKpgsdz()) ? " ": r.getKpgsdz());
                    department.setCompphone(StringUtils.isEmpty(r.getKpgsdh()) ? " ": r.getKpgsdh());
                    department.setTax(StringUtils.isEmpty(r.getSh()) ? " ": r.getSh());
                    department.setBank(StringUtils.isEmpty(r.getKhh()) ? " ":r.getKhh());
                    department.setBanknum(StringUtils.isEmpty(r.getKhzh()) ? " ":r.getKhzh());
                    department.setCreatedate(nowDate.getDateStr());
                    department.setCreatetime(nowDate.getTimeStr());

                    return department;

                }).collect(Collectors.toList());


                departmentMapper.deleteAll();

                for (Department department: departmentList) {
                    departmentMapper.insert(department);
                }
            }

        }else{
            throw new NetPlusException(resp.getMessage());
        }
    }

    public Department getDepartment (GetDepartmentRequest request) {
        return departmentMapper.selectByPrimaryKey(request.getDepno());
    }


}
