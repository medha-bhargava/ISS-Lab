import java.util.*;
public class VigenereCipher {
    public static int[] generatePTArray(String plainText) {
        int n = plainText.length();
        int[] pTValueArray = new int[n]; 
        for(int i=0; i<n; i++) {
            pTValueArray[i] = ((int)plainText.charAt(i)) % 26;
        }
        return pTValueArray;
    }
    public static int[] generateKTArray(String plainText, String keyText) {
        int len = plainText.length();
        int n = keyText.length();
        int[] kTValueArray = new int[len];
        for(int i=0; i<len; i++) {
            kTValueArray[i] = (int)keyText.charAt(i % n);
        }
        return kTValueArray;
    }
    public static String encrypt(String plainText, String keyText) {
        int n = plainText.length();
        String encryptedText = "";
        int[] plainTextArray = generatePTArray(plainText);
        int[] keyTextArray = generateKTArray(plainText, keyText);
        int[] cTValueArray = new int[n];
        for(int i=0; i<n; i++) {
            cTValueArray[i] = (plainTextArray[i] + keyTextArray[i]) % 26;
            encryptedText += (char)(cTValueArray[i] + 'A');
        }
        return encryptedText;
    }
    public static String decrypt(String encryptedText, String keyText) {
        int n = encryptedText.length();
        String decryptedText = "";
        int[] cipherTextArray = new int[n];
        for (int i = 0; i < n; i++) {
            cipherTextArray[i] = encryptedText.charAt(i) - 'A';
        }
        int[] keyTextArray = generateKTArray(encryptedText, keyText);
        for (int i = 0; i < n; i++) {
            keyTextArray[i] = keyTextArray[i] - 'A';
        }
        int[] pTValueArray = new int[n];
        for (int i = 0; i < n; i++) {
            pTValueArray[i] = (cipherTextArray[i] - keyTextArray[i] + 26) % 26;
            decryptedText += (char)(pTValueArray[i] + 'A');
        }
        return decryptedText;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the plain text: ");
        String plainText = sc.nextLine();
        System.out.print("Enter the key text: ");
        String keyText = sc.nextLine();
        String encryptedText = encrypt(plainText, keyText);
        String decryptedText = decrypt(encryptedText, keyText);
        System.out.println("Encrypted Text: " + encryptedText);
        System.out.println("Decrypted Text: " + decryptedText);
        sc.close();
    }
}