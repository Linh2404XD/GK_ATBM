package controller;

import model.TranspositionCipherModel;

import java.io.File;
import java.io.IOException;

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
    public String getKey() {
        int[] keyArray = model.getKey(); // Lấy khóa từ model
        if (keyArray != null && keyArray.length > 0) {
            StringBuilder keyString = new StringBuilder();
            for (int i = 0; i < keyArray.length; i++) {
                if (i > 0) {
                    keyString.append(", ");
                }
                keyString.append(keyArray[i]);
            }
            return keyString.toString();  // Trả về khóa dưới dạng chuỗi phân cách dấu phẩy
        }
        return "";  // Nếu không có khóa hợp lệ, trả về chuỗi rỗng
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

    public void loadKey(File selectedFile) throws IOException {
        try {
            model.loadKey(selectedFile); // Gọi phương thức loadKey trong model
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Lỗi khi tải khóa từ file: " + e.getMessage());
        }
    }
}
