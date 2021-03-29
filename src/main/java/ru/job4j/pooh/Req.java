package ru.job4j.pooh;

public class Req {
    private final String method;
    private final String mode;
    private final String text;

    private Req(String method, String mode, String text) {
        this.method = method;
        this.mode = mode;
        this.text = text;
    }

    public static Req of(String content) {
        String[] s = content.split(" ");
        String method = s[0];
        String[] s2 = s[1].split("/");
        String mode = s2[1];
        String[] s3 = s[7].split("\r\n\r\n");
        String text = s3[1];
        return new Req(method, mode, text);
    }

    public String method() {
        return method;
    }

    public String mode() {
        return mode;
    }

    public String text() {
        return text;
    }
}