package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TopicService implements Service {

    private static final AtomicInteger ID = new AtomicInteger(1);
    private final ConcurrentHashMap<
            String, ConcurrentHashMap<Integer, ConcurrentLinkedQueue<String>>
            > queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String nameQueue = req.getQueueName();
        String text;
        if (req.method().equals("POST")) {
            queue.putIfAbsent(nameQueue, new ConcurrentHashMap<>());
            text = req.text();
            int id = ID.get();
            queue.get(nameQueue).putIfAbsent(ID.getAndIncrement(), new ConcurrentLinkedQueue<>());
            queue.get(nameQueue).get(id).offer(text);
        } else {
            text = queue
                    .getOrDefault(nameQueue, new ConcurrentHashMap<>())
                    .getOrDefault(Integer.valueOf(req.id()), new ConcurrentLinkedQueue<>())
                    .poll();
        }
        return new Resp(text, 200);
    }
}
