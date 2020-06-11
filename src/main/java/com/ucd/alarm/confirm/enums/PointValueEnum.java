package com.ucd.alarm.confirm.enums;


/**
 * @ClassName: Status
 * @Description: 告警点值 数据类型与字段对应枚举类
 * @Author: Crayon
 * @CreateDate: 2020/6/11 11:41 上午
 * @Version 1.0
 * @Copyright: Copyright©2018-2020 BJCJ Inc. All rights reserved.
 **/
public enum PointValueEnum {
    INTEGER_I("Integer", "i"),
    BOOLEAN_I("Boolean", "i"),
    DOUBLE_D("Double", "d"),
    STRING_S("String", "s"),
    ARRAY_S("Array", "s"),
    LONG_L("Long", "l"),
    DWORD("DWord", "dw"),
    ERROR("Error", "报错了");
    /**
     * 类型
     */
    private String type;
    /**
     * 字段名称
     */
    private String fieldName;

    PointValueEnum(String type, String fieldName) {
        this.fieldName = fieldName;
        this.type = type;
    }


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
