package com.znq.freedom.model.DTO;

import com.znq.freedom.model.DataBase;
import com.znq.freedom.model.GlobalConfig;

import lombok.Data;

@Data
public class ConfigDTO {

    private DataBase dataBase;

    private GlobalConfig globalConfig;

}
