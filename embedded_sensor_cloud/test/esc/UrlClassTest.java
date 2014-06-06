package esc;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author Alex
 */
@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class UrlClassTest {

    @Test
    public void simpleValidUrlPasses() {
        UrlClass url = new UrlClass("/valid/simple/url");
        url.parseUrl();
        String[] expectedPath = {"valid", "simple", "url"};

        assertEquals("valid", url.getPluginPath());
        assertArrayEquals(expectedPath, url.getSplitFullPath());
        assertTrue(url.getParameters().isEmpty());
    }

    @Test
    public void urlEncodingFails() {
        UrlClass url = new UrlClass("/valid/simple/url");
        url.parseUrl();
        String[] expectedPath = {"valid", "simple", "url"};

        assertEquals("valid", url.getPluginPath());
        assertArrayEquals(expectedPath, url.getSplitFullPath());
        assertTrue(url.getParameters().isEmpty());
    }

    @Test
    public void complexValidUrlPasses(){
        UrlClass url = new UrlClass("/valid/complex/url.xml?parameter1=123&parameter2=asdf");
        url.parseUrl();
        String[] expectedPath =  {"valid", "complex", "url.xml"};
        FileThing expectedFile = new FileThing();
        expectedFile.setName("url");
        expectedFile.setExtension("xml");
        HashMap expectedMap = new HashMap<String, String>();
        expectedMap.put("parameter1", "123");
        expectedMap.put("parameter2", "asdf");

        assertEquals(expectedMap, url.getParameters());
        assertEquals(expectedFile.getName(), url.getFile().getName());
        assertEquals(expectedFile.getExtension(), url.getFile().getExtension());
        assertArrayEquals(expectedPath, url.getSplitFullPath());
        assertEquals("valid", url.getPluginPath());
    }

    @Test
    public void rootUrlPasses(){
        UrlClass url = new UrlClass("/");
        url.parseUrl();

        assertEquals("/", url.getPluginPath());
        assertTrue(url.getParameters().isEmpty());
    }

    @Test
    public void complexInvalidUrlPasses(){
        UrlClass url = new UrlClass("/foo//foo.bar?=bl%C3%9Fa*&a=b&moo='?sds&qwwe&ai");
        boolean ok = url.parseUrl();
        assertTrue(ok);

        String[] expectedPath = {"foo", "", "foo.bar"};
        FileThing expectedFile = new FileThing();
        expectedFile.setName("foo");
        expectedFile.setExtension("bar");
        HashMap expectedMap = new HashMap<String, String>();
        expectedMap.put("a", "b");
        expectedMap.put("moo", "'");

        assertEquals(expectedMap, url.getParameters());
        assertEquals(expectedFile.getName(), url.getFile().getName());
        assertEquals(expectedFile.getExtension(), url.getFile().getExtension());
        assertArrayEquals(expectedPath, url.getSplitFullPath());
        assertEquals("foo", url.getPluginPath());
        assertEquals("/foo//foo.bar?=blßa*&a=b&moo='?sds&qwwe&ai", url.getRawUrl());
        assertEquals("/foo//foo.bar", url.getFullPath());
    }

    @Test
    public void emptyKeyValuePair(){
        UrlClass url = new UrlClass("/findCompany");
        boolean ok = url.parseUrl();
        assertTrue(ok);
        url.parseParameters("asdf=&==&woop=1&&&=woo");
        HashMap expectedMap = new HashMap<String, String>();
        expectedMap.put("asdf", "");
        expectedMap.put("woop", "1");
        assertEquals(expectedMap, url.getParameters());
        assertEquals("", url.getParameterAsString("asdf"));
        assertEquals("1", url.getParameterAsString("woop"));
        assertTrue(url.hasParameters());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getParameterAsStringExeption(){
        UrlClass url = new UrlClass("/findCompany");
        boolean ok = url.parseUrl();
        assertTrue(ok);
        url.parseParameters("asdf=&==&woop=1&&&=woo");
        url.getParameterAsString("meh");

    }

    @Test
    public void parseUrlTestException(){
        UrlClass url = new UrlClass("");
        boolean ok = url.parseUrl();
        assertFalse(ok);
    }
    @Test
    public void parseParameterTestException(){
        try {
            UrlClass url = new UrlClass(URLEncoder.encode("/asdf/?%$äüö#!", "US-ASCII"));
            boolean ok = url.parseUrl();
            assertTrue(ok);
            url.parseParameters("&");
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }
}
