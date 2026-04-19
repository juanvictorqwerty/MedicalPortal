package com.server.medportalserver.connection._common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class GenerateRandomCode {

    private final Random random = new Random();

    public String generateAdminConfirmCode() {
        List<Character> chars = new ArrayList<>();

        // Add 2 random digits (0-9)
        for (int i = 0; i < 2; i++) {
            chars.add((char) (random.nextInt(10) + '0'));
        }

        // Add 4 random uppercase letters (A-Z)
        for (int i = 0; i < 4; i++) {
            chars.add((char) (random.nextInt(26) + 'A'));
        }

        // Shuffle to randomize positions
        Collections.shuffle(chars);

        // Build string
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            sb.append(c);
        }

        return sb.toString();
    }
}