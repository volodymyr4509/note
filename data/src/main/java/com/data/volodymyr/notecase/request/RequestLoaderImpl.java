package com.data.volodymyr.notecase.request;

import android.util.Log;

import com.data.volodymyr.notecase.util.RequestMethod;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by volodymyr on 06.02.16.
 */
public class RequestLoaderImpl implements RequestLoader {
    private static final String TAG = "RequestLoaderImpl";
    private static final int REQUEST_TIMEOUT = 5000;

    public String makeGet(String myurl) throws IOException {
        return makeRequestWithoutBody(myurl, RequestMethod.GET);
    }

    public String makePut(String url, byte[] data) throws IOException {
        return makeRequestWithBody(url, data, RequestMethod.PUT);
    }

    @Override
    public String makePost(String url, byte[] data) throws IOException {
        return makeRequestWithBody(url, data, RequestMethod.POST);
    }

    @Override
    public String makeDelete(String url) throws IOException {
        return makeRequestWithoutBody(url, RequestMethod.DELETE);
    }

    private String makeRequestWithoutBody(String myurl, RequestMethod method) throws IOException {
        InputStream is = null;
        String content = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(myurl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method.toString());
            conn.setReadTimeout(REQUEST_TIMEOUT);
            conn.connect();
            int response = conn.getResponseCode();

            Log.i(TAG, "Request: " + method + ", url: " + myurl + ", response code: " + response);
            is = conn.getInputStream();

            content = readIS(is);
        } finally {
            conn.disconnect();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

    private String makeRequestWithBody(String myurl, byte[] data, RequestMethod method) throws IOException {
        InputStream is = null;
        String content = null;
        DataOutputStream dos = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(myurl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method.toString());
            conn.setDoOutput(true);
            conn.setReadTimeout(REQUEST_TIMEOUT);
            conn.setConnectTimeout(REQUEST_TIMEOUT);

            conn.connect();
            int response = conn.getResponseCode();

            Log.i(TAG, "Request: " + method + ", url: " + myurl + ", response code: " + response);
            dos = new DataOutputStream(conn.getOutputStream());
            dos.write(data);

            is = conn.getInputStream();

            content = readIS(is);
        } finally {
            conn.disconnect();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

    private String readIS(InputStream stream) throws IOException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        BufferedReader bufferedReader = null;
        String result = "";
        try {
            String line;
            bufferedReader = new BufferedReader(reader);
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
        return result;
    }
}
