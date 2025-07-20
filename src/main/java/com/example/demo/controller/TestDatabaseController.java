package com.example.demo.controller; // Change to your package name

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
public class TestDatabaseController {
    @Autowired
    private DataSource dataSource;

    @GetMapping("/test-db")
    public String testDb() {
        try (Connection conn = dataSource.getConnection()) {
            return "Connected to: " + conn.getMetaData().getURL();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failed: " + e.getMessage();
        }
    }
}