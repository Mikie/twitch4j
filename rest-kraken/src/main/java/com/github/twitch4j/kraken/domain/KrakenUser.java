package com.github.twitch4j.kraken.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KrakenUser {

    @JsonProperty("_id")
    private String id;

    private String name;

    @JsonProperty("display_name")
    private String displayName;

    private String logo;

    private String type;

    private String bio;

    private String email;

    private Boolean emailVerified;

    private Notifications notifications;

    private Boolean partnered;

    private Boolean twitterConnected;

    @JsonProperty("updated_at")
    private Instant updatedAtInstant;

    @JsonProperty("created_at")
    private Instant createdAtInstant;

    /**
     * @return the timestamp the account was created
     * @deprecated in favor of getCreatedAtInstant()
     */
    @JsonIgnore
    @Deprecated
    public Date getCreatedAt() {
        return Date.from(createdAtInstant);
    }

    /**
     * @return the timestamp the account was updated
     * @deprecated in favor of getUpdatedAtInstant()
     */
    @JsonIgnore
    @Deprecated
    public Date getUpdatedAt() {
        return Date.from(updatedAtInstant);
    }

    @Data
    @Setter(AccessLevel.PRIVATE)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Notifications {
        private Boolean email;
        private Boolean push;
    }
}
