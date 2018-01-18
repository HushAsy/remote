package org.hhs;

import java.nio.ByteBuffer;

/**
 * Hello world!
 *
 */
public class App {

    public byte[] codeC(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] value = "Hello".getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        return new byte[10];
    }

}
