package com.example.jarvis.sendmefoodclient.Current;

import com.example.jarvis.sendmefoodclient.Model.RqData;
import com.example.jarvis.sendmefoodclient.Model.User;

public class Current {
    public static User usrCurrent;

    public static RqData currentRqData;

    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";

    public static String statusCode(String code){
        if(code.equals("0")){
            return "your order is placed";
        }else if(code.equals("1")){
            return "your order is on the way";
        }else{
            return "your order is already shipped";
        }
    }

}
