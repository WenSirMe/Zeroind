package org.sssta.zeroind;

/**
 * Created by Heaven on 2015/11/3.
 */
public interface NContent {
    int POST_MESSAGE = 0;
    int REQUEST_MESSAGE = 1;
    String[] WIND_FLAG_NAME={
            "无风带","孤独风","逗比风","疲惫风",
            "愉悦风"," 失落风","烦恼风","甜蜜风"
    };
    String[] TEMP_TEXT_FROM_ACTIVITY={
            "PostMessageActivity","RequestMessageActivity"
    };
    String FROM_ACTIVITY_KEY = "FromActivityKey";
    String INFO_DIRECTION_FRAGMENT = "InfoDirectionFragment";
    String INFO_MESSAGE_ALL = "InfoMessageAll";
    String INFO_MESSAGE_CONTENT = "InfoMessageContent";
    String INFO_MESSAGE_DIRECTION = "InfoMessageDirection";
    String INFO_MESSAGE_IMAGE_ID = "InfoMessageImageId";
}
