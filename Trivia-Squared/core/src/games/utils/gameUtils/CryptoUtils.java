package games.utils.gameUtils;


import games.containers.HighScore;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by Max Towery on 6/7/2015.
 */
public class CryptoUtils {

    private static String algorithm = "DESede";
    private static String password = "soury01";

    public static void main(String [] args) throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException, IOException {

        //this will be stored in the admin screen
        byte[] encryptionBytes = encryptF(password);

        //get encrypted password
        System.out.print("Encrypyed: ");
        for(int i = 0; i < encryptionBytes.length; i++){
            System.out.print(encryptionBytes[i]);
        }

        //get decrypted password
        System.out.println();
        System.out.println("Decrypted: " + decryptF(encryptionBytes));
    }


    //used to generate key for the first time
    public static void writeToFile(String filehandle, byte[] key) throws IOException {

        FileWriter fileWriter = new FileWriter(filehandle, false);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for(int i = 0; i < key.length; i++){
            printWriter.print(key[i]);
            printWriter.print("\n");
        }
        fileWriter.close();
    }

    public static byte[] readFile(String filehandle) throws IOException {

        byte[] encoded = new byte[24];
        Scanner fin = new Scanner(new File(filehandle));
        int i = 0;
        while(fin.hasNext()){
            encoded[i] = fin.nextByte();
            i++;
        }

        fin.close();

        return encoded;
    }

    private static byte[] encryptF(String input) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException, NoSuchPaddingException, NoSuchAlgorithmException {

        Key pkey = new SecretKeySpec(readFile("key.txt"), algorithm);
        Cipher c = Cipher.getInstance(algorithm);
        c.init(Cipher.ENCRYPT_MODE, pkey);
        byte[] inputBytes = input.getBytes();
        return c.doFinal(inputBytes);
    }

    private static String decryptF(byte[] encryptionBytes) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, IOException {
        Key pkey = new SecretKeySpec(readFile("key.txt"), algorithm);
        Cipher c = Cipher.getInstance(algorithm);
        c.init(Cipher.DECRYPT_MODE, pkey);

        byte[] decrypt = c.doFinal(encryptionBytes);

        String decrypted = new String(decrypt);
        return decrypted;
    }
}

