package com.cinemo.metarbrowser.background;

import android.text.Html;

import com.cinemo.metarbrowser.db.entity.Info;
import com.cinemo.metarbrowser.util.Constants;
import com.cinemo.metarbrowser.util.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class NetworkRequest {

    public List<Info> fetchList(String stringURL) {
        List<Info> files = new ArrayList<>();
        URL url;
        URLConnection cn;
        try {
            url = new URL(stringURL);
            cn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(cn.getInputStream()));
            String line;
            while((line = in.readLine()) != null) {
                if (line.contains("href=\"ED")) {
                    String text = Html.fromHtml(line).toString().replace(".TXT", ".TXT ");
                    String[] split = text.split(" ");
                    Info info = new Info();
                    info.setId(split[0].substring(0, split[0].indexOf(".")));

                    String decode = fetchSingle(Constants.DECODED + split[0]);
                    String raw = fetchSingle(Constants.STATIONS + split[0]);

                    info.setDecode(decode);
                    info.setRaw(raw);
                    info.setLastUpdated(raw.split("\n")[0]);

                    files.add(info);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        Utils.writeLastUpdateDate();
        return files;
    }

    public String fetchSingle(String stringURL) {
        StringBuilder result = new StringBuilder();
        URL url;
        URLConnection cn;
        try {
            url = new URL(stringURL);
            cn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(cn.getInputStream()));
            String line;
            while((line = in.readLine()) != null) {
                    result.append(line).append("\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result.length() > 0 ? result.toString().substring(0, result.length()-1) : result.toString();
    }
}
