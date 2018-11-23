/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import server.model.cygnus.GameAccount;

/**
 *
 * @author Kaz Voeten
 */
@RestController
public class GameAccountController {

    /**
     * Gets and returns the user's personal Game Account object.0
     * 
     * @return GameAccount
     */
    @GetMapping("/account")
    @PreAuthorize("hasAuthority('PRIVILEGE_USER_READ')")
    public ObjectNode account() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return GameAccount.GetAccount(auth.getName()).toObjectNode();
    }
}
