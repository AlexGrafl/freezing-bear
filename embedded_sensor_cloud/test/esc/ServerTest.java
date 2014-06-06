package esc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ServerTest {

    @Test
    public void testGetServerProperty() {
        String foo = Server.getServerProperty("foo");
        String port = Server.getServerProperty("port");
        assertNull(foo);
        assertEquals("8080", port);
    }
}