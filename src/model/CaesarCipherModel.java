package model;

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

    // Phương thức thiết lập khóa
    public void setKey(int key) {
        if (key < 1 || key > 25) {
            throw new IllegalArgumentException("Khóa phải nằm trong khoảng từ 1 đến 25.");
        }
        this.key = key;
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

