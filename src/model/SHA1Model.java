package model;

import java.io.*;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Model {
    // Hash văn bản với SHA-1
    public String hash(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] bytes = data.getBytes();
        byte[] digest = md.digest(bytes);
        BigInteger re = new BigInteger(1, digest);
        return re.toString(16);  // Trả về kết quả dưới dạng chuỗi hex
    }

    // Hash file với SHA-1
    public String hashFile(String path) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        File file = new File(path);
        if (!file.exists()) return null;

        InputStream in = new BufferedInputStream(new FileInputStream(file));
        DigestInputStream dis = new DigestInputStream(in, md);

        byte[] buffer = new byte[1024];
        int byteRead;
        do {
            byteRead = dis.read(buffer);
        } while (byteRead != -1);

        BigInteger re = new BigInteger(1, dis.getMessageDigest().digest());
        return re.toString(16);  // Trả về kết quả dưới dạng chuỗi hex
    }

    // Hàm main để kiểm tra SHA-1
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        SHA1Model sha1Model = new SHA1Model();

        // Kiểm tra hash văn bản
        String text = "HELLO";
        System.out.println("Hash văn bản: " + sha1Model.hash(text));

        // Kiểm tra hash file
        String path = "C:\\Users\\nguye\\Downloads\\key.txt";
        System.out.println("Hash file: " + sha1Model.hashFile(path));
    }
}
