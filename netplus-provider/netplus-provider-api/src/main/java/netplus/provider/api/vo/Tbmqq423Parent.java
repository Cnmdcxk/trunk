package netplus.provider.api.vo;

import lombok.Data;
import netplus.provider.api.pojo.ygmalluser.Tbmqq423;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Tbmqq423Parent extends Tbmqq423 implements Serializable {
    List<Tbmqq423> children= new ArrayList<>();
}
