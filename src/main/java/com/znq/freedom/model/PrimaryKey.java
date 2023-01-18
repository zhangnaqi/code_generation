package com.znq.freedom.model;

import lombok.Data;

@Data
public class PrimaryKey {

    private String pkName;
    private String pkPropertyName;
    private String pkType;
    private boolean PKAuto = false;

}
