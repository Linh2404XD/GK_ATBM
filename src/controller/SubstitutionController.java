package controller;

import model.SubstitutionCipherModel;

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

}
