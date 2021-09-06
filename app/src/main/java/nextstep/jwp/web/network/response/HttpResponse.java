package nextstep.jwp.web.network.response;


import nextstep.jwp.web.controller.View;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class HttpResponse {

    private static final byte[] DEFAULT_BODY = "".getBytes();

    private final StatusLine statusLine;
    private final HttpHeaders headers;
    private byte[] body;

    public HttpResponse() {
        this.statusLine = new StatusLine(HttpStatus.OK);
        this.headers = new HttpHeaders();
        this.body = DEFAULT_BODY;
    }

    public byte[] print() {
        return concat(String.format("%s\r%n%s\r%n\r%n", statusLine.print(), headersFields()).getBytes(StandardCharsets.UTF_8), body);
    }

    private static byte[] concat(byte[]... bytesArr) {
        int flatSize = calculateFlatSize(bytesArr);
        ByteBuffer byteBuffer = ByteBuffer.allocate(flatSize);
        for (byte[] bytes : bytesArr) {
            byteBuffer.put(bytes);
        }
        return byteBuffer.array();
    }

    private static int calculateFlatSize(byte[][] bytesArr) {
        int size = 0;
        for (byte[] bytes : bytesArr) {
            size += bytes.length;
        }
        return size;
    }

    private String headersFields() {
        return headers.getAll();
    }

    public void setStatus(HttpStatus status) {
        this.statusLine.setStatus(status);
    }

    public void setBody(View view) {
        this.body = view.render();
        headers.put("Content-Type", view.getContentType());
        headers.put("Content-Length", String.valueOf(this.body.length));
    }

    public void setHeader(String key, String value) {
        this.headers.put(key, value);
    }
}
