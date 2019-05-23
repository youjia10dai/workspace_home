package com.royasoft.account.api.vo;


import java.io.Serializable;
import java.util.List;

/**
 * @author Clj 注销人员分页显示
 */
public class AccountPageVo implements Serializable{

    private static final long serialVersionUID = 4436388386861565695L;

    /**
     * 总页数
     */
    private Integer total;

    /**
     * 分页的内容
     */
    private List<AccountVo> content;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<AccountVo> getContent() {
        return content;
    }

    public void setContent(List<AccountVo> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "AccountPageVo [total=" + total + ", content=" + content + "]";
    }
}
