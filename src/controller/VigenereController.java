package controller;

import model.VigenereCipherModel;

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

    // Thiết lập khóa
    public void setKey(String key) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("Khóa không được để trống.");
        }
        for (char c : key.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new IllegalArgumentException("Khóa chỉ được chứa các ký tự chữ cái.");
            }
        }
        this.currentKey = key.toUpperCase();
    }

    // Lấy khóa hiện tại
    public String getKey() {
        return this.currentKey;
    }
}
