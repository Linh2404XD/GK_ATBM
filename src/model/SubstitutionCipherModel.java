package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SubstitutionCipherModel {
    private Map<Character, Character> substitutionMap; // Bảng thay thế
    private Map<Character, Character> reverseMap;      // Bảng thay thế ngược (giải mã)
    private String key;

    public SubstitutionCipherModel() {
        substitutionMap = new HashMap<>();
        reverseMap = new HashMap<>();
        key = " ";
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


    // Phương thức tải khóa từ file
    public void loadKey(File selectedFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
            String fileContent = reader.readLine(); // Đọc một dòng duy nhất từ file
            if (fileContent != null && fileContent.length() == 26) {
                // Kiểm tra khóa hợp lệ (có đủ 26 ký tự và chỉ bao gồm các chữ cái)
                this.key = fileContent.trim().toUpperCase(); // Khóa phải là chữ hoa
            } else {
                throw new IllegalArgumentException("Khóa không hợp lệ. Khóa phải chứa đủ 26 ký tự.");
            }
        } catch (IOException e) {
            throw new IOException("Lỗi khi đọc file: " + e.getMessage());
        }
    }

    // Getter cho khóa
    public String getKey() {
        return this.key;
    }
}

