import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
 
public class CaesarCipher
{
    public final String ALPHABET = "abcdefghijklmnopqrstuvwxyz ";
    public final String OUTPUTFILE = "results.txt";
 
    public String encrypt(String plainText, int shiftKey)
    {
        plainText = plainText.toLowerCase();
        String cipherText = "";
        for (int i = 0; i < plainText.length(); i++)
        {
            int charPosition = ALPHABET.indexOf(plainText.charAt(i));
            int keyVal = (shiftKey + charPosition) % ALPHABET.length();
            char replaceVal = ALPHABET.charAt(keyVal);
            cipherText += replaceVal;
        }
        return cipherText;
    }
 
    public String decrypt(String cipherText, int shiftKey)
    {
        cipherText = cipherText.toLowerCase();
        String plainText = "";
        for (int i = 0; i < cipherText.length(); i++)
        {
            int charPosition = ALPHABET.indexOf(cipherText.charAt(i));
            int keyVal = (charPosition - shiftKey) % ALPHABET.length();
            if (keyVal < 0)
            {
                keyVal = ALPHABET.length() + keyVal;
            }
            if (' ' != ALPHABET.charAt((keyVal+shiftKey)%ALPHABET.length()))
            {
            	char replaceVal = ALPHABET.charAt(keyVal);
            	plainText += replaceVal;
            }
        }
        return plainText;
    }
 
}