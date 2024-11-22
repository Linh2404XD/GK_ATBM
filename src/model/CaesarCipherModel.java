package model;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

public class CaesarCipherModel {

    private int key; // Lưu khóa cho thuật toán Caesar

    // Hàm khởi tạo
    public CaesarCipherModel() {
        this.key = 0; // Mặc định không có khóa
    }

    // Phương thức tạo khóa ngẫu nhiên
    public int generateKey() {
        Random random = new Random();
        this.key = random.nextInt(25) + 1; // Tạo số từ 1 đến 25
        return this.key;
    }

    // Phương thức tải khóa từ file
    public void loadKey(File file) throws IOException {
        String content = Files.readString(file.toPath()).trim(); // Đọc nội dung file
        try {
            this.key = Integer.parseInt(content); // Chuyển đổi nội dung thành số nguyên và lưu
            if (key < 1 || key > 25) {
                throw new IllegalArgumentException("Khóa phải nằm trong khoảng từ 1 đến 25.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("File phải chứa một số nguyên đại diện cho khóa.");
        }
    }

    // Lấy khóa hiện tại
    public int getKey() {
        return this.key;
    }

    // Mã hóa văn bản
    public static String encrypt(String text, int key) {
        StringBuilder encrypted = new StringBuilder();

        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                char encryptedChar = (char) ((character - base + key) % 26 + base);
                encrypted.append(encryptedChar);
            } else {
                encrypted.append(character); // Giữ nguyên các ký tự không phải chữ cái
            }
        }
        return encrypted.toString();
    }

    // Giải mã văn bản
    public static String decrypt(String text, int key) {
        StringBuilder decrypted = new StringBuilder();

        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                char decryptedChar = (char) ((character - base - key + 26) % 26 + base);
                decrypted.append(decryptedChar);
            } else {
                decrypted.append(character); // Giữ nguyên các ký tự không phải chữ cái
            }
        }
        return decrypted.toString();
    }

}

