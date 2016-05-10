package com.henmory.anonymous.net;

import android.os.AsyncTask;

import com.henmory.anonymous.main.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by henmory on 2016/3/14.
 */
public class NetConnection {

    public NetConnection(final String url, final SuccessCallBack successCallBack, final FailedCallBack failedCallBack, final HttpMethord methord, final String ... kvs) {

        new AsyncTask<String, Void, String>(){

            @Override
            protected String doInBackground(String... params) {

                StringBuffer sb = new StringBuffer();
                for(int i = 0; i < kvs.length; i += 2) {
                    sb.append(kvs[i]).append("=").append(kvs[i + 1]).append("&");
                }

                URL urlString = null;
                URLConnection connection = null;
                try {
                    switch (methord){
                        case POST:
                            urlString = new URL(url);
                            connection = urlString.openConnection();
                            connection.setDoInput(true);
                            connection.setDoOutput(true);
                            connection.setConnectTimeout(5000);//设置超时时间，避免用户长时间等待
                            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(), Config.CHARSET);
                            BufferedWriter bw = new BufferedWriter(osw);
                            bw.write(sb.toString());//写到内存中
                            bw.flush();//发送出去
                            break;
                        case GET:
                            urlString = new URL(url + "?" + sb.toString());
                            connection = urlString.openConnection();
                            break;
                    }
                    System.out.println("para: " + sb.toString());

                    InputStreamReader isr = new InputStreamReader(connection.getInputStream(), Config.CHARSET);
                    BufferedReader rd = new BufferedReader(isr);
                    StringBuilder builder = new StringBuilder();
                    String line = null;
                    while ((line = rd.readLine()) != null){
                        builder.append(line);
                    }
                    return builder.toString();


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                if (result == null) {
                    if (failedCallBack != null) {
                        System.out.println("NetConnection failed");
                        failedCallBack.onFail();
                    }
                }else {
                    if(successCallBack != null){
                        System.out.println("NetConnection success!");
                        System.out.println("result = " + result);
                        successCallBack.onSucces(result);
                    }
                }
                super.onPostExecute(result);
            }
        }.execute(url);
    }

    public static interface SuccessCallBack{
        void onSucces(String result);
    }
    public static interface FailedCallBack{
        void onFail();
    }
}
