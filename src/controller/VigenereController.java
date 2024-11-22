package controller;

import model.VigenereCipherModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class VigenereController {
    private final VigenereCipherModel model;
    private String currentKey;

    public VigenereController(VigenereCipherModel model) {
        this.model = model;
        this.currentKey = ""; // Khóa mặc định ban đầu
    }

    // Xử lý mã hóa
    public String handleEncryption(String plainText, String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Khóa không được để trống.");
        }
        return model.encrypt(plainText, key);
    }

    // Xử lý giải mã
    public String handleDecryption(String cipherText, String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Khóa không được để trống.");
        }
        return model.decrypt(cipherText, key);
    }

    // Tạo khóa ngẫu nhiên
    public String generateKey() {
        int keyLength = 5; // Độ dài khóa mặc định
        StringBuilder generatedKey = new StringBuilder();
        for (int i = 0; i < keyLength; i++) {
            char randomChar = (char) ('A' + Math.random() * 26);
            generatedKey.append(randomChar);
        }
        this.currentKey = generatedKey.toString();
        return this.currentKey;
    }


    // Lấy khóa hiện tại
    public String getKey() {
        return model.getKey(); // Lấy khóa từ model
    }

    // Phương thức tải khóa từ file
    public void loadKey(File selectedFile) throws IOException {
        try {
            model.loadKey(selectedFile); // Gọi phương thức loadKey trong model
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Lỗi khi tải khóa từ file: " + e.getMessage());
        }
    }
}
