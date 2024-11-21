package controller;

import model.TranspositionCipherModel;

public class TranspositionController {
    private final TranspositionCipherModel model;

    public TranspositionController(TranspositionCipherModel model) {
        this.model = model;
    }

    // Thiết lập khóa
    public void setKey(String keyInput) {
        model.setKey(keyInput);
    }

    // Lấy khóa hiện tại
    public int[] getKey() {
        return model.getKey();
    }

    // Mã hóa văn bản
    public String handleEncryption(String plainText) {
        return model.encrypt(plainText);
    }

    // Giải mã văn bản
    public String handleDecryption(String cipherText) {
        return model.decrypt(cipherText);
    }

    // Tạo khóa ngẫu nhiên
    public String generateKey(int length) {
        return model.generateKey(length);
    }
}
