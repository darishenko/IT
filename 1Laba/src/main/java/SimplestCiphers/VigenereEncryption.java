package SimplestCiphers;

import java.util.Locale;

public class VigenereEncryption {
    private String Alphabet;

    public VigenereEncryption(){
        setAlphabet("АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ");
    }

    public VigenereEncryption(String newAlphabet){
        setAlphabet(newAlphabet);
    }

    private void setAlphabet(String newAlphabet){
        Alphabet = newAlphabet;
    }

    public String editKey(String key) {
        StringBuilder newKey = new StringBuilder();
        key= key.toUpperCase(Locale.ROOT);

        for (int i = 0; i < key.length(); i++) {
            if (Alphabet.indexOf(key.charAt(i)) != -1 ) {
                newKey.append(key.charAt(i));
            }
        }

        return newKey.toString();
    }

    /**
     * @param text
     * @param keyWord
     * @param state if state=true - Encrypt, else - Decrypt.
     * @return
     */
    public String cipher(String text, String keyWord, boolean state) {
        text = text.toUpperCase(Locale.ROOT);
        keyWord = keyWord.toUpperCase(Locale.ROOT);
        int keyLength = keyWord.length() -1;
        int indexOfKeyWord = 0, newIndex;
        StringBuilder resultText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char temp = text.charAt(i);
            if (Alphabet.indexOf(temp) != -1) {
                if (indexOfKeyWord > keyLength) indexOfKeyWord = 0;

                char tempKey = keyWord.charAt(indexOfKeyWord);
                if (state) {
                    newIndex = Alphabet.indexOf(temp) + Alphabet.indexOf(tempKey);
                } else{
                    newIndex = Alphabet.indexOf(temp) - Alphabet.indexOf(tempKey) + Alphabet.length();
                }
                newIndex %= Alphabet.length();

                resultText.append(Alphabet.charAt(newIndex));
                indexOfKeyWord++;
            } else {
                resultText.append(temp);
            }
        }

        return resultText.toString();
    }

}
