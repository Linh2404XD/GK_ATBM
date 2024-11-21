package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TranspositionCipherModel {
    private int[] key;

    // Thiết lập khóa từ chuỗi nhập vào
    public void setKey(String keyInput) {
        try {
            String[] keyParts = keyInput.split(",");
            int[] newKey = new int[keyParts.length];
            for (int i = 0; i < keyParts.length; i++) {
                newKey[i] = Integer.parseInt(keyParts[i].trim());
            }

            // Kiểm tra tính hợp lệ của khóa
            if (!isValidKey(newKey)) {
                throw new IllegalArgumentException("Khóa không hợp lệ. Khóa phải là hoán vị của các số từ 1 đến " + newKey.length);
            }
            this.key = newKey;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Khóa phải là dãy các số nguyên, cách nhau bởi dấu phẩy.");
        }
    }

    public int[] getKey() {
        return key;
    }

    // Tạo khóa ngẫu nhiên
    public String generateKey(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Độ dài khóa phải lớn hơn 0.");
        }

        List<Integer> keyList = new ArrayList<>();
        for (int i = 1; i <= length; i++) {
            keyList.add(i);
        }

        Collections.shuffle(keyList); // Hoán vị ngẫu nhiên các phần tử
        key = keyList.stream().mapToInt(Integer::intValue).toArray();

        // Trả về khóa dưới dạng chuỗi
        StringBuilder generatedKey = new StringBuilder();
        for (int value : key) {
            if (generatedKey.length() > 0) {
                generatedKey.append(",");
            }
            generatedKey.append(value);
        }
        return generatedKey.toString();
    }

    // Kiểm tra tính hợp lệ của khóa
    private boolean isValidKey(int[] key) {
        boolean[] seen = new boolean[key.length];
        for (int value : key) {
            if (value < 1 || value > key.length || seen[value - 1]) {
                return false;
            }
            seen[value - 1] = true;
        }
        return true;
    }

    // Mã hóa
    public String encrypt(String plainText) {
        if (key == null) {
            throw new IllegalStateException("Chưa thiết lập khóa.");
        }

        int columns = key.length;
        int rows = (int) Math.ceil((double) plainText.length() / columns);
        char[][] grid = new char[rows][columns];

        // Điền văn bản vào lưới
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (index < plainText.length()) {
                    grid[i][j] = plainText.charAt(index++);
                } else {
                    grid[i][j] = ' '; // Điền khoảng trắng nếu thiếu
                }
            }
        }

        // Mã hóa theo thứ tự cột trong khóa
        StringBuilder encryptedText = new StringBuilder();
        for (int column : key) {
            for (int i = 0; i < rows; i++) {
                encryptedText.append(grid[i][column - 1]);
            }
        }
        return encryptedText.toString();
    }

    // Giải mã
    public String decrypt(String cipherText) {
        if (key == null) {
            throw new IllegalStateException("Chưa thiết lập khóa.");
        }

        int columns = key.length;
        int rows = (int) Math.ceil((double) cipherText.length() / columns);
        char[][] grid = new char[rows][columns];

        // Điền văn bản vào lưới theo thứ tự khóa
        int index = 0;
        for (int column : key) {
            for (int i = 0; i < rows; i++) {
                if (index < cipherText.length()) {
                    grid[i][column - 1] = cipherText.charAt(index++);
                } else {
                    grid[i][column - 1] = ' '; // Điền khoảng trắng nếu thiếu
                }
            }
        }

        // Giải mã bằng cách đọc theo hàng
        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                decryptedText.append(grid[i][j]);
            }
        }
        return decryptedText.toString().stripTrailing();
    }
}
