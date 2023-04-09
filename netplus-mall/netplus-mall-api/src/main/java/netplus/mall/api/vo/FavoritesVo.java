package netplus.mall.api.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/*收藏夹实体类*/
public class FavoritesVo implements Serializable {
    private String goodid;

    private String categoryname;

    private String onelevelclaname;

    private String twolevelclaname;

    private String productname;

    private String goodno;

    private String matrlno;

    private String qtyunit;

    private BigDecimal price;

    private BigDecimal notaxprice;

    private BigDecimal tax;

    private String spec;

    private String brand;

    private Long referdeliverydate;

    private String buyerno;

    private String buyername;

    private String userno;

    private String username;

    private String supplierno;

    private String suppliername;

    private String createuser;

    private String createdate;

    private String createtime;

    private String updateuser;

    private String updatedate;

    private String updatetime;

    public String getGoodid() {
        return goodid;
    }

    public void setGoodid(String goodid) {
        this.goodid = goodid == null ? null : goodid.trim();
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname == null ? null : categoryname.trim();
    }

    public String getOnelevelclaname() {
        return onelevelclaname;
    }

    public void setOnelevelclaname(String onelevelclaname) {
        this.onelevelclaname = onelevelclaname == null ? null : onelevelclaname.trim();
    }

    public String getTwolevelclaname() {
        return twolevelclaname;
    }

    public void setTwolevelclaname(String twolevelclaname) {
        this.twolevelclaname = twolevelclaname == null ? null : twolevelclaname.trim();
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
    }

    public String getGoodno() {
        return goodno;
    }

    public void setGoodno(String goodno) {
        this.goodno = goodno == null ? null : goodno.trim();
    }

    public String getMatrlno() {
        return matrlno;
    }

    public void setMatrlno(String matrlno) {
        this.matrlno = matrlno == null ? null : matrlno.trim();
    }

    public String getQtyunit() {
        return qtyunit;
    }

    public void setQtyunit(String qtyunit) {
        this.qtyunit = qtyunit == null ? null : qtyunit.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getNotaxprice() {
        return notaxprice;
    }

    public void setNotaxprice(BigDecimal notaxprice) {
        this.notaxprice = notaxprice;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public Long getReferdeliverydate() {
        return referdeliverydate;
    }

    public void setReferdeliverydate(Long referdeliverydate) {
        this.referdeliverydate = referdeliverydate;
    }

    public String getBuyerno() {
        return buyerno;
    }

    public void setBuyerno(String buyerno) {
        this.buyerno = buyerno == null ? null : buyerno.trim();
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername == null ? null : buyername.trim();
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno == null ? null : userno.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
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
}