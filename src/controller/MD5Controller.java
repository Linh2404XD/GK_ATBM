package controller;

import model.MD5Model;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class MD5Controller {
    private MD5Model model;

    public MD5Controller(MD5Model md5Model) {
        model = new MD5Model();
    }

    // Mã hóa chuỗi
    public String hash(String data) {
        try {
            return model.hash(data);
        } catch (NoSuchAlgorithmException e) {
            return "Lỗi: Thuật toán MD5 không khả dụng.";
        }
    }

    // Mã hóa file
    public String hashFile(String filePath) {
        try {
            return model.hashFile(filePath);
        } catch (NoSuchAlgorithmException e) {
            return "Lỗi: Thuật toán MD5 không khả dụng.";
        } catch (IOException e) {
            return "Lỗi: Không thể đọc file.";
        }
    }
}
