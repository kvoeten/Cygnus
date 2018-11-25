/*
 * Copyright (C) 2018 Kaz Voeten
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package server;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;

/**
 * @author Kaz Voeten
 */
public class APIFactory extends Thread {

    public static HashMap<String, Date> mIPBan = new HashMap<>(), mHWIDBan = new HashMap<>(), mMACBan = new HashMap<>();
    private static APIFactory instance;
    private static final OkHttpClient client = new OkHttpClient();

    @Override
    public void run() {
        GetBlockList();
        while (true) {
            try {
                Thread.sleep(5 * 60 * 1000); //5 minutes.
                GetBlockList();
            } catch (InterruptedException ex) {
                Logger.getLogger(APIFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void GetBlockList() {
        Request request = new Request.Builder()
                .url(Configuration.AUTH_API_URL + "/blocklist")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("[APIFactory] Unexpected response " + response);
                    }
                    JSONObject pBlockList = new JSONObject(responseBody.string());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    JSONArray pIPBlock = pBlockList.getJSONArray("ip");
                    JSONArray pHWIDBlock = pBlockList.getJSONArray("hwid");
                    JSONArray pMACBlock = pBlockList.getJSONArray("mac");

                    //IP
                    pIPBlock.forEach((pBlock) -> {
                        JSONObject pJSONBlock = (JSONObject) pBlock;
                        try {
                            if ((sdf.parse(pJSONBlock.getString("pBanEndDate"))).before(new Date())) {
                                mIPBan.put(pJSONBlock.getString("sIP"), sdf.parse(pJSONBlock.getString("pBanEndDate")));
                            }
                        } catch (ParseException ex) {
                            Logger.getLogger(APIFactory.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                    //HWID
                    pHWIDBlock.forEach((pBlock) -> {
                        JSONObject pJSONBlock = (JSONObject) pBlock;
                        try {
                            if ((sdf.parse(pJSONBlock.getString("pBanEndDate"))).before(new Date())) {
                                mHWIDBan.put(pJSONBlock.getString("sHWID"), sdf.parse(pJSONBlock.getString("pBanEndDate")));
                            }
                        } catch (ParseException ex) {
                            Logger.getLogger(APIFactory.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                    //MAC
                    pMACBlock.forEach((pBlock) -> {
                        JSONObject pJSONBlock = (JSONObject) pBlock;
                        try {
                            if ((sdf.parse(pJSONBlock.getString("pBanEndDate"))).before(new Date())) {
                                mMACBan.put(pJSONBlock.getString("sMAC"), sdf.parse(pJSONBlock.getString("pBanEndDate")));
                            }
                        } catch (ParseException ex) {
                            Logger.getLogger(APIFactory.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                    System.out.println("[Info] Updated blocklist. Now contains "
                            + (mIPBan.size() + mHWIDBan.size() + mMACBan.size())
                            + " rules.");
                } catch (Exception ex) {
                    Logger.getLogger(APIFactory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public static APIFactory GetInstance() {
        if (instance == null) {
            instance = new APIFactory();
        }
        return instance;
    }
}
