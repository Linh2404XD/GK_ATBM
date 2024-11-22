package controller;

import model.CaesarCipherModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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


    // Mã hóa văn bản
    public String handleEncryption(String plainText, int key) {
        return CaesarCipherModel.encrypt(plainText, key);
    }

    public String handleDecryption(String cipherText, int key) {
        return CaesarCipherModel.decrypt(cipherText, key);
    }

    // Phương thức loadKey
    public void loadKey(File file) throws IOException {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File không hợp lệ hoặc không tồn tại.");
        }
        // Gọi loadKey từ model để xử lý trực tiếp
        model.loadKey(file);
    }
    // Lấy khóa hiện tại
    public int getKey() {
        return model.getKey();
    }

}
