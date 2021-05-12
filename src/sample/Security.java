package sample;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Security
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
        System.err.println("Private key format: " + pvt.getFormat());
        // prints "Private key format: PKCS#8" on my machine

        out = new FileOutputStream("src/Keys/pub.key", false);
        out.write(pub.getEncoded());
        out.close();
        System.err.println("Public key format: " + pub.getFormat());
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
        System.out.println("here we print the priv key: " + pvt);
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
        System.out.println("here we print the priv key: " + pub);
        return pub;
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