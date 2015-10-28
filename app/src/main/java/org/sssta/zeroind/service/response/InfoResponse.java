package org.sssta.zeroind.service.response;

import org.sssta.zeroind.model.User;

/**
 * Created by 林韬 on 2015/10/24.
 */
public class InfoResponse {
    private int status = -1;
    private String token;

    private User user;

    public String getToken() {
        return token;
    }
//    private

    public int getStatus() {
        return status;
    }
    public User getUser() {
        return user;
    }
}
