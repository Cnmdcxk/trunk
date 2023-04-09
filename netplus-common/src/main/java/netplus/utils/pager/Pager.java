package netplus.utils.pager;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import netplus.component.entity.exceptions.NetPlusException;

import java.util.List;

@Setter
@Getter
@Slf4j
public class Pager<T>  {

    private int totalPage;
    private int totalCount;
    private List<T> data;
    private int limit;

    public Pager (List<T> data, int limit) {

        if (null == data || data.size() == 0) {
            throw new NetPlusException("数组是null或者size等于0");
        }

        this.totalCount = data.size();
        this.limit = limit;
        this.data = data;
        this.totalPage = this.totalCount / limit;
        int remain = this.totalCount % limit;
        if (remain > 0) {
            this.totalPage  = this.totalPage + 1;
        }
    }

    public List<T> getPageData(int page) {

        int start = (page - 1) * limit;
        int end = page * limit;

        if (end > totalCount) {
            end = totalCount;
        }

        log.info(String.format("第%d页：data.subList(%d, %d)", page, start, end));
        return data.subList(start, end);
    }




}
