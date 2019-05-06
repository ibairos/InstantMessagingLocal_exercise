package publisher;

import util.Subscription_close;
import util.Message;
import util.Topic;
import java.util.ArrayList;
import java.util.List;
import subscriber.Subscriber;

public class PublisherImpl implements Publisher {

  private List<Subscriber> subscriberSet;
  private int numPublishers;
  private Topic topic;

    public PublisherImpl(Topic topic) {
        subscriberSet = new ArrayList<Subscriber>();
        numPublishers = 1;
        this.topic = topic;
    }

    @Override
    public void incPublishers() {
        numPublishers++;
    }

    @Override
    public int decPublishers() {
        numPublishers--;
        return numPublishers;
    }

    @Override
    public void attachSubscriber(Subscriber subscriber) {
        if (!subscriberSet.contains(subscriber)) {
            subscriberSet.add(subscriber);
        }
    }

    @Override
    public boolean detachSubscriber(Subscriber subscriber) {
        subscriber.onClose(new Subscription_close(topic, Subscription_close.Cause.SUBSCRIBER));
        return subscriberSet.remove(subscriber);
    }

    @Override
    public void detachAllSubscribers() {
        for (Subscriber s : subscriberSet) {
            s.onClose(new Subscription_close(topic, Subscription_close.Cause.PUBLISHER));
        }
    }

    @Override
    public void publish(Message message) {
        for (Subscriber s : subscriberSet) {
            s.onMessage(message);
        }
    }
}
