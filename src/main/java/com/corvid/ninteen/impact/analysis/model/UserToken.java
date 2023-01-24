package com.corvid.ninteen.impact.analysis.model;

import org.springframework.stereotype.Component;

/**
 * @author shahzeb.latif
 */

@Component
public class UserToken {
    private String access_token;
    private String token_type;
    private String message;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
