/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author student
 */
public class NetworksUtils {

    public static String get(String address) {
        
        StringBuffer stringBuffer = new StringBuffer();
        
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-agent", "Mozilla/5.0");

            int requestCode = connection.getResponseCode();
            
            System.out.println("request - " + requestCode);
            
            if (requestCode!=200){
                System.out.println("Bad request");
                return "Bad request";
            }
            
            InputStreamReader is = new InputStreamReader(connection.getInputStream());
            BufferedReader in = new BufferedReader(is);
            
            String line;
            while ((line = in.readLine())!=null) {
                stringBuffer.append(line);
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(NetworksUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NetworksUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return stringBuffer.toString();
    }
}
