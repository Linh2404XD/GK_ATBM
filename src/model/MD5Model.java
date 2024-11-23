package model;

import java.io.*;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Model {
    public String hash(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = data.getBytes();
        byte[] digest = md.digest(bytes);
        BigInteger re = new BigInteger(1, digest);
        return re.toString(16);

    }
    public String hashFile (String path) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        File file = new File(path);
        if(!file.exists()) return null;
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        DigestInputStream dis = new DigestInputStream(in, md);

        byte[] buffer = new byte[1024];
        int byteRead = 0;
        do {
            byteRead = dis.read(buffer);
        } while (byteRead != -1 );
        BigInteger re = new BigInteger(1, dis.getMessageDigest().digest());
        return re.toString(16);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        MD5Model m = new MD5Model();
        String mess = "HELLO";
        String path = "C:\\Users\\nguye\\Downloads\\key.txt";
        System.out.println(m.hash(mess));
        System.out.println(m.hashFile(path));
    }
}
