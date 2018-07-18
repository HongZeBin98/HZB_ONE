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
     * 通过id得到相应的音乐列表api
     * @param id 需要添加的
     * @return 添加了新id的音乐列表api
     */
    public static String refreshMusicApi(String id){
        String musicAddress = "http://v3.wufazhuce.com:8000/api/channel/music/more/" + id + "?platform=android";
        return musicAddress;
    }

    /**
     * 通过id得到相应的阅读列表api
     * @param id 需要添加的id
     * @return 添加了新id的阅读列表api
     */
    public static String refreshReadApi(String id){
        String readAddress = "http://v3.wufazhuce.com:8000/api/channel/reading/more/" + id + "?channel=wdj&version=4.0.2&platform=android";
        return readAddress;
    }

    /**
     * 通过id得到相应的影视列表api
     * @param id 需要添加的id
     * @return 添加了新id的影视列表api
     */
    public static String refreshVideoApi(String id){
        String videoAddress = "http://v3.wufazhuce.com:8000/api/channel/movie/more/" + id + "?platform=android";
        return videoAddress;
    }

    /**
     * 通过id得到相应的插画列表api
     * @param id 需要添加的id
     * @return 添加了新id的插画列表api
     */
    public static String refreshPictureApi(String id){
        String pictureAddress = "http://v3.wufazhuce.com:8000/api/hp/idlist/" + id + "?version=3.5.0&platform=android";
        return pictureAddress;
    }

    /**
     * 通过itemId得到相应的阅读api
     * @param itemId 需要添加的itemId
     * @return 添加了新itemId的阅读api
     */
    public static String getReadAddress(String itemId){
        String ReadDetailAddress = "http://v3.wufazhuce.com:8000/api/essay/" + itemId + "?platform=android";
        return ReadDetailAddress;
    }

    /**
     * 通过itemId得到相应的音乐api
     * @param itemId 需要添加的itemId
     * @return 添加了新itemId的音乐api
     */
    public static String getMusicAddress(String itemId){
        String MusicDetailAddress = "http://v3.wufazhuce.com:8000/api/music/detail/" + itemId + "?version=3.5.0&platform=android";
        return MusicDetailAddress;
    }

    /**
     * 通过itemId得到相应的影视api
     * @param itemId 需要添加的itemId
     * @return 添加了新itemId的影视api
     */
    public static String getVideoAddress(String itemId){
        String VideoDetailAddress = "http://v3.wufazhuce.com:8000/api/movie/" + itemId + "/story/1/0?platform=android";
        return VideoDetailAddress;
    }

    /**
     * 通过itemId得到相应的插画api
     * @param itemId 需要添加的itemId
     * @return 添加了新itemId的插画api
     */
    public static String getPictureAddress(String itemId){
        String PictureDetailAddress = "http://v3.wufazhuce.com:8000/api/hp/detail/" + itemId + "?version=3.5.0&platform=android";
        return PictureDetailAddress;
    }

    /**
     * 通过itemId得到相应的影视评论api
     * @param itemId 需要添加的itemId
     * @return 添加了新itemId的影视评论api
     */
    public static String getVideoComAddress(String itemId){
        String VideoComAddress = "http://v3.wufazhuce.com:8000/api/comment/praiseandtime/movie/" + itemId + "/0?&platform=android";
        return VideoComAddress;
    }

    /**
     * 通过itemId得到相应的音乐评论api
     * @param itemId 需要添加的itemId
     * @return 添加了新itemId的音乐评论api
     */
    public static String getMusicComAddress(String itemId){
        String MusicComAddress = "http://v3.wufazhuce.com:8000/api/comment/praiseandtime/music/" + itemId + "/0?platform=android";
        return MusicComAddress;
    }

    /**
     * 通过itemId得到相应的阅读评论api
     * @param itemId 需要添加的itemId
     * @return 添加了新itemId的阅读评论api
     */
    public static String getReadComAddress(String itemId){
        String ReadComAddress = "http://v3.wufazhuce.com:8000/api/comment/praiseandtime/essay/" + itemId + "/0?&platform=android";
        return ReadComAddress;
    }
}
