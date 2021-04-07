package ru.job4j.pooh;

import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    //ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
    private final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

    @Override
    public Resp process(Req req) {
        if (req.method().equals("POST")) {
            post(req);
            System.out.println(req.method());
            return new Resp(req.text(), 204);
        }
        if (req.method().equals("GET")) {
            System.out.println(req.method());
            return get(req);
        }
        return null;
    }

    private void post(Req req) {
        queue.offer(req.text());
    }

    private Resp get(Req req) {
        return new Resp(queue.poll(), 200);
    }
}
