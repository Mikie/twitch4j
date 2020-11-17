package com.github.twitch4j.helix.eventsub.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.twitch4j.helix.domain.CustomReward;
import com.github.twitch4j.helix.eventsub.domain.GlobalCooldown;
import com.github.twitch4j.helix.eventsub.domain.MaxPerStream;
import com.github.twitch4j.helix.eventsub.domain.MaxPerUserPerStream;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Data
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ChannelPointsCustomRewardEvent extends EventSubChannelEvent {

    /**
     * The reward identifier.
     */
    private String id;

    /**
     * Whether the reward is currently enabled. If false, the reward won’t show up to viewers.
     */
    private Boolean isEnabled;

    /**
     * Whether the reward is currently paused. If true, viewers can’t redeem.
     */
    private Boolean isPaused;

    /**
     * Whether the reward is currently in stock. If false, viewers can’t redeem.
     */
    private Boolean isInStock;

    /**
     * The reward title.
     */
    private String title;

    /**
     * The reward cost.
     */
    private Integer cost;

    /**
     * The reward description.
     */
    private String prompt;

    /**
     * Whether the viewer needs to enter information when redeeming the reward.
     */
    private Boolean isUserInputRequired;

    /**
     * Whether redemptions should be set to fulfilled status immediately when redeemed and skip the request queue instead of the normal unfulfilled status.
     */
    private Boolean shouldRedemptionsSkipRequestQueue;

    /**
     * Whether a maximum per stream is enabled and what the maximum is.
     */
    private MaxPerStream maxPerStream;

    /**
     * Whether a maximum per user per stream is enabled and what the maximum is.
     */
    private MaxPerUserPerStream maxPerUserPerStream;

    /**
     * Custom background color for the reward.
     */
    private String backgroundColor;

    /**
     * Set of custom images of 1x, 2x and 4x sizes for the reward. Can be null if no images have been uploaded.
     */
    private CustomReward.Image image;

    /**
     * Set of default images of 1x, 2x and 4x sizes for the reward.
     */
    private CustomReward.Image defaultImage;

    /**
     * Whether a cooldown is enabled and what the cooldown is in seconds.
     */
    private GlobalCooldown globalCooldown;

    /**
     * Timestamp of the cooldown expiration. null if the reward isn’t on cooldown.
     */
    private Instant cooldownExpiresAt;

    /**
     * The number of redemptions redeemed during the current live stream.
     * Counts against the max_per_stream limit.
     * null if the broadcasters stream isn’t live or max_per_stream isn’t enabled.
     */
    private Integer redemptionsRedeemedCurrentStream;

}
