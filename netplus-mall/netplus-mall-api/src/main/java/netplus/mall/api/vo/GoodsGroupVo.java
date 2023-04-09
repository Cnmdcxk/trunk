package netplus.mall.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
/*商品组实体类*/
public class GoodsGroupVo implements Serializable {
    private String groupno; // 分组编号

    private String referdeliverydate; // 交货日期

    private String supplierno; // 供应商编号

    private String suppliername; // 供应商名称

    private String createuser; // 创建用户

    private String createdate; // 创建时间(年月日)

    private String createtime; // 创建时间(时分秒)

    private String updateuser; // 修改用户

    private String updatedate; // 修改时间(年月日)

    private String updatetime; // 修改时间(时分秒)

    private List<GoodsVo> goodslist; // 商品列表

    public String getGroupno() {
        return groupno;
    }

    public void setGroupno(String groupno) {
        this.groupno = groupno == null ? null : groupno.trim();
    }

    public String getSupplierno() {
        return supplierno;
    }

    public void setSupplierno(String supplierno) {
        this.supplierno = supplierno == null ? null : supplierno.trim();
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername == null ? null : suppliername.trim();
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate == null ? null : createdate.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser == null ? null : updateuser.trim();
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate == null ? null : updatedate.trim();
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    public List<GoodsVo> getGoodslist() {
        return goodslist;
    }

    public void setGoodslist(List<GoodsVo> goodslist) {
        this.goodslist = goodslist;
    }

    public GoodsGroupVo() {
        this.goodslist = new ArrayList<GoodsVo>();
    }
}