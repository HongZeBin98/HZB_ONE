package com.hongzebin.util;

/**
 * api常量类
 * Created by 洪泽彬
 */

public class ApiConstant {
    //音乐列表最新的url
    public static final String MUSIC_ADDRESS = "http://v3.wufazhuce.com:8000/api/channel/music/more/0?platform=android";
    //阅读列表最新的url
    public static final String READ_ADDRESS = "http://v3.wufazhuce.com:8000/api/channel/reading/more/0?channel=wdj&version=4.0.2&platform=android";
    //影视列表最新的url
    public static final String VIDEO_ADDRESS = "http://v3.wufazhuce.com:8000/api/channel/movie/more/0?platform=android";
    //插画列表最新的url
    public static final String PICTURE_ADDRESS = "http://v3.wufazhuce.com:8000/api/hp/idlist/0?version=3.5.0&platform=android";

    /**
     * 通过id得到相应的音乐api
     * @param id 需要添加的
     * @return 添加了新id的音乐api
     */
    public static String refreshMusicApi(String id){
        String musicAddress = "http://v3.wufazhuce.com:8000/api/channel/music/more/" + id + "?platform=android";
        return musicAddress;
    }

    /**
     * 通过id得到相应的阅读api
     * @param id 需要添加的id
     * @return 添加了新id的阅读api
     */
    public static String refreshReadApi(String id){
        String readAddress = "http://v3.wufazhuce.com:8000/api/channel/reading/more/" + id + "?channel=wdj&version=4.0.2&platform=android";
        return readAddress;
    }

    /**
     * 通过id得到相应的影视api
     * @param id 需要添加的id
     * @return 添加了新id的影视api
     */
    public static String refreshVideoApi(String id){
        String videoAddress = "http://v3.wufazhuce.com:8000/api/channel/movie/more/" + id + "?platform=android";
        return videoAddress;
    }

    /**
     * 通过id得到相应的插画api
     * @param id 需要添加的id
     * @return 添加了新id的插画api
     */
    public static String refreshPictureApi(String id){
        String pictureAddress = "http://v3.wufazhuce.com:8000/api/hp/idlist/" + id + "?version=3.5.0&platform=android";
        return pictureAddress;
    }
}
