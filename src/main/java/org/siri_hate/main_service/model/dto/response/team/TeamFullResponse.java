package org.siri_hate.main_service.model.dto.response.team;

public class TeamFullResponse extends TeamBaseResponse {

    public TeamFullResponse() {
    }

    public TeamFullResponse(Long id, String username, String role) {
        super(id, username, role);
    }
}
