package com.dongh.funplus.http;

/**
 * Created by chenxz on 2017/11/30.
 */

public class APi {

    /**
     * 天气预报url
     */
    public static final String WEATHER_HOST = "http://www.weather.com.cn/";//"http://wthrcdn.etouch.cn/";
    public static final String WAN_HOST = "https://www.wanandroid.com/";


    public static String getHost(int hostType) {
        switch (hostType) {
            case HostType.WEATHER_INFO:
//                return APi.WEATHER_HOST;
                return APi.WAN_HOST;

        }
        return "";
    }

}
