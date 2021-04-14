package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    //private final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        if (req.method().equals("POST")) {
            post(req);
            return new Resp(req.text(), 204);
        }
        if (req.method().equals("GET")) {
            return get(req);
        }
        return null;
    }

    private void post(Req req) {
        queue.putIfAbsent(req.getQueueName(), new ConcurrentLinkedQueue<>());
        queue.get(req.getQueueName()).offer(req.text());
    }

    private Resp get(Req req) {
        String result = queue.get(req.getQueueName()).poll();
        return new Resp(result, 200);
    }
}
