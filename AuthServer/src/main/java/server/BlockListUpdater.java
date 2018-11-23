/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import server.controller.BlockListController;
import static server.controller.BlockListController.sBlockList;
import server.hikari.Database;

/**
 *
 * @author Kaz Voeten
 */
public class BlockListUpdater extends Thread {

    @Override
    public void run() {
        Update();
        while (true) {
            try {
                Thread.sleep(5 * 60 * 1000); //refresh every 5 minutes.
            } catch (InterruptedException ex) {
                Logger.getLogger(BlockListUpdater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void Update() {
        try (Connection connection = Database.GetConnection()) {
            JSONObject pBlockListJSON = new JSONObject();

            //IP
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ipban");
            ResultSet rs = ps.executeQuery();
            JSONArray aBlockedIP = new JSONArray();
            while (rs.next()) {
                JSONObject pBlock = new JSONObject();
                pBlock.put("sIP", rs.getString("sIP"));
                pBlock.put("pBanDate", rs.getDate("pBanDate"));
                pBlock.put("pBanEndDate", rs.getDate("pBanEndDate"));
                aBlockedIP.put(pBlock);
            }

            //HWID
            ps = connection.prepareStatement("SELECT * FROM hwidban");
            rs = ps.executeQuery();
            JSONArray aBlockedHWID = new JSONArray();
            while (rs.next()) {
                JSONObject pBlock = new JSONObject();
                pBlock.put("sHWID", rs.getString("sHWID"));
                pBlock.put("pBanDate", rs.getDate("pBanDate"));
                pBlock.put("pBanEndDate", rs.getDate("pBanEndDate"));
                aBlockedHWID.put(pBlock);
            }

            //MAC
            ps = connection.prepareStatement("SELECT * FROM macban");
            rs = ps.executeQuery();
            JSONArray aBlockedMAC = new JSONArray();
            while (rs.next()) {
                JSONObject pBlock = new JSONObject();
                pBlock.put("sMAC", rs.getString("sMAC"));
                pBlock.put("pBanDate", rs.getDate("pBanDate"));
                pBlock.put("pBanEndDate", rs.getDate("pBanEndDate"));
                aBlockedMAC.put(pBlock);
            }

            rs.close();
            ps.close();

            //Finalize
            pBlockListJSON.put("ip", aBlockedIP);
            pBlockListJSON.put("hwid", aBlockedHWID);
            pBlockListJSON.put("mac", aBlockedMAC);
            sBlockList = pBlockListJSON.toString();
        } catch (SQLException ex) {
            Logger.getLogger(BlockListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
