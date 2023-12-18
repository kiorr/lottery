package com.itheima.prize.commons.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName view_dict
 */
@TableName(value ="view_dict")
public class ViewDict implements Serializable {
    /**
     * 字典名称
     */
    @TableField(value = "dict_name")
    private String dictName;

    /**
     * 字典编码
     */
    @TableField(value = "dict_code")
    private String dictCode;

    /**
     * 字典项值
     */
    @TableField(value = "item_value")
    private String itemValue;

    /**
     * 字典项文本
     */
    @TableField(value = "item_text")
    private String itemText;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 字典名称
     */
    public String getDictName() {
        return dictName;
    }

    /**
     * 字典名称
     */
    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    /**
     * 字典编码
     */
    public String getDictCode() {
        return dictCode;
    }

    /**
     * 字典编码
     */
    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    /**
     * 字典项值
     */
    public String getItemValue() {
        return itemValue;
    }

    /**
     * 字典项值
     */
    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    /**
     * 字典项文本
     */
    public String getItemText() {
        return itemText;
    }

    /**
     * 字典项文本
     */
    public void setItemText(String itemText) {
        this.itemText = itemText;
    }
}