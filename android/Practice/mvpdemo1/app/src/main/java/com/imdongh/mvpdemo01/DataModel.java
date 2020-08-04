package com.imdongh.mvpdemo01;

import com.imdongh.mvpdemo01.base.BaseModel;

public class DataModel {
    public static BaseModel request(String token) {
        //
        BaseModel model = null;

        try {
            model = (BaseModel) Class.forName(token).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return model;
    }
}
