package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    private static HttpURLConnection connection;
    private static HttpURLConnection connection2;

    public static void main(String[] args) {
        BufferedReader br;
        BufferedReader br2;
        String line;
        StringBuffer responseContent = new StringBuffer();
        StringBuffer responseContent2 = new StringBuffer();

        String zipcode = "72716";
        String location = "London,uk";

        String API_key = "43457004b4bdb6d075ad08366d4e686e";
        try {
            //Geo-location service
            URL url = new URL("https://www.zipcodeapi.com/rest/vtdMA2OTcUCG685hnmCCriBglpuB7BdNiaSza7sFtaHxhNamORp0pzmdjtuRbqfO/info.json/" + zipcode + "/degrees");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            //Weather service
            URL url2 = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + API_key);
            connection2 = (HttpURLConnection) url2.openConnection();
            connection2.setRequestMethod("POST");
            connection2.setConnectTimeout(10000);
            connection2.setReadTimeout(10000);

            int status = connection.getResponseCode();
            int status2 = connection2.getResponseCode();


            //Geo-location service
            if (status > 299) {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = br.readLine()) != null) {
                    responseContent.append(line);
                }
                br.close();
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = br.readLine()) != null) {
                    responseContent.append(line);
                }
            }

            //Weather service
            if (status2 > 299) {
                br2 = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = br2.readLine()) != null) {
                    responseContent2.append(line);
                }
                br2.close();
            } else {
                br2 = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
                while ((line = br2.readLine()) != null) {
                    responseContent2.append(line);
                }
                br2.close();
            }

            System.out.println("HTTP Status: " + status2);
            System.out.println(responseContent2);
            System.out.println("--------------------------------------");
            System.out.println("HTTP Status: " + status);
            System.out.println("Information by zipcode: " + responseContent.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
            connection2.disconnect();
        }
    }
}
