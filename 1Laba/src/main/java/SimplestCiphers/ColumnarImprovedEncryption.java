package SimplestCiphers;

import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.IntStream;

public class ColumnarImprovedEncryption {
    private String Alphabet;

    public ColumnarImprovedEncryption(){
        setAlphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    public ColumnarImprovedEncryption(String newAlphabet){
        setAlphabet(newAlphabet);
    }

    private void setAlphabet(String newAlphabet){
        Alphabet = newAlphabet;
    }

    private int[] generateKeyVector(String key){
        int[] intKeyArray = new int[key.length()];
        int indexToKeyArray = 1;
        int i = 0;
        while (i < Alphabet.length() && indexToKeyArray <= key.length() ){
            int number = -1;
            while (key.indexOf(Alphabet.charAt(i), (number + 1)) != -1) {
                number = key.indexOf(Alphabet.charAt(i), number + 1);
                intKeyArray[number] = indexToKeyArray;
                indexToKeyArray++;
            }
            i++;
        }

        return intKeyArray;
    }

    public String editKey(String key) {
        StringBuilder newKey = new StringBuilder();
        key = key.toUpperCase(Locale.ROOT);

        for (int i = 0; i < key.length(); i++) {
            if (Alphabet.indexOf(key.charAt(i)) != -1 ) {
                newKey.append(key.charAt(i));
            }
        }
        return newKey.toString();
    }

    private ArrayList<char []> generateArrayForEncrypt(String text, String key, int[] intKeyArray){
        ArrayList<char[]> textArray = new ArrayList<>();
        textArray.add(new char[key.length()]);

        int indexOfKeySymbol = 0;
        int indexOfTextSymbol = 0;
        int indexOfArrayRow = 0;
        // iterating throw the text
        while (indexOfTextSymbol < text.length()) {
            // finding max index of filling text on the current row of matrix
            int finalInd_of_key_letter = indexOfKeySymbol + 1;
            int border_ind_of_key_letter = IntStream.range(0, intKeyArray.length).filter(i -> intKeyArray[i] == finalInd_of_key_letter).findFirst().orElse(-1);

            // filling text to this row
            char[] currLastSet = textArray.get(indexOfArrayRow);
            for (int i = 0; i <= border_ind_of_key_letter; i++) {
                currLastSet[i] = text.charAt(indexOfTextSymbol);
                textArray.set(indexOfArrayRow, currLastSet);
                indexOfTextSymbol++;
                if (indexOfTextSymbol >= text.length()) {
                    break;
                }
            }

            // adding new row
            textArray.add(new char[key.length()]);
            indexOfArrayRow++;
            indexOfKeySymbol++;
            // refresh the value
            if (indexOfKeySymbol == key.length()) {
                indexOfKeySymbol = 0;
            }
        }

        return textArray;
    }

    public String encrypt(String text, String key) {
        text = text.toUpperCase(Locale.ROOT);
        key = key.toUpperCase(Locale.ROOT);
        int[] intKeyArray = generateKeyVector(key);
        ArrayList<char[]> textArray = generateArrayForEncrypt(text, key, intKeyArray);

        StringBuilder resultText = new StringBuilder();
        for (int ind = 0; ind < key.length(); ind++) {
            int finalInd = ind + 1;
            int columnIndex = IntStream.range(0, intKeyArray.length).filter(i -> intKeyArray[i] == finalInd).findFirst().orElse(-1);

            for (char[] chars : textArray) {
                if (Alphabet.indexOf(chars[columnIndex]) != -1) {
                    resultText.append(chars[columnIndex]);
                }
            }
        }

        return resultText.toString();
    }
    private ArrayList<char []> findSizeOfArray(String encryptText, String key, int [] intKeyArray){
        char[] keyArray = key.toCharArray();

        int maxCapacityOfTextArrayPart = 0;
        for (int i = 1; i <= keyArray.length; i++) {
            maxCapacityOfTextArrayPart += i;
        }
        int countOfTextArrayColumn = (int) (Math.ceil( (double) encryptText.length() / maxCapacityOfTextArrayPart) ) * keyArray.length;
        int maxCapacityOfTextArray = countOfTextArrayColumn / keyArray.length * maxCapacityOfTextArrayPart;

        int modNum;
        while (maxCapacityOfTextArray >= encryptText.length()) {
            if (countOfTextArrayColumn % keyArray.length == 0) {
                modNum = keyArray.length;
            } else {
                modNum = countOfTextArrayColumn % keyArray.length;
            }
            int finalModNum = modNum;
            maxCapacityOfTextArray -= IntStream.range(0, intKeyArray.length).filter(i -> intKeyArray[i] == finalModNum).findFirst().orElse(-1) + 1;
            countOfTextArrayColumn --;
        }
        countOfTextArrayColumn++;

        ArrayList<char[]> TextArray = new ArrayList<>();
        for (int i = 0; i <= countOfTextArrayColumn; i++) {
            TextArray.add(new char[key.length()]);
        }
        return TextArray;
    }

    private ArrayList<char []> generateArrayForDecrypt(String encryptText, String key, int[] intKeyArray){
        ArrayList<char[]> TextArray = findSizeOfArray(encryptText, key, intKeyArray);
        int indexOfTextSymbol = 0;
        int rowCount = TextArray.size();
        for (int columnIndex = 1; columnIndex <= key.length(); columnIndex++) {
            // index of current columnIndex in the column
            int finalColNum = columnIndex;
            int currentColumnIndex = IntStream.range(0, intKeyArray.length).filter(i -> intKeyArray[i] == finalColNum).findFirst().orElse(-1);
            int indexOfArrayColumn = 1;
            int numFillLet = 0;
            for (int rowNum = 0; rowNum < rowCount; rowNum++) {
                // index of columnIndex on the current row
                int finalCol_ind = indexOfArrayColumn;
                int tempColumnIndex = IntStream.range(0, intKeyArray.length).filter(i -> intKeyArray[i] == finalCol_ind).findFirst().orElse(-1);

                // num of fill letters from string
                numFillLet += tempColumnIndex + 1;
                if (currentColumnIndex <= tempColumnIndex && indexOfTextSymbol < encryptText.length()) {
                    if ( !( rowNum == rowCount - 1 && (numFillLet - (tempColumnIndex - currentColumnIndex) )> encryptText.length() )) {
                        char[] temp = TextArray.get(rowNum);
                        temp[currentColumnIndex] = encryptText.charAt(indexOfTextSymbol);
                        TextArray.set(rowNum, temp);
                        indexOfTextSymbol++;
                    }
                }
                indexOfArrayColumn++;
                if (indexOfArrayColumn > key.length()) {
                    indexOfArrayColumn = 1;
                }
            }
        }

        return TextArray;
    }

    public String decrypt(String encryptText, String key) {
        encryptText = encryptText.toUpperCase(Locale.ROOT);
        key = key.toUpperCase(Locale.ROOT);

        int [] keyArray = generateKeyVector(key);
        ArrayList<char[]> TextArray = generateArrayForDecrypt(encryptText, key, keyArray);
        StringBuilder resultText = new StringBuilder();
        for (char[] chars : TextArray) {
            for (int col = 0; col < keyArray.length; col++) {
                if (Alphabet.indexOf(chars[col]) != -1) {
                    resultText.append(chars[col]);
                }
            }
        }

        return resultText.toString();
    }

}
