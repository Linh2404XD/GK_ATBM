package controller;

import model.CaesarCipherModel;

public class CaesarController {

    private final CaesarCipherModel model;

    // Hàm khởi tạo
    public CaesarController(CaesarCipherModel model) {
        this.model = model;
    }

    // Tạo khóa ngẫu nhiên
    public int generateKey() {
        return model.generateKey();
    }

    // Thiết lập khóa từ người dùng
    public void setKey(String keyInput) {
        try {
            int key = Integer.parseInt(keyInput);
            model.setKey(key);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Khóa phải là số nguyên.");
        }
    }

    // Lấy khóa hiện tại
    public int getKey() {
        return model.getKey();
    }

    // Mã hóa văn bản
    public String handleEncryption(String plainText, int key) {
        return CaesarCipherModel.encrypt(plainText, key);
    }

    public String handleDecryption(String cipherText, int key) {
        return CaesarCipherModel.decrypt(cipherText, key);
    }
}
