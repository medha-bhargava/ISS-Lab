import java.util.*;
public class HillCipher {
    public static int[][] generateKeyMatrix(String key) {
        int n = key.length();
        int mSize = (int) Math.sqrt(n);
        if(mSize * mSize != n) {
            System.out.println("Cannot form Square Matrix!");
        }
        int[][] keyMatrix = new int[mSize][mSize];
        int index = 0;
        for(int i=0; i<mSize; i++) {
            for(int j=0; j<mSize; j++) {
                keyMatrix[i][j] = (key.charAt(index)) % 65;
                index++;
            }
        }
        return keyMatrix;
    }
    public static String encrypt(String plainText, int[][] squareKeyMatrix) {
        int blockSize = squareKeyMatrix.length;
        int pTLen = plainText.length();
        String encryptedString = "";
        for(int i=0; i<pTLen; i+=blockSize) {
            String blockString = plainText.substring(i, i + blockSize);
            int[][] blockMatrix = new int[blockSize][1];
            for(int j=0; j<blockSize; j++) {
                blockMatrix[j][0] = (blockString.charAt(j)) % 65;
            }
            int[][] encyrptedBlockMatrix = new int[blockSize][1];
            for(int row=0; row<blockSize; row++) {
                for(int k=0; k<blockSize; k++) {
                    encyrptedBlockMatrix[row][0] += squareKeyMatrix[row][k] * blockMatrix[k][0];
                }
                encyrptedBlockMatrix[row][0] %= 26;
                encryptedString += (char)(encyrptedBlockMatrix[row][0] + 'A');
            }
        }
        return encryptedString;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the key text: ");
        String keyText = sc.nextLine();
        System.out.print("Enter the plain text: ");
        String plainText = sc.nextLine();
        int[][] sqKeyMatrix = generateKeyMatrix(keyText);
        String encryptedString = encrypt(plainText, sqKeyMatrix);
        System.out.println("Encrypted text for the plain text " + plainText + " is: " + encryptedString);
        sc.close();
    }
}