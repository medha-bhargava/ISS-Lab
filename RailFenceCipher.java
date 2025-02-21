import java.util.*;
public class RailFenceCipher {
    public static String encrypt(String text, int rails) {
        if (rails <= 1) {
            return text; 
        }
        char[][] rail = new char[rails][text.length()];
        boolean down = false;
        int row = 0, col = 0;
        for (char c : text.toCharArray()) {
            rail[row][col++] = c;
            if (row == 0 || row == rails - 1) {
                down = !down;
            }
            if (down) {
                row++;
            } else {
                row--;
            }
        }
        String encryptedText = "";
        for (char[] r : rail) {
            for (char ch : r) {
                if (ch != 0) {
                    encryptedText += ch;
                }
            }
        }
        return encryptedText;
    }
    public static String decrypt(String cipherText, int rails) {
        if (rails <= 1) {
            return cipherText;
        }
        char[][] rail = new char[rails][cipherText.length()];
        boolean down = false;
        int row = 0, col = 0;
        for (int i=0; i<cipherText.length(); i++) {
            rail[row][col++] = '*';
            if (row == 0 || row == rails-1) {
                down = !down;
            }
            if (down) {
                row++;
            } else {
                row--;
            }
        }
        int index = 0;
        for (int i=0; i<rails; i++) {
            for (int j=0; j<cipherText.length(); j++) {
                if (rail[i][j] == '*' && index < cipherText.length()) {
                    rail[i][j] = cipherText.charAt(index++);
                }
            }
        }
        row = 0;
        col = 0;
        down = false;
        String decryptedText = "";
        for (int i=0; i<cipherText.length(); i++) {
            decryptedText += rail[row][col++];
            if (row == 0 || row == rails - 1) {
                down = !down;
            }
            if (down) {
                row++;
            } else {
                row--;
            }
        }
        return decryptedText;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the plain text: ");
        String plainText = sc.nextLine();
        System.out.print("Enter the key value: ");
        int keyValue = sc.nextInt();
        String encryptedText = encrypt(plainText, keyValue);
        String decryptedText = decrypt(encryptedText, keyValue);
        System.out.println("Encrypted Text: " + encryptedText);
        System.out.println("Decrypted Text: " + decryptedText);
        sc.close();
    }   
}