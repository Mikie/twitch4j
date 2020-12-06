package com.github.twitch4j.eventsub.subscriptions;

import com.github.twitch4j.eventsub.condition.ChannelBanCondition;

/**
 * A viewer is banned from the specified channel.
 * <p>
 * Must have channel:moderate scope.
 */
public class ChannelBanType implements SubscriptionType<ChannelBanCondition, ChannelBanCondition.ChannelBanConditionBuilder<?, ?>> {

    @Override
    public String getName() {
        return "channel.ban";
    }

    @Override
    public String getVersion() {
        return "1";
    }

    @Override
    public ChannelBanCondition.ChannelBanConditionBuilder<?, ?> getConditionBuilder() {
        return ChannelBanCondition.builder();
    }

}
