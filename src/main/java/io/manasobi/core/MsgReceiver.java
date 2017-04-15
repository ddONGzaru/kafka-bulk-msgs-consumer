package io.manasobi.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

/**
 * Created by manasobi on 2017-04-15.
 */
@Slf4j
public class MsgReceiver<T> {

    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(id = "manasobi", topics = "${kafka.topic}")
    public void receive(T message) {

        log.info("received message='{}'", message);

        //latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
