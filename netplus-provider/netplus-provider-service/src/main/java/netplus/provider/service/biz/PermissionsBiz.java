package netplus.provider.service.biz;

import netplus.component.entity.data.KeyValue;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.response.ApiResponse;
import netplus.provider.api.pojo.ygmalluser.Tbmqq423;
import netplus.provider.api.request.GetMenuListRequest;
import netplus.provider.api.request.permissionsRequest;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.vo.LoginUserBean;
import netplus.provider.api.vo.MenuVo;
import netplus.provider.api.vo.Tbmqq423Vo;
import netplus.provider.service.dao.Tbmqq423Mapper;
import netplus.utils.date.DateUtil;
import netplus.utils.object.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PermissionsBiz {
    @Autowired
    Tbmqq423Mapper tbmqq423Mapper;

    @Autowired
    IdentityRest identityRest;

    /**
     * 搜索权限表
     *
     * @param permissionsRequest
     * @return
     */
    public PageBean<Tbmqq423Vo> getList(permissionsRequest permissionsRequest){
        Map<String , Object> map = ObjectUtil.transBeanToMap(permissionsRequest);
        List<Tbmqq423Vo> tbmqq423VoList;
        int totalCount = tbmqq423Mapper.getjurisdictionCount(map);
        tbmqq423VoList = tbmqq423Mapper.getList(map);
        List<KeyValue> parentcodeList = tbmqq423Mapper.getparentcodeList(map);

        List<KeyValue> privilegeTypeList = new ArrayList();

        PageBean<Tbmqq423Vo> pageBean = new PageBean();
        pageBean.setItems(tbmqq423VoList);
        pageBean.setTotalCount(totalCount);
        pageBean.addResultMap("parentcode",parentcodeList);
        pageBean.addResultMap("privilegetype",privilegeTypeList);
        return pageBean;
    }

    /**
     * 添加权限表
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse addPrivilege(permissionsRequest request) {

        Date now = new Date();
        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        Map<String, Object> mapParam = new HashMap<String , Object>();
        mapParam.put("code", request.getCode());

        // 根据code查找权限表
        List<Tbmqq423Vo> tbmqq423List = tbmqq423Mapper.getPrivilegeInfoByParam(mapParam);
        // 不存在时，新增
        if ((ObjectUtil.isEmpty(tbmqq423List) || tbmqq423List.size() == 0) ) {
            // 表示顺不重复时,添加权限数据
            if (sortCheck(request)) {
                // 权限
                Tbmqq423 tbmqq423 = new Tbmqq423();
                tbmqq423.setCode(request.getCode());
                tbmqq423.setName(request.getName());
                tbmqq423.setPrivilegedesc(request.getPrivilegedesc());
                tbmqq423.setPrivilegetype(request.getPrivilegetype());
                tbmqq423.setParentcode(request.getParentcode());
                tbmqq423.setSort(Short.parseShort(request.getSort()));
                tbmqq423.setIsactive(request.getIsactive());
                tbmqq423.setBelongto("PC");
                tbmqq423.setIsdefault(request.getIsdefault());
                tbmqq423.setIcon(request.getIcon());
                tbmqq423.setPagevisible(request.getPagevisible());
                tbmqq423.setCreateuser(loginUserBean.getUserCode());
                tbmqq423.setCreatedate(DateUtil.format(now, "yyyyMMdd"));
                tbmqq423.setCreatetime(DateUtil.format(now, "HHmmss"));
                tbmqq423.setUpdateuser(loginUserBean.getUserCode());
                tbmqq423.setUpdatedate(DateUtil.format(now, "yyyyMMdd"));
                tbmqq423.setUpdatetime(DateUtil.format(now, "HHmmss"));

                int add423count = tbmqq423Mapper.insertSelective(tbmqq423);
                if (add423count != 1) {
                    throw new NetPlusException("权限数据添加失败");
                }
            } else {
                throw new NetPlusException("表示顺已经存在，请重新填写");
            }

        } else {
            throw new NetPlusException("权限数据已经存在，请重新填写信息");
        }
        return ApiResponse.success();
    }

    /**
     * 修改权限表
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse modifyPrivilege(permissionsRequest request) {

        Date now = new Date();
        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        // 表示顺不重复时
        if (sortCheck(request) || request.getSort().equals(request.getHidsort())) {
            // 权限
            Tbmqq423 tbmqq423 = new Tbmqq423();
            tbmqq423.setCode(request.getCode());
            tbmqq423.setName(request.getName());
            tbmqq423.setPrivilegedesc(request.getPrivilegedesc());
            tbmqq423.setPrivilegetype(request.getPrivilegetype());
            tbmqq423.setParentcode(request.getParentcode());
            tbmqq423.setSort(Short.parseShort(request.getSort()));
            tbmqq423.setIsactive(request.getIsactive());
            tbmqq423.setBelongto(request.getBelongto());
            tbmqq423.setIsdefault(request.getIsdefault());
            tbmqq423.setIcon(request.getIcon());
            tbmqq423.setPagevisible(request.getPagevisible());
            tbmqq423.setUpdateuser(loginUserBean.getUserCode());
            tbmqq423.setUpdatedate(DateUtil.format(now, "yyyyMMdd"));
            tbmqq423.setUpdatetime(DateUtil.format(now, "HHmmss"));

            int modify423count = tbmqq423Mapper.updateByPrimaryKeySelective(tbmqq423);
            if (modify423count != 1) {
                throw new NetPlusException("权限数据修改失败");
            }
        } else {
            throw new NetPlusException("表示顺已经存在，请重新填写");
        }
        return ApiResponse.success();
    }

    /**
     * 父权限编码对应的表示顺check
     *
     * @param request
     * @return
     */
    private boolean sortCheck(permissionsRequest request){
        boolean sortCheckflag = false;
        Map<String, Object> mapParam = new HashMap<String , Object>();
        mapParam.put("parentcode", request.getParentcode());
        mapParam.put("sort", Short.parseShort(request.getSort()));
        // 根据code查找权限表
        List<Tbmqq423Vo> tbmqq423List = tbmqq423Mapper.getPrivilegeInfoByParam(mapParam);
        // 不存在时，新增
        if (ObjectUtil.isEmpty(tbmqq423List) || tbmqq423List.size() == 0) {
            sortCheckflag = true;
        }
        return sortCheckflag;
    }

    /**
     * 根据权限类型查父权限代码
     *
     * @param request
     * @return
     */
    public PageBean<Tbmqq423Vo> getPrivilegeTypeList(permissionsRequest request){
        Map<String,Object> map = ObjectUtil.transBeanToMap(request);
        List<KeyValue> getCodeList = tbmqq423Mapper.getprivilegetypeList(map);
        PageBean<Tbmqq423Vo> pageBean = new PageBean();
        pageBean.addResultMap("privilegetype",getCodeList);
        return pageBean;
    }


    public List<MenuVo> getUserMenuList (GetMenuListRequest request) {
        Map<String, Object> map = ObjectUtil.transBeanToMap(request);

        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        map.put("userno", loginUserBean.getUserCode());
        List<Tbmqq423Vo> page = tbmqq423Mapper.getUserMenuList(map);

        Map<String, Object> getPrivilegeListByTypeParam = new HashMap();
        getPrivilegeListByTypeParam.put("type", "MODULE");
        List<Tbmqq423Vo> module = tbmqq423Mapper.getPrivilegeListByType(getPrivilegeListByTypeParam);

        getPrivilegeListByTypeParam.put("type", "MENU");
        List<Tbmqq423Vo> menu = tbmqq423Mapper.getPrivilegeListByType(getPrivilegeListByTypeParam);

        //获取权限级联数据 模块->菜单->页面
        List<MenuVo> moduleList = module.stream().map( t -> {

            MenuVo md = new MenuVo();
            md.setName(t.getName());
            md.setCode(t.getCode());

            //获取模块对应的菜单
            List<MenuVo> menuList = menu
                    .stream()
                    .filter( tt -> tt.getParentcode().equals(t.getCode()) )
                    .map( tt -> {

                        MenuVo me = new MenuVo();
                        me.setName(tt.getName());
                        me.setCode(tt.getCode());
                        me.setIcon(tt.getIcon());


                        //获取菜单对应的页面
                        List<MenuVo> pageList = page
                                .stream()
                                .filter( ttt -> ttt.getParentcode().equals(tt.getCode()) )
                                .map( ttt -> {

                                    MenuVo pa = new MenuVo();
                                    pa.setName(ttt.getName());
                                    pa.setCode(ttt.getCode());
                                    pa.setUrl(ttt.getPrivilegedesc());

//                                    if (ttt.getIsdefault().equals("Y")) {
//                                        md.setUrl(ttt.getPrivilegedesc());
//                                        me.setUrl(ttt.getPrivilegedesc());
//                                    }

                                    return pa;

                                }).collect(Collectors.toList());

                        me.setChildmenulist(pageList);

                        //如果没有值的话，默认是菜单下的第一个页面跳转路径
                        if (pageList.size() > 0) {
                            me.setUrl(pageList.get(0).getUrl());
                        }

                        return me;

                    })
                    .filter(me -> me.getChildmenulist().size() > 0)
                    .collect(Collectors.toList());

            md.setChildmenulist(menuList);

            //如果没有值的话，就认为是模块下的第一个菜单跳转路径
            if (menuList.size() > 0) {
                md.setUrl(menuList.get(0).getUrl());
            }

            return md;

        }).filter(md -> md.getChildmenulist().size() > 0).collect(Collectors.toList());

        return moduleList;
    }
}
