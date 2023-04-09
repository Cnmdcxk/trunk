package netplus.provider.service.biz;


import netplus.component.entity.data.PageBean;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.response.ApiResponse;import netplus.provider.api.pojo.ygmalluser.Tbmqq420;
import netplus.provider.api.pojo.ygmalluser.Tbmqq421;
import netplus.provider.api.pojo.ygmalluser.Tbmqq423;
import netplus.provider.api.request.GetRolePrivilegeListRequest;
import netplus.provider.api.request.RolemanagerRequest;
import netplus.provider.api.request.SaveRolePrivilegeRequest;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.rest.ServiceConfigRest;
import netplus.provider.api.vo.LoginUserBean;
import netplus.provider.api.vo.Tbmqq420Vo;
import netplus.provider.api.vo.Tbmqq423Parent;
import netplus.provider.api.vo.Tbmqq423Vo;
import netplus.provider.service.dao.*;
import netplus.utils.date.DateUtil;
import netplus.utils.object.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleBiz {
    @Autowired
    private Tbmqq420Mapper tbmqq420Mapper;

    @Autowired
    private Tbmqq421Mapper tbmqq421Mapper;


    @Autowired
    private Tbmqq423Mapper tbmqq423Mapper;

    @Autowired
    private Tbmqq422Mapper tbmqq422Mapper;

    @Autowired
    IdentityRest identityRest;

    @Autowired
    ServiceConfigRest serviceConfigRest;

    @Autowired
    Tbdu01Mapper tbdu01Mapper;

    public PageBean<Tbmqq420Vo> getList(RolemanagerRequest request) {

        PageBean<Tbmqq420Vo> pageBean = new PageBean();

        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);

        List<Tbmqq420Vo> tbmqq420VoList = tbmqq420Mapper.getall(mapParam);

        int count = tbmqq420Mapper.getallCount(mapParam);

        pageBean.setTotalCount(count);
        pageBean.setItems(tbmqq420VoList);

        return pageBean;
    }


    public void delete(RolemanagerRequest request) {

        String id=request.getRolecode();

        tbmqq420Mapper.deleteByRolecode(id);
    }

    public boolean islist(RolemanagerRequest request) {

        String id=request.getRolecode();

        List<String> roleList = tbmqq422Mapper.getRoleList();

        boolean res= roleList.contains(request.getRolecode());

        return res;
    }


    public List<Tbmqq423Parent> getListPermissions() {

        List<Tbmqq423Parent> parent=tbmqq423Mapper.getListPermissionsParent();

        for (int i = 0; i < parent.size(); i++) {
            String code = parent.get(i).getCode();

            List<Tbmqq423> children = tbmqq423Mapper.getListPermissionsChildren(code);

            parent.get(i).setChildren(children);
        }
        return parent;
    }
    /**
     * 添加角色
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse addRole(RolemanagerRequest request) {

        Date now = new Date();

        LoginUserBean loginUserBean =identityRest.getCurrentUser();

        Map<String, Object> mapParam = new HashMap<String , Object>();
        mapParam.put("code", request.getRolecode());
        // 根据code查找权限表
        List<Tbmqq420Vo> tbmqq420List = tbmqq420Mapper.getRoleByParam(mapParam);
        // 不存在时，新增
        if (ObjectUtil.isEmpty(tbmqq420List) || tbmqq420List.size() == 0) {
            Tbmqq420 tbmqq420 = new Tbmqq420();

            tbmqq420.setRolecode(request.getRolecode());
            tbmqq420.setRolename(request.getRolename());
            tbmqq420.setRoledesc(request.getRoledesc());
            tbmqq420.setCreateuser(loginUserBean.getUserCode());
            tbmqq420.setCreatedate(DateUtil.format(now, "yyyyMMdd"));
            tbmqq420.setCreatetime(DateUtil.format(now, "HHmmss"));
            tbmqq420.setUpdateuser(loginUserBean.getUserCode());
            tbmqq420.setUpdatedate(DateUtil.format(now, "yyyyMMdd"));
            tbmqq420.setUpdatetime(DateUtil.format(now, "HHmmss"));
            tbmqq420.setRolepermission("0");

            int add420count = tbmqq420Mapper.insertSelective(tbmqq420);
            if (add420count != 1) {
                throw new NetPlusException("权限数据添加失败");
            }
        }
        else {
            throw new NetPlusException("权限数据已经存在，请重新填写信息");
        }
        return ApiResponse.success();

    }
    /**
     * 修改角色表
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse updateRole(RolemanagerRequest request) {

        Date now = new Date();

        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        Tbmqq420 tbmqq420 = new Tbmqq420();

        tbmqq420.setRolecode(request.getRolecode());
        tbmqq420.setRolename(request.getRolename());
        tbmqq420.setRoledesc(request.getRoledesc());
        tbmqq420.setUpdateuser(loginUserBean.getUserCode());
        tbmqq420.setUpdatedate(DateUtil.format(now, "yyyyMMdd"));
        tbmqq420.setUpdatetime(DateUtil.format(now, "HHmmss"));

        int modify420count = tbmqq420Mapper.modifyRole(tbmqq420);
        if (modify420count != 1) {
            throw new NetPlusException("角色数据修改失败");
            }
        return ApiResponse.success();
    }

    public List<Tbmqq423Vo> getRolePrivilegeList (GetRolePrivilegeListRequest request) {


        List<Tbmqq423Vo> tbmqq423VoList = tbmqq423Mapper.getRolePrivileges(ObjectUtil.transBeanToMap(request));

        Map<String, List<Tbmqq423Vo>> groupData = tbmqq423VoList
                .stream()
                .collect(Collectors
                        .groupingBy( t -> String.format("%s-%s", t.getParentcode(), t.getParentname())));


        List<Tbmqq423Vo> rolePrivilegeList = new ArrayList();
        for (Map.Entry<String, List<Tbmqq423Vo>> entry: groupData.entrySet()) {
            String key = entry.getKey();
            List<Tbmqq423Vo> val = entry.getValue();

            Tbmqq423Vo t = new Tbmqq423Vo();
            t.setParentname(key.split("-")[1]);
            t.setParentcode(key.split("-")[0]);
            t.setChildprivilegelist(val);
            rolePrivilegeList.add(t);
        }

        return rolePrivilegeList;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveRolePrivilege (SaveRolePrivilegeRequest request) {

        if (StringUtils.isEmpty(request.getRolecode())) {
            throw new NetPlusException("角色代码不能为空");
        }

        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        tbmqq421Mapper.delByRoleCode(ObjectUtil.transBeanToMap(request));

        Date now = new Date();
        String rolepermission = "1";
        if (request.getPrivilegecodelist().size() > 0) {

            List<Tbmqq421> tbmqq421List = new ArrayList();
            request.getPrivilegecodelist().forEach( p -> {

                Tbmqq421 t = new Tbmqq421();
                t.setRolecode(request.getRolecode());
                t.setPrivilegecode(p);
                t.setCreateuser(loginUserBean.getUserCode());
                t.setCreatedate(DateUtil.format(now, "yyyyMMdd"));
                t.setCreatetime(DateUtil.format(now, "HHmmss"));
                t.setUpdateuser(loginUserBean.getUserCode());
                t.setUpdatedate(DateUtil.format(now, "yyyyMMdd"));
                t.setUpdatetime(DateUtil.format(now, "HHmmss"));

                tbmqq421List.add(t);
            });

            tbmqq421Mapper.bulkCreate(tbmqq421List);
        }else{
            rolepermission = "0";
        }

        Tbmqq420 tbmqq420 = new Tbmqq420();
        tbmqq420.setRolecode(request.getRolecode());
        tbmqq420.setRolepermission(rolepermission);
        tbmqq420.setUpdateuser(loginUserBean.getUserCode());
        tbmqq420.setUpdatedate(DateUtil.format(now, "yyyyMMdd"));
        tbmqq420.setUpdatetime(DateUtil.format(now, "HHmmss"));
        tbmqq420Mapper.updateByPrimaryKeySelective(tbmqq420);

    }

    public List<Tbmqq423Vo> getRoleOwnPrivileges (GetRolePrivilegeListRequest request) {
        List<Tbmqq423Vo> tbmqq423VoList = tbmqq423Mapper.getRoleOwnPrivileges(ObjectUtil.transBeanToMap(request));
        return tbmqq423VoList;
    }
}

