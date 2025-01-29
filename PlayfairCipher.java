import java.util.Scanner;
public class PlayfairCipher {
    private char[][] keySquare;
    public PlayfairCipher(String key) {
        this.keySquare = generateKeySquare(key);
    }
    private char[][] generateKeySquare(String key) {
        char[][] keySquare = new char[5][5];
        int[] charFreq = new int[26];
        int index = 0;
        for(int i=0; i<key.length(); i++) {
            char ch = key.charAt(i);
            if(ch == 'J') {
                ch = 'I';
            }
            if(charFreq[ch - 'A'] == 0) {
                int row = index / 5;
                int col = index % 5;
                keySquare[row][col] = ch;
                index++;
                charFreq[ch - 'A']++;
            }
        }
        for(char ch='A'; ch<='Z'; ch++) {
            if(ch != 'J') {
                if(charFreq[ch - 'A'] == 0) {
                    int row = index / 5;
                    int col = index % 5;
                    keySquare[row][col] = ch;
                    index++;
                    charFreq[ch - 'A']++;
                }
            }
        }
        return keySquare;
    }
    private void printKeySquare() {
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                System.out.print(keySquare[i][j]+ " ");
            }
            System.out.println();
        }
    }
    private String getEncryptionForDiagraph(String diagraph) {
        int row1 = -1, row2 = -1, col1 = -1, col2 = -1;
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                if(keySquare[i][j] == diagraph.charAt(0)) {
                    row1 = i;
                    col1 = j;
                } else if(keySquare[i][j] == diagraph.charAt(1)) {
                    row2 = i;
                    col2 = j;
                }
            }
        }
        String diagraphEncryption = "";
        char firstChar, secondChar;
        if(row1 == row2) {
            firstChar = keySquare[row1][(col1 + 1) % 5];
            secondChar = keySquare[row2][(col2 + 1) % 5];
        } else if(col1 == col2) {
            firstChar = keySquare[(row1 + 1) % 5][col1];
            secondChar = keySquare[(row2 + 1) % 5][col2];
        } else {
            firstChar = keySquare[row1][col2];
            secondChar = keySquare[row2][col1];
        }
        diagraphEncryption = diagraphEncryption + firstChar + secondChar;
        return diagraphEncryption;
    }
    private String getEncryptedText(String plainText) {
        int index = 0;
        String encryptedText = "";
        while(index < plainText.length()) {
            String diagraph = "";
            char firstChar = plainText.charAt(index);
            diagraph = diagraph + firstChar;
            char filler = 'X';
            if(index < plainText.length() - 1) {
                char secondChar = plainText.charAt(index + 1);
                if(firstChar == secondChar) {
                    diagraph = diagraph + filler;
                    index++;
                } else {
                    diagraph = diagraph + secondChar;
                    index += 2;
                }
            } else {
                diagraph = diagraph + filler;
                index++;
            }
            encryptedText = encryptedText + getEncryptionForDiagraph(diagraph);
        }
        return encryptedText;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the key: ");
        String key = sc.nextLine();
        PlayfairCipher playfairCipher = new PlayfairCipher(key);
        System.out.println("Printing key square...");
        playfairCipher.printKeySquare();
        System.out.print("Enter the Plain Text: ");
        String plainText = sc.nextLine();
        plainText = plainText.replaceAll("J", "I"); // Extra addition
        String encryptedText = playfairCipher.getEncryptedText(plainText);
        System.out.println("Encrypted Text: " + encryptedText);
        sc.close();
    }
}