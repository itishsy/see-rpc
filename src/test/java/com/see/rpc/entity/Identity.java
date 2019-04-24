package com.see.rpc.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 所有数据库实体的父对象
 * @author King.peng
 * CreateTime:2017年9月21日
 */
public abstract class Identity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8359308998962438119L;
	/**
     * 自增长id
     */
    //="自增长id")
    protected Long id;
    /**
     * 创建人id
     */
    //="创建人id")
    private Integer createId;
    /**
     * 创建人
     */
    //="创建人")
    private String createName;
    /**
     * 创建时间
     */
    //="创建时间")
    private Date createTime;
    /**
     * 更新人id
     */
    //="更新人id")
    private Integer updateId;
    /**
     * 更新人
     */
    //="更新人")
    private String updateName;
    /**
     * 更新时间
     */
    //="更新时间")
    private Date updateTime;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Integer getCreateId()
    {
        return createId;
    }

    public void setCreateId(Integer createId)
    {
        this.createId = createId;
    }

    public String getCreateName()
    {
        return createName;
    }

    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Integer getUpdateId()
    {
        return updateId;
    }

    public void setUpdateId(Integer updateId)
    {
        this.updateId = updateId;
    }

    public String getUpdateName()
    {
        return updateName;
    }

    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

}
