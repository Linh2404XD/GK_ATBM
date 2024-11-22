package controller;

import model.SubstitutionCipherModel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SubstitutionController {
    private final SubstitutionCipherModel model;
    Map<Character, Character> substitutionMap = new HashMap<>();
    public SubstitutionController(SubstitutionCipherModel model) {
        this.model = model;
    }

    // Tạo bảng thay thế ngẫu nhiên
    public Map<Character, Character> generateRandomSubstitutionMap() {

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String shuffledAlphabet = shuffleString(alphabet);

        for (int i = 0; i < alphabet.length(); i++) {
            substitutionMap.put(alphabet.charAt(i), shuffledAlphabet.charAt(i));
        }

        model.setSubstitutionMap(substitutionMap); // Thiết lập bảng thay thế trong model
        return substitutionMap;
    }
    public Map<Character, Character> getCurrentSubstitutionMap() {
        return substitutionMap;
    }

    // Thiết lập bảng thay thế thủ công
    public void setCustomSubstitutionMap(Map<Character, Character> map) {
        model.setSubstitutionMap(map);
    }

    // Xử lý mã hóa
    public String handleEncryption(String plainText) {
        return model.encrypt(plainText);
    }

    // Xử lý giải mã
    public String handleDecryption(String cipherText) {
        return model.decrypt(cipherText);
    }

    // Hàm hỗ trợ: Xáo trộn chuỗi ký tự
    private String shuffleString(String input) {
        StringBuilder builder = new StringBuilder(input);
        java.util.Random random = new java.util.Random();

        for (int i = builder.length() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = builder.charAt(i);
            builder.setCharAt(i, builder.charAt(j));
            builder.setCharAt(j, temp);
        }

        return builder.toString();
    }

    // Phương thức tải khóa từ file
    public void loadKey(File selectedFile) throws IOException {
        try {
            model.loadKey(selectedFile);  // Gọi phương thức loadKey trong Model để cập nhật key
            // Sau khi tải khóa, tạo lại bảng thay thế và cập nhật lại substitutionMap
            substitutionMap = generateSubstitutionMapFromKey(model.getKey());
            model.setSubstitutionMap(substitutionMap);  // Cập nhật substitutionMap trong model
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Lỗi khi tải khóa từ file: " + e.getMessage());
        }
    }

    // Tạo bảng thay thế từ key
    private Map<Character, Character> generateSubstitutionMapFromKey(String key) {
        Map<Character, Character> map = new HashMap<>();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // Dùng key để tạo bảng thay thế
        for (int i = 0; i < alphabet.length(); i++) {
            map.put(alphabet.charAt(i), key.charAt(i));  // Gán ký tự trong key cho bảng thay thế
        }

        return map;
    }

    // Getter cho khóa từ Model
    public String getKey() {
        return model.getKey();
    }
}
