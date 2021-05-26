package sample;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class
Security
{

    public void writeKeys() throws NoSuchAlgorithmException, IOException
    {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        Key pub = kp.getPublic();
        Key pvt = kp.getPrivate();


        FileOutputStream out = new FileOutputStream("src/Keys/priv.key", false);
        out.write(pvt.getEncoded());
        out.close();
        //System.err.println("Private key format: " + pvt.getFormat());
        // prints "Private key format: PKCS#8" on my machine

        out = new FileOutputStream("src/Keys/pub.key", false);
        out.write(pub.getEncoded());
        out.close();
        //System.err.println("Public key format: " + pub.getFormat());
        // prints "Public key format: X.509" on my machine

    }

    //RSA-2048
    public PrivateKey getPrivKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException
    {
        Path path = Paths.get("src\\Keys\\priv.key");
        byte[] bytes = Files.readAllBytes(path);

        /* Generate private key. */
        PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey pvt = kf.generatePrivate(ks);
        //System.out.println("here we print the priv key: " + pvt);
        return pvt;
    }

    //RSA-2048
    public PublicKey getPublKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        /* Read all the public key bytes */
        Path path = Paths.get("src\\Keys\\pub.key");
        byte[] bytes = Files.readAllBytes(path);

        /* Generate public key. */
        X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey pub = kf.generatePublic(ks);
        //System.out.println("here we print the priv key: " + pub);
        return pub;
    }

    public byte[] encrypt(String plainText, PublicKey publicKey) throws Exception
    {
        //Get Cipher Instance RSA With ECB Mode and OAEPWITHSHA-512ANDMGF1PADDING Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");

        //Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        //Perform Encryption
        byte[] cipherText = cipher.doFinal(plainText.getBytes());

        return cipherText;
    }

    public String decrypt(byte[] cipherTextArray, PrivateKey privateKey) throws Exception
    {
        //Get Cipher Instance RSA With ECB Mode and OAEPWITHSHA-512ANDMGF1PADDING Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");

        //Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        //Perform Decryption
        byte[] decryptedTextArray = cipher.doFinal(cipherTextArray);

        return new String(decryptedTextArray);
    }

    //The key here comes out encoded already thats why we return a String
    public String encodedaesKey()
    {
        KeyGenerator keyGen;

        try
        {
            keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            System.out.println(encodedKey);
            return encodedKey;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }
    //We take  the String of the encoded text and we decoded to get back our original key AES-256
    public SecretKey decodedaesKey(String encodedKey)
    {
        // decode the base64 encoded string
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        // rebuild key using SecretKeySpec
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        return originalKey;
    }

    public String sha256(String password, byte[] salt) {

        String hashedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes)
            {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }

    public byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    //Virtual Signature Method
    public void SingVir() throws IOException, SignatureException
    {
        //todo add file here for signature
        String inputfile = null;
        String outputfile = null;

        Signature sign = null;
        try
        {
            sign = Signature.getInstance("SHA256withRSA");
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        try
        {
            sign.initSign(getPrivKey());
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | IOException e)
        {
            e.printStackTrace();
        }

        try (InputStream in = new FileInputStream(inputfile))
        {
            byte[] buf = new byte[2048];
            int len;
            while ((len = in.read(buf)) != -1)
            {
                sign.update(buf, 0, len);
            }
        }

        try (OutputStream out = new FileOutputStream(outputfile))
        {
            byte[] signature = sign.sign();
            out.write(signature);
        }
    }

    public void checkSign() throws SignatureException, IOException
    {
        //todo add file here for signature
        String inputfile = null;
        String outputfile = null;

        Signature sign = null;
        try
        {
            sign = Signature.getInstance("SHA256withRSA");
            sign.initVerify(getPublKey());
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException e)
        {
            e.printStackTrace();
        }

        InputStream in = null;
        try
        {
            in = new FileInputStream(inputfile);
            byte[] buf = new byte[2048];
            int len;
            while ((len = in.read(buf)) != -1)
            {
                sign.update(buf, 0, len);
            }
        } finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        /* Read the signature bytes from file */
        Path path = Paths.get(outputfile);
        byte[] bytes = Files.readAllBytes(path);
        System.out.println(inputfile + ": Signature " + (sign.verify(bytes) ? "OK" : "Not OK"));
    }

}