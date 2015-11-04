package org.sssta.zeroind.service.response;

import com.google.gson.annotations.SerializedName;

import org.sssta.zeroind.model.Message;

import java.util.List;

/**
 * Created by Heaven on 2015/11/1.
 */
public class MessageResponse {
    private int status = -1;
    private String token;
    @SerializedName("  ")
    private List<Message> messageList;

    public int getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public List<Message> getUnReadMessageList() {
        return messageList;
    }
}
