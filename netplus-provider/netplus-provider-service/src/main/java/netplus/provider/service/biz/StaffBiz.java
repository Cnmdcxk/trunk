package netplus.provider.service.biz;

import netplus.component.entity.data.PageBean;
import netplus.component.entity.enums.YesNoEnum;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.excel.api.request.GenExcelRequest;
import netplus.excel.api.rest.ExportRest;
import netplus.provider.api.request.*;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.vo.LoginUserBean;
import netplus.provider.api.vo.Tbdu01Vo;
import netplus.provider.api.vo.Tbmqq420Vo;
import netplus.provider.api.vo.Tbmqq422Vo;
import netplus.provider.service.dao.Tbdu01Mapper;
import netplus.provider.service.dao.Tbmqq420Mapper;
import netplus.provider.service.dao.Tbmqq422Mapper;
import netplus.utils.date.NowDate;
import netplus.utils.object.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StaffBiz {

    @Autowired
    private Tbdu01Mapper tbdu01Mapper;

    @Autowired
    private Tbmqq422Mapper tbmqq422Mapper;

    @Autowired
    private Tbmqq420Mapper tbmqq420Mapper;

    @Autowired
    private ExportRest exportRest;

    @Autowired
    private IdentityRest identityRest;

    public PageBean<Tbdu01Vo> getList (GetStaffListRequest request) {

        PageBean<Tbdu01Vo> pageBean = new PageBean();

        List<Tbdu01Vo> tbdu01VoList = tbdu01Mapper.getList(ObjectUtil.transBeanToMap(request));
        int count = tbdu01Mapper.getCount(ObjectUtil.transBeanToMap(request));
        if(tbdu01VoList != null && count != 0){
            List<String> userNoList = tbdu01VoList.stream().map(t -> t.getUserno()).collect(Collectors.toList());
            List<Tbmqq422Vo> tbmqq422VoList = tbmqq422Mapper.getUserRoleByUserNo(userNoList);

            Map<String, List<Tbmqq422Vo>> groupData = tbmqq422VoList.stream().collect(Collectors.groupingBy( t -> t.getUserno()));
            for (Tbdu01Vo tbdu01Vo: tbdu01VoList) {

                if (groupData.keySet().contains(tbdu01Vo.getUserno())) {
                    tbdu01Vo.setTbmqq422VoList(groupData.get(tbdu01Vo.getUserno()));
                }

            }
        }


        pageBean.setTotalCount(count);
        pageBean.setItems(tbdu01VoList);

        return pageBean;
    }

    public List<Tbmqq420Vo> getUserOwnRoleByUserNo (GetUserOwnRoleByUserNoRequest request) {
        return tbmqq420Mapper.getUserOwnRoleByUserNo(request.getUserNo());
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateRole(UpdateStaffRoleRequest request) {

        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        NowDate nowDate = new NowDate();

        List<Tbmqq422Vo> tbmqq422VoList = request.getRoleCodeList().stream().map( r -> {

            Tbmqq422Vo tbmqq422Vo = new Tbmqq422Vo();
            tbmqq422Vo.setUserno(request.getUserNo());
            tbmqq422Vo.setRolecode(r);
            tbmqq422Vo.setCreateuser(loginUserBean.getUserCode());
            tbmqq422Vo.setCreatedate(nowDate.getDateStr());
            tbmqq422Vo.setCreatetime(nowDate.getTimeStr());
            tbmqq422Vo.setUpdateuser(loginUserBean.getUserCode());
            tbmqq422Vo.setUpdatedate(nowDate.getDateStr());
            tbmqq422Vo.setUpdatetime(nowDate.getTimeStr());

            return tbmqq422Vo;

        }).collect(Collectors.toList());

        tbmqq422Mapper.deleteByUserNo(request.getUserNo());

        if (tbmqq422VoList.size() > 0) {

            tbmqq422Mapper.batchInsert(tbmqq422VoList);
            tbdu01Mapper.updateIsRole(request.getUserNo(), YesNoEnum.Yes.getValue(), "");

        }else{
            tbdu01Mapper.updateIsRole(request.getUserNo(), YesNoEnum.No.getValue(), "");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateActive(UpdateStaffStatusRequest request) {

        String oldIsActive = YesNoEnum.Yes.getValue();

        if (request.getIsActive().equals(YesNoEnum.Yes.getValue())) {
            oldIsActive = YesNoEnum.No.getValue();
        }

        int count = tbdu01Mapper.updateIsActive(request.getUserNo(), request.getIsActive(), oldIsActive);
        if (count != 1) {
            throw new NetPlusException("操作失败");
        }

    }

    public String exportStaff (GetStaffListRequest request) throws Exception {
        PageBean<Tbdu01Vo> vos = getList(request);
        String tempName = "tmp_account_info";
        for (Tbdu01Vo t: vos.getItems()) {
            t.setUsertype(t.getUsertype().equals("B")?"员工":"供应商");
            t.setIsavailable(t.getIsavailable().equals("Y") ?"启用":"禁用");
            if(ObjectUtil.nonEmpty(t.getTbmqq422VoList())) {
                String roleName="";
                for (Tbmqq422Vo tbmqq422Vo:t.getTbmqq422VoList()) {
                    roleName+=tbmqq422Vo.getRolename()+" ";
                }
            t.setRoleName(roleName);
            }
        }
        GenExcelRequest genExcelRequest = new GenExcelRequest();
        genExcelRequest.setItems(vos.getItems());
        genExcelRequest.setTemplateName(tempName);
        genExcelRequest.setSheetName("导出明细");
        String url = exportRest.genExcel(genExcelRequest);
        return url;
    }


    public List<Tbdu01Vo> getConsignee(GetConsigneeRequest request) {
        return tbdu01Mapper.getConsignee(request.getKeyword(), request.getDeptNo());
    }


    public Tbdu01Vo getConsigneeByUserNo(GetConsigneeRequest request) {
        return tbdu01Mapper.getConsigneeByUserNo(request.getUserNo());
    }


    public List<String> getUserRoleList (GetUserRoleListRequest request) {
        return tbmqq422Mapper.getUserRoleList(request.getUserNo());
    }

}
