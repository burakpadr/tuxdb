package com.padr.tuxdb.engine.process;

import java.util.HashMap;
import java.util.Map;

public class Feedback {
    
    public static Map<String, Object> feedback(Integer success, String message){
        Map<String, Object> feedbackMap = new HashMap<>();

        feedbackMap.put("success", success);
        feedbackMap.put("message", message);

        return feedbackMap;
    }
}
