package controller;

import model.SHA1Model;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class SHA1Controller {
    private SHA1Model sha1Model;

    // Constructor
    public SHA1Controller(SHA1Model sha1Model) {
        this.sha1Model = sha1Model;
    }

    // Phương thức hash cho văn bản
    public String hash(String data) {
        try {
            return sha1Model.hash(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "Lỗi thuật toán SHA-1.";
        }
    }

    // Phương thức hash cho file
    public String hashFile(String filePath) {
        try {
            return sha1Model.hashFile(filePath);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return "Lỗi khi đọc file hoặc tính toán hash.";
        }
    }
}