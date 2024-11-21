package model;

public class VigenereCipherModel {

    // Mã hóa văn bản bằng thuật toán Vigenère
    public String encrypt(String plainText, String key) {
        StringBuilder encryptedText = new StringBuilder();
        plainText = plainText.toUpperCase();
        key = generateFullKey(plainText, key).toUpperCase();

        for (int i = 0; i < plainText.length(); i++) {
            char plainChar = plainText.charAt(i);
            if (Character.isLetter(plainChar)) {
                int shift = key.charAt(i) - 'A';
                char encryptedChar = (char) ((plainChar - 'A' + shift) % 26 + 'A');
                encryptedText.append(encryptedChar);
            } else {
                encryptedText.append(plainChar); // Giữ nguyên ký tự không phải chữ
            }
        }

        return encryptedText.toString();
    }

    // Giải mã văn bản bằng thuật toán Vigenère
    public String decrypt(String cipherText, String key) {
        StringBuilder decryptedText = new StringBuilder();
        cipherText = cipherText.toUpperCase();
        key = generateFullKey(cipherText, key).toUpperCase();

        for (int i = 0; i < cipherText.length(); i++) {
            char cipherChar = cipherText.charAt(i);
            if (Character.isLetter(cipherChar)) {
                int shift = key.charAt(i) - 'A';
                char decryptedChar = (char) ((cipherChar - 'A' - shift + 26) % 26 + 'A');
                decryptedText.append(decryptedChar);
            } else {
                decryptedText.append(cipherChar); // Giữ nguyên ký tự không phải chữ
            }
        }

        return decryptedText.toString();
    }

    // Tạo khóa đầy đủ bằng cách lặp lại khóa ban đầu
    private String generateFullKey(String text, String key) {
        StringBuilder fullKey = new StringBuilder();
        int keyIndex = 0;
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                fullKey.append(key.charAt(keyIndex));
                keyIndex = (keyIndex + 1) % key.length();
            } else {
                fullKey.append(c); // Giữ nguyên các ký tự không phải chữ
            }
        }
        return fullKey.toString();
    }
}
