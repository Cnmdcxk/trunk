package netplus.component.entity.data;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PageBean<T> implements Serializable {

    private static final long serialVersionUID = 8470697978259453214L;

    private int totalCount;

    private int totalPages;

    private List<T> items = new ArrayList<>(0);

    private Map<String, Object> resultMap = new HashMap<>();

    /**
     * 计算总页数
     * @param totalCount 总记录数
     * @param pageSize 每页多少条记录
     * @return
     */
    public static int countTotalPage(int totalCount, int pageSize) {
        if (totalCount % pageSize == 0) {
            // 刚好整除
            return totalCount / pageSize;
        } else {
            // 不能整除则总页数为：商 + 1
            return totalCount / pageSize + 1;
        }
    }

    public void addResultMap (String key, Object object) {
        resultMap.put(key, object);
    }

}
