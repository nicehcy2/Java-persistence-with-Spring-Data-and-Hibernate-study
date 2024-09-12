package com.mannig.javapersistence.springdatajpa.model;

import org.springframework.beans.factory.annotation.Value;

public class Projection {

    public interface UserSummary {

        String getUsername();

        @Value("#{target.username} #{target.email}")
        String getInfo();
    }

    public static class UserNameOnly {

        private String username;

        public UserNameOnly(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }
    }
}
