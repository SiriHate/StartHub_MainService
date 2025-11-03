package org.siri_hate.main_service.model.dto.response.user;

public class UserBaseResponse {

    private Long id;
    private String username;

    public UserBaseResponse() {
    }

    public UserBaseResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
