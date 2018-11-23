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

import user.account.Account;
import auth.AuthServerSessionManager;
import auth.AuthServerSocket;
import auth.packet.Auth;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.LoginServerSocket;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;
import login.packet.Login;
import net.InPacket;
import user.UserStorage;

/**
 *
 * @author Kaz Voeten
 */
public class APIFactory {

    private static APIFactory instance;
    private static final OkHttpClient client = new OkHttpClient();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static APIFactory GetInstance() {
        if (instance == null) {
            instance = new APIFactory();
        }
        return instance;
    }

    public static void RequestAccount(LoginServerSocket pSocket, InPacket iPacket) {
        AuthServerSocket pAuthSocket = AuthServerSessionManager.pSession;
        if (pAuthSocket == null) {
            return;
        }
        
        long nSessionID = iPacket.DecodeLong();
        String sToken = iPacket.DecodeString();
        String sIP = iPacket.DecodeString();
        
        Request request = new Request.Builder()
                .addHeader("Authorization", "bearer " + sToken)
                .url(Configuration.AUTH_API_URL + "/account")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                pSocket.SendPacket(Login.AccountInformation(nSessionID, null, false));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("[APIFactory] Unexpected response " + response);
                    }

                    JSONObject SJONAccount = new JSONObject(responseBody.string());
                    
                    if (SJONAccount.getInt("nBanned") > 0) {
                        pSocket.SendPacket(Login.AccountInformation(nSessionID, null, true));
                        return;
                    }
                    
                    Account pAccount = new Account(
                            SJONAccount.getInt("nAccountID"),
                            nSessionID,
                            SJONAccount.getString("sAccountName"),
                            sIP,
                            SJONAccount.getString("sSecondPW"),
                            (byte) SJONAccount.getInt("nState"),
                            (byte) SJONAccount.getInt("nGender"),
                            sdf.parse(SJONAccount.getString("pLastLoadDate")),
                            sdf.parse(SJONAccount.getString("pBirthDate")),
                            sdf.parse(SJONAccount.getString("pCreateDate")),
                            (byte) SJONAccount.getInt("nGradeCode"),
                            (short) SJONAccount.getInt("nLastWorldID"),
                            SJONAccount.getInt("nNexonCash"),
                            SJONAccount.getInt("nMaplePoint"),
                            SJONAccount.getInt("nMileage")
                    );
                    
                    if (pAccount.nState > 0) {
                        pSocket.SendPacket(Login.AccountInformation(nSessionID, null, false));
                        return;
                    }

                    UserStorage.GetStorage().mAccountStorage.put(nSessionID, pAccount);
                    pSocket.SendPacket(Login.AccountInformation(nSessionID, pAccount, false));
                    pAuthSocket.SendPacket(Auth.SetState(pAccount.nAccountID, (byte) 1));
                    
                } catch (ParseException ex) {
                    pSocket.SendPacket(Login.AccountInformation(nSessionID, null, false));
                    Logger.getLogger(APIFactory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
