package ru.job4j.pooh;

public class Req {
    private final String method;
    private final String mode;
    private final String text;
    private static String id;

    private Req(String method, String mode, String text) {
        this.method = method;
        this.mode = mode;
        this.text = text;
    }

    public static Req of(String content) {

        String method, mode, text = null;

        String[] s = content.split(" ");
        method = s[0];
        String[] s2 = s[1].split("/");
        mode = s2[1];
        if(method.equals("POST")) {
            String[] s3 = s[7].split("\r\n\r\n");
            text = s3[1];
        }
        if(s2.length > 3){
            id = s2[3];
        }
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

    public String id() {
        return id;
    }
}