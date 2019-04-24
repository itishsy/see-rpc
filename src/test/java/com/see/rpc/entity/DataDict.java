package com.see.rpc.entity;

import java.io.Serializable;

public class DataDict implements Serializable {
    private Long id;

    private String dictCode;

    private String dictName;

    private int displayOrder;

    private String status;

    private String comment;

    private String easSubject;

    private String easSubjectName;

    private String dataKey;

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 字典代码
     *
     * @return
     */
    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    /**
     * 字典名称
     *
     * @return
     */
    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    /**
     * 字典显示顺序
     *
     * @return
     */
    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * 字典状态
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 字典备注
     *
     * @return
     */
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEasSubject() {
        return easSubject;
    }

    public void setEasSubject(String easSubject) {
        this.easSubject = easSubject;
    }

    public String getEasSubjectName() {
        return easSubjectName;
    }

    public void setEasSubjectName(String easSubjectName) {
        this.easSubjectName = easSubjectName;
    }
}
