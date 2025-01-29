import java.util.Scanner;
public class CipherCaesar {
    private int shiftValue;
    public CipherCaesar(int shiftValue) {
        this.shiftValue = shiftValue % 26;
        if(shiftValue < 0) {
            shiftValue += 26;
        }
    }
    private String encrypt(String plainText) {
        String cipherText = "";
        for(int index = 0; index < plainText.length(); index++) {
            char plainTextChar = plainText.charAt(index);
            int plainTextCharAscii = (int) plainTextChar;
            int cipherTextCharAscii = (plainTextCharAscii + this.shiftValue - 'A') % 26 + 'A';
            char cipherTextChar = (char) cipherTextCharAscii;
            cipherText += cipherTextChar;
        }
        return cipherText;
    }
    private String decrypt(String cipherText) {
        String plainText = "";
        for(int index = 0; index < cipherText.length(); index++) {
            char cipherTextChar = cipherText.charAt(index);
            int cipherTextCharAscii = (int) cipherTextChar;
            int plainTextCharAscii = cipherTextCharAscii - this.shiftValue;
            if(plainTextCharAscii < 'A') {
                plainTextCharAscii += 26;
            }
            char plainTextChar = (char) plainTextCharAscii;
            plainText += plainTextChar;
        }
        return plainText;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the plain text: ");
        String plainText = sc.nextLine();
        System.out.print("Enter the shift value: ");
        int shift = sc.nextInt();
        CipherCaesar cipherCaesar = new CipherCaesar(shift);
        String encryptedText = cipherCaesar.encrypt(plainText);
        String decryptedText = cipherCaesar.decrypt(encryptedText);
        System.out.println("Encrypted message: " + encryptedText);
        System.out.println("Decrypted message: " + decryptedText);
        sc.close();
    }
}