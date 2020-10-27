package com.github.twitch4j.pubsub;

import com.github.twitch4j.common.pool.TwitchModuleConnectionPool;
import com.github.twitch4j.pubsub.domain.PubSubRequest;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collection;
import java.util.stream.StreamSupport;

/**
 * A pool for PubSub connections to help navigate rate-limits.
 * <p>
 * Warning: Passing a {@link java.util.concurrent.ScheduledThreadPoolExecutor} with too small corePoolSize can lead to
 * connections in the pool not behaving properly. Not specifying an executor allows connections to create their own
 * at will. If enough connections are made, this could pollute one's runtime environment.
 */
@SuperBuilder
public class TwitchPubSubConnectionPool extends TwitchModuleConnectionPool<TwitchPubSub, PubSubRequest, PubSubSubscription, Class<Void>, TwitchPubSubBuilder> {

    private final String threadPrefix = "twitch4j-pool-" + RandomStringUtils.random(4, true, true) + "-pubsub-";

    @Override
    public PubSubSubscription subscribe(PubSubRequest pubSubRequest) {
        final int topics = getTopicCount(pubSubRequest);
        if (topics <= 0) return null;
        if (topics > 1) throw new IllegalArgumentException("TwitchPubSubConnectionPool can only handle PubSubRequest's with a single topic subscription");
        return super.subscribe(pubSubRequest);
    }

    @Override
    protected TwitchPubSub createConnection() {
        return advancedConfiguration.apply(
            TwitchPubSubBuilder.builder()
                .withEventManager(getConnectionEventManager())
                .withScheduledThreadPoolExecutor(getExecutor(threadPrefix + RandomStringUtils.random(4, true, true), TwitchPubSub.REQUIRED_THREAD_COUNT))
                .withProxyConfig(proxyConfig.get())
        ).build();
    }

    @Override
    protected void disposeConnection(TwitchPubSub connection) {
        connection.disconnect();
    }

    @Override
    protected PubSubSubscription handleSubscription(TwitchPubSub twitchPubSub, PubSubRequest pubSubRequest) {
        return twitchPubSub != null ? twitchPubSub.listenOnTopic(pubSubRequest) : null;
    }

    @Override
    protected PubSubSubscription handleDuplicateSubscription(TwitchPubSub twitchPubSub, PubSubRequest pubSubRequest) {
        return null;
    }

    @Override
    protected Class<Void> handleUnsubscription(TwitchPubSub twitchPubSub, PubSubSubscription pubSubSubscription) {
        if (twitchPubSub == null) return null;
        twitchPubSub.unsubscribeFromTopic(pubSubSubscription);
        return Void.TYPE;
    }

    @Override
    protected PubSubRequest getRequestFromSubscription(PubSubSubscription subscription) {
        return subscription.getRequest();
    }

    private static int getTopicCount(PubSubRequest req) {
        Object topics = req.getData().get("topics");
        if (topics instanceof Collection)
            return ((Collection<?>) topics).size();
        else if (topics instanceof Iterable)
            return (int) StreamSupport.stream(((Iterable<?>) topics).spliterator(), false).count();
        else if (topics instanceof Object[])
            return ((Object[]) topics).length;
        return -1;
    }

}
