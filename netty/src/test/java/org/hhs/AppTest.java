package org.hhs;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.hhs.serial.UserInfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue( true );
    }

    public void testSerial() throws IOException {
        UserInfo info = new UserInfo();
        info.buildUserID(100).buildUserName("Welcome to Netty");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(info);
        os.flush();
        os.close();
        byte[] b = bos.toByteArray();
        for (byte b1:b){
            System.out.println(Byte.valueOf(b1));
        }
//        System.out.println("The jdk serializable length is:"+Integer.toBinaryString(b));
        bos.close();
        System.out.println("----------------");
//        System.out.println("The byte array serializable length is:"+info.codeC().toString());
    }

    public void testSerialLoop() throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.buildUserID(100).buildUserName("Welcome to Netty");
        int loop = 1000000;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++){
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(userInfo);
            os.flush();
            os.close();
            byte[] b = bos.toByteArray();
            bos.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("the jdk serializable cost time is:" + (endTime-startTime) + "ms");
        System.out.println("---------------------------------------------------------------");
//        ByteBuffer buffer = ByteBuffer.allocate(1024);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++){
            byte[] b = userInfo.codeC();
        }
        endTime = System.currentTimeMillis();
        System.out.println("The byte array serializable cost time is :"+(endTime - startTime) + "ms");
    }
}
