package topicmanager;

import util.Subscription_check;
import util.Topic;
import util.Topic_check;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import publisher.Publisher;
import publisher.PublisherImpl;
import subscriber.Subscriber;

public class TopicManagerImpl implements TopicManager {

    private Map<Topic, Publisher> topicMap;

    public TopicManagerImpl() {
        topicMap = new HashMap<Topic, Publisher>();
    }

    @Override
    public Publisher addPublisherToTopic(Topic topic) {
        Topic_check tc = isTopic(topic);
        Publisher p;
        if (!tc.isOpen) {
            p = new PublisherImpl(topic);
            topicMap.put(topic, p);
        } else {
            p = topicMap.get(topic);
            p.incPublishers();
        }
        return p;
    }

    @Override
    public void removePublisherFromTopic(Topic topic) {
        if (topicMap.containsKey(topic)) {
            Publisher p = topicMap.get(topic);
            if (p.decPublishers() == 0) {
                p.detachAllSubscribers();
                topicMap.remove(topic);
            }
        }
    }

    @Override
    public Topic_check isTopic(Topic topic) {
        Topic_check tc;
        if (topicMap.containsKey(topic)) {
            tc = new Topic_check(topic, true);
        } else {
            tc =  new Topic_check(topic, false);
        }
        return tc;
    }

    @Override
    public List<Topic> topics() {
        return new ArrayList<Topic>(topicMap.keySet());
    }

    @Override
    public Subscription_check subscribe(Topic topic, Subscriber subscriber) {
        Subscription_check sc;
        if (topicMap.containsKey(topic)) {
            topicMap.get(topic).attachSubscriber(subscriber);
            sc = new Subscription_check(topic, Subscription_check.Result.OKAY);
        } else {
            sc = new Subscription_check(topic, Subscription_check.Result.NO_TOPIC);
        }
        return sc;
    }

    @Override
    public Subscription_check unsubscribe(Topic topic, Subscriber subscriber) {
        Subscription_check sc;
        if (topicMap.containsKey(topic)) {
            if (topicMap.get(topic).detachSubscriber(subscriber)) {
                sc = new Subscription_check(topic, Subscription_check.Result.OKAY);
            } else {
                sc = new Subscription_check(topic, Subscription_check.Result.NO_SUBSCRIPTION);
            }
        } else {
            sc = new Subscription_check(topic, Subscription_check.Result.NO_TOPIC);
        }
        return sc;
    }
    
}


