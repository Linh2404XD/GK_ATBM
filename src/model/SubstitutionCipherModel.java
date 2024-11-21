package model;

import java.util.HashMap;
import java.util.Map;

public class SubstitutionCipherModel {
    private Map<Character, Character> substitutionMap; // Bảng thay thế
    private Map<Character, Character> reverseMap;      // Bảng thay thế ngược (giải mã)

    public SubstitutionCipherModel() {
        substitutionMap = new HashMap<>();
        reverseMap = new HashMap<>();
    }

    // Thiết lập bảng thay thế
    public void setSubstitutionMap(Map<Character, Character> map) {
        this.substitutionMap = map;

        // Tự động tạo bảng thay thế ngược để giải mã
        reverseMap.clear();
        for (Map.Entry<Character, Character> entry : map.entrySet()) {
            reverseMap.put(entry.getValue(), entry.getKey());
        }
    }

    // Mã hóa văn bản
    public String encrypt(String plainText) {
        StringBuilder encryptedText = new StringBuilder();

        for (char c : plainText.toCharArray()) {
            if (Character.isLetter(c)) {
                char key = Character.toUpperCase(c);
                encryptedText.append(substitutionMap.getOrDefault(key, c)); // Thay thế ký tự
            } else {
                encryptedText.append(c); // Giữ nguyên ký tự không phải chữ
            }
        }

        return encryptedText.toString();
    }

    // Giải mã văn bản
    public String decrypt(String cipherText) {
        StringBuilder decryptedText = new StringBuilder();

        for (char c : cipherText.toCharArray()) {
            if (Character.isLetter(c)) {
                char key = Character.toUpperCase(c);
                decryptedText.append(reverseMap.getOrDefault(key, c)); // Thay thế ngược ký tự
            } else {
                decryptedText.append(c); // Giữ nguyên ký tự không phải chữ
            }
        }

        return decryptedText.toString();
    }
}

