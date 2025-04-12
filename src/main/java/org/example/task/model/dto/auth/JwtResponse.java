package org.example.task.model.dto.auth;

import lombok.Getter;
import lombok.ToString;


/** This class represents the response object that is returned to the client
 * after a successful authentication. It contains the JWT token, username,
 * and email of the authenticated user.
 */

@Getter @ToString
public class JwtResponse {

    private String token;
    private String username;
    private String email;

    public JwtResponse(String token, String username, String email) {
        this.token = token;
        this.username = username;
        this.email = email;
    }
}

