package com.znq.freedom.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ObjMapUtils {
    
    public static Map<String, Object> convertObjToMap(Object obj) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for(int i = 0; i < fields.length; i++){
            try {
                Field field = obj.getClass().getDeclaredField(fields[i].getName());
                field.setAccessible(true);
                Object value = field.get(obj);
                map.put(fields[i].getName(), value);
            } catch (NoSuchFieldException 
                    | SecurityException 
                    | IllegalArgumentException 
                    | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("对象转换map异常");
            }
        }
        return map;
    }

}
