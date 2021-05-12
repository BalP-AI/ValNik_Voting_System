package sample;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class SecurityHandler
{

    public void writePubKey()
    {

    }


    public RSAPrivateKey getPrivKey()
    {
        //get the private key
        File file = new File(PRIVATE_KEY); //add path file
        FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis);

        byte[] keyBytes = new byte[(int) file.length()];
        dis.readFully(keyBytes);
        dis.close();

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPrivateKey privKey = (RSAPrivateKey) kf.generatePrivate(spec);
        System.out.println("Exponent :" + privKey.getPrivateExponent());
        System.out.println("Modulus" + privKey.getModulus());
        return privKey;
    }

    public RSAPublicKey getPublKey()
    {
        //get the public key
        File file1 = new File(PUBLIC_KEY); //add path file
        FileInputStream fis1 = new FileInputStream(file1);
        DataInputStream dis1 = new DataInputStream(fis1);
        byte[] keyBytes1 = new byte[(int) file1.length()];
        dis1.readFully(keyBytes1);
        dis1.close();

        X509EncodedKeySpec spec1 = new X509EncodedKeySpec(keyBytes1);
        KeyFactory kf1 = KeyFactory.getInstance("RSA");
        RSAPublicKey pubKey = (RSAPublicKey) kf1.generatePublic(spec1);

        System.out.println("Exponent :" + pubKey.getPublicExponent());
        System.out.println("Modulus" + pubKey.getModulus());
        return pubKey;
    }

}