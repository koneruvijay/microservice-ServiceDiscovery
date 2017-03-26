package com.cognizant.microservices;

/**
 * Created by Koneru on 3/14/17.
 */

import java.util.UUID;

/**
 *
 */
public class User {

    public UUID getUserId() {
        return userId;
    }


    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    private UUID userId;

}
