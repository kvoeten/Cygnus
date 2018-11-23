/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Kaz Voeten
 */
@RestController
public class BlockListController {
    
    public static String sBlockList = "";

    @GetMapping("/blocklist")
    public String GetBlockList() {
        return sBlockList;
    }
}
