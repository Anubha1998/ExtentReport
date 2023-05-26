package com.lambdatest.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;


public class LambdaTestApi {

    public void setup(String config_file) throws FileNotFoundException, IOException, ParseException {

        JSONParser parser = new JSONParser();

    }

    public void markTestStatus(String session_id, String status,String reason) throws URISyntaxException, IOException {
        URI uri = new URI("https://anubhs:OmOOOshGyRoUgkriuFPDqahTb1fHdRHGXFPyStZb3BEXIQi1hg@mobile-api.lambdatest.com/mobile-automation/api/v1/sessions/"+session_id+".json");
        HttpPut putRequest = new HttpPut(uri);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add((new BasicNameValuePair("status", status)));
        nameValuePairs.add((new BasicNameValuePair("reason", reason)));
        nameValuePairs.add((new BasicNameValuePair("reason", reason)));
        putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpClientBuilder.create().build().execute(putRequest);
    }

    public JSONObject getSessionDetails(String session_id) throws URISyntaxException, IOException, JSONException {
        URI uri = new URI("https://anubhas:OmOOOshGyRoUgkriuFPDqahTb1fHdRHGXFPyStZb3BEXIQi1hg@mobile-api.lambdatest.com/mobile-automation/api/v1/sessions/"+session_id);
        HttpGet getRequest = new HttpGet(uri);
        HttpResponse response =  HttpClientBuilder.create().build().execute(getRequest);
        String json_string = EntityUtils.toString(response.getEntity());
        JSONObject json_response = new JSONObject(json_string);
        return(json_response);
    }

    public JSONObject getVideo(String session_id) throws URISyntaxException, IOException, JSONException {
        URI uri = new URI("https://anubhas:OmOOOshGyRoUgkriuFPDqahTb1fHdRHGXFPyStZb3BEXIQi1hg@mobile-api.lambdatest.com/mobile-automation/api/v1/sessions/"+session_id+"/video");
        HttpGet getRequest = new HttpGet(uri);
        HttpResponse response =  HttpClientBuilder.create().build().execute(getRequest);
        String json_string = EntityUtils.toString(response.getEntity());
        JSONObject json_response = new JSONObject(json_string);
        return(json_response);
    }


    public static JSONObject getBuildDetails(String build_id) throws URISyntaxException, IOException, JSONException {
        URI uri = new URI("https://anubhas:OmOOOshGyRoUgkriuFPDqahTb1fHdRHGXFPyStZb3BEXIQi1hg@mobile-api.lambdatest.com/mobile-automation/api/v1/sessions/"+build_id+"/sessions.json");
        HttpGet getRequest = new HttpGet(uri);
        HttpResponse response =  HttpClientBuilder.create().build().execute(getRequest);
        String json_string = EntityUtils.toString(response.getEntity());
        JSONObject json_response = new JSONObject(json_string);
        return(json_response);
    }

    public static void parseObject(JSONObject json, String key) throws JSONException {
        // System.out.println(json.has(key));
        System.out.println(json.get(key));
    }

    public String getValue(JSONObject json, String key) throws JSONException {

        boolean exists = json.has(key);
        Iterator<?> keys;
        String nextKeys;

        if (!exists) {
            keys = json.keys();
            while (keys.hasNext()) {
                nextKeys = (String) keys.next();
                try {

                    if (json.get(nextKeys) instanceof JSONObject) {

                        if (exists == false) {
                            return getValue(json.getJSONObject(nextKeys), key);
                        }

                    } else if (json.get(nextKeys) instanceof JSONArray) {
                        JSONArray jsonarray = json.getJSONArray(nextKeys);
                        for (int i = 0; i < jsonarray.length(); i++) {
                            String jsonarrayString = jsonarray.get(i).toString();
                            JSONObject innerJSOn = new JSONObject(jsonarrayString);

                            if (exists == false) {
                                return getValue(innerJSOn, key);
                            }

                        }

                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }

            }

        } else {
            return (String) json.get(key);
        }
        return (String) json.get(key);

    }
}
