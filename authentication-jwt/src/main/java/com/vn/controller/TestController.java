package com.vn.controller;

import com.vn.Utils;
import com.vn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@PreAuthorize(value = "hasRole('MODERATOR') or hasRole('ADMIN')")
public class TestController {
    @Autowired
    private UserService userService;

    @GetMapping("/test/all")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(Utils.buildResponse(0,
                    null,
                    userService.findAll(),
                    null));
        } catch (Exception ex) {
            return ResponseEntity.ok(Utils.buildResponse(1, ex.getMessage(), null, null));
        }
    }


    @GetMapping("/test/[role]")
    public String getRole() {
        return "home";
    }


}
