package com.corvid.ninteen.impact.analysis.model;

import org.springframework.stereotype.Component;

/**
 * @author shahzeb.latif
 */

@Component
public class User {

    private String clientId;
    private String role;

    public User() {
    }

    public User(String clientId, String role) {
        this.clientId = clientId;
        this.role = role;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "clientId='" + clientId + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}