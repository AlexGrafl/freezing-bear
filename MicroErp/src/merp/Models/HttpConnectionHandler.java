package merp.Models;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HttpConnectionHandler {
    //private static String url = "http://localhost:8080/server/";
    private static String url = "http://192.168.1.18:8080/server/";

    /*******************************Contact*******************************/
    public boolean sendPostCreateContactRequest(String jsonObject) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url + "insertNewContact");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("json", jsonObject));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return true;
    }
    public boolean sendPostEditContactRequest(String jsonObject) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url + "editContact");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("json", jsonObject));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return true;
    }

    public String sendPostSearchContactRequest(String firstName, String lastName, String name, String uid) throws IOException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url + "searchContacts");
        List <NameValuePair> urlParameters = new ArrayList <NameValuePair>();
        if(!firstName.isEmpty()) urlParameters.add(new BasicNameValuePair("firstName", firstName));
        if(!lastName.isEmpty()) urlParameters.add(new BasicNameValuePair("lastName", lastName));
        if(!name.isEmpty()) urlParameters.add(new BasicNameValuePair("name", name));
        if(!uid.isEmpty()) urlParameters.add(new BasicNameValuePair("uid", uid));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(urlParameters, "UTF-8");
        httpPost.setEntity(urlEncodedFormEntity);
        HttpResponse response = client.execute(httpPost);
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }
    public String sendGetEditContactRequest(String contact) throws IOException {
        String responseBody ="";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(url + "editContacts?json=" + contact);

            System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            responseBody = httpClient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseBody;

    }
    public String sendGetCreateContactRequest(String contact) throws IOException {
        String responseBody ="";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(url + "insertNewContacts?json=" + contact);

            System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            responseBody = httpClient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseBody;

    }

    public String sendGetRequest(String searchString){
        String responseBody ="";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(url + "searchContacts?" + searchString);

            System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            responseBody = httpClient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseBody;

    }

    /*******************************Invoice*******************************/
    public boolean sendPostCreateInvoiceRequest(String jsonObject) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url + "createInvoice");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("json", jsonObject));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return true;
    }

    public String sendPostSearchInvoicesRequest(Date startDate, Date endDate, Double startAmount, Double endAmount, Integer contactId) throws IOException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url + "searchInvoices");
        List <NameValuePair> urlParameters = new ArrayList <NameValuePair>();
        System.out.println();
        if(startDate != null) urlParameters.add(new BasicNameValuePair("dateMin", Long.toString(startDate.getTime())));
        if(endDate != null) urlParameters.add(new BasicNameValuePair("dateMax", Long.toString(endDate.getTime())));
        if(startAmount != null) urlParameters.add(new BasicNameValuePair("totalMin", startAmount.toString()));
        if(endAmount != null) urlParameters.add(new BasicNameValuePair("totalMax", endAmount.toString()));
        if(contactId != null) urlParameters.add(new BasicNameValuePair("contactId", contactId.toString()));

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(urlParameters, "UTF-8");
        httpPost.setEntity(urlEncodedFormEntity);
        HttpResponse response = client.execute(httpPost);
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

}
