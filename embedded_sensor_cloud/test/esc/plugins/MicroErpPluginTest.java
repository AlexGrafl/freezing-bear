package esc.plugins;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class MicroErpPluginTest {

    @Test
    public void testAcceptRequestTrue() throws Exception {
        MicroErpPlugin microErpPlugin = new MicroErpPlugin();
        assertTrue(microErpPlugin.acceptRequest("server"));
    }
    @Test
    public void testAcceptRequestFalse() throws Exception {
        MicroErpPlugin microErpPlugin = new MicroErpPlugin();
        assertFalse(microErpPlugin.acceptRequest(null));
    }
}