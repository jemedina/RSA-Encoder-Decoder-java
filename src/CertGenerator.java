import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;
import java.util.Scanner;

public class CertGenerator {
    final static String PUBLIC_FILE = "public.key";
    final static String PRIVATE_FILE = "private.key";

    public static void create() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec rsaPublicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        RSAPrivateCrtKeySpec rsaPrivateCrtKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateCrtKeySpec.class);

        writeFiles(PUBLIC_FILE, rsaPublicKeySpec.getModulus(), rsaPublicKeySpec.getPublicExponent());
        writeFiles(PRIVATE_FILE, rsaPrivateCrtKeySpec.getModulus(), rsaPrivateCrtKeySpec.getPrivateExponent());
    }
    private static void writeFiles(String fileName, BigInteger modulus, BigInteger exp) throws IOException {
        FileOutputStream fos = null;
        fos = new FileOutputStream(fileName);
        PrintWriter printWriter = new PrintWriter(fos);
        printWriter.println(new String(Base64.getEncoder().encode(modulus.toByteArray()), Charset.forName("UTF-8")));
        printWriter.println(new String(Base64.getEncoder().encode(exp.toByteArray()), Charset.forName("UTF-8")));
        printWriter.flush();
        System.out.println("Key saved: " + fileName);
        fos.close();
    }


    private static String[] readKeyFile(String fileName) throws FileNotFoundException {
        String [] out = new String [2];
        File myObj = new File(fileName);
        Scanner myReader = new Scanner(myObj);
        out[0] = myReader.nextLine();
        out[1] = myReader.nextLine();
        myReader.close();
        return out;
    }


    public static PublicKey getPublicKey() throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
        String [] strKey = readKeyFile(PUBLIC_FILE);
        BigInteger modulus = new BigInteger(Base64.getDecoder().decode(strKey[0]));
        BigInteger exp = new BigInteger(Base64.getDecoder().decode(strKey[1]));
        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exp);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(rsaPublicKeySpec);
    }

    public static PrivateKey getPrivateKey() throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
        String [] strKey = readKeyFile(PRIVATE_FILE);
        BigInteger modulus = new BigInteger(Base64.getDecoder().decode(strKey[0]));
        BigInteger exp = new BigInteger(Base64.getDecoder().decode(strKey[1]));
        RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, exp);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(rsaPrivateKeySpec);
    }
}
