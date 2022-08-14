package SimplestCiphers;

import java.util.Locale;

import static java.lang.Integer.parseInt;

public class DecimationEncryption {
    private String Alphabet;

    public DecimationEncryption(){
        setAlphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    public DecimationEncryption(String newAlphabet){
        setAlphabet(newAlphabet);
    }

    private void setAlphabet(String newAlphabet){
        Alphabet = newAlphabet;
    }

    private  int gcdByEuclideanAlgorithm(int n1, int n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcdByEuclideanAlgorithm(n2, n1 % n2);
    }

    public int editKey(String key) {
        String keyAlphabet = "1234567890";
        StringBuilder newKey = new StringBuilder();

        for (int i = 0; i < key.length(); i++) {
            if (keyAlphabet.indexOf(key.charAt(i)) != -1) {
                newKey.append(key.charAt(i));
            }
        }

        if (!newKey.isEmpty()) {
            int intNewKey = parseInt(newKey.toString());
            if (gcdByEuclideanAlgorithm(intNewKey, Alphabet.length()) == 1) {
                return intNewKey;
            }
        }
        return 0;
    }

    /**
     *
     * @param text
     * @param key
     * @param state if state=true - Encrypt, else - Decrypt.
     * @return
     */
    public  String cipher(String text, int key, boolean state) {
        text = text.toUpperCase(Locale.ROOT);
        int cryptIndex;
        StringBuilder resultText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char temp = text.charAt(i);
            if (Alphabet.indexOf(temp)!= -1) {
                if (state) {
                    cryptIndex = (Alphabet.indexOf(temp) * key) % Alphabet.length();
                } else {
                    cryptIndex = Alphabet.indexOf(temp);
                    while (cryptIndex % key != 0) {
                        cryptIndex += Alphabet.length();
                    }
                    cryptIndex /= key;
                }
                resultText.append(Alphabet.charAt(cryptIndex));
            } else {
                resultText.append(temp);
            }
        }

        return resultText.toString();
    }

}
