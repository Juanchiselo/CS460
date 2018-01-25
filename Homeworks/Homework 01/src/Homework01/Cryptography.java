package Homework01;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Cryptography
{
    private static Cryptography instance = null;
    public Map<Character, Integer> letterToInt;
    public Map<Integer, Character> intToLetter;

    public static Cryptography getInstance()
    {
        if(instance == null)
            instance = new Cryptography();
        return instance;
    }

    private Cryptography()
    {
        letterToInt = new HashMap<>();
        intToLetter = new HashMap<>();

        for(int i = 0, j = 65; i <= 25; i++, j++)
        {
            letterToInt.put((char)j, i);
            intToLetter.put(i, (char)j);
        }
    }

    public String caesarCipher(int key, String text, String function)
    {
        char character;
        text = text.toUpperCase();
        String newText = "";

        switch(function)
        {
            case "ENCRYPT":
                key = Math.abs(key);
                break;
            case "DECRYPT":
                key = key * - 1;
                break;
            default:
                System.out.println("ERROR: Not a valid function");
        }

        for(int i = 0; i < text.length(); i++)
        {
            character = text.charAt(i);

            if(character >= 65 && character <= 90)
                newText += intToLetter.get(((letterToInt.get(character) + key) + 26) % 26);
            else
                newText += character;
        }

        return newText;
    }

    public String playfairCipher(String key, String text, String function)
    {
        char[][] keyMatrix;
        text = text.replace(" ", "").toUpperCase();
        key = key.toUpperCase() + "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        key = key.replace('J', 'I');
        keyMatrix = createKeyMatrix(key);

        switch(function)
        {
            case "ENCRYPT":
                text = padPlaintext(text);
                text = translatePairs(text, keyMatrix, function);
                break;
            case "DECRYPT":
                text = translatePairs(text, keyMatrix, function);
                text = unpadCiphertext(text);
                break;
            default:
                System.out.println("ERROR: Not a valid function");
        }

        return text;
    }

    private char[][] createKeyMatrix(String key)
    {
        char[][] keyMatrix = new char[5][5];

        Set<Character> characterSet = new LinkedHashSet<>();
        for (char character : key.toCharArray())
            characterSet.add(character);

        StringBuilder stringBuilder = new StringBuilder();
        for (Character character : characterSet)
            stringBuilder.append(character);
        key = stringBuilder.toString();

        int index = 0;
        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < 5; j++)
                keyMatrix[i][j] = key.charAt(index++);
        }

        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < 5; j++)
                System.out.print(keyMatrix[i][j] + " ");
            System.out.println();
        }

        return keyMatrix;
    }

    private String padPlaintext(String plaintext)
    {
        char first;
        char second;
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < plaintext.length(); i += 0)
        {
            first = plaintext.charAt(i);
            if(i + 1 < plaintext.length())
                second = plaintext.charAt(i + 1);
            else
                second = 'X';

            if(first == second)
            {
                second = 'X';
                i++;
            }
            else
                i += 2;

            stringBuilder.append(first);
            stringBuilder.append(second);
        }

        return stringBuilder.toString();
    }

    private String unpadCiphertext(String ciphertext)
    {
        for(int i = 0; i < ciphertext.length(); i++)
        {
            if(ciphertext.charAt(i) == 'X' && i != ciphertext.length() - 1)
            {
                if(ciphertext.charAt(i - 1) == ciphertext.charAt(i + 1))
                    ciphertext = ciphertext.replace("X", "");
            }
        }

        return ciphertext;
    }

    private String translatePairs(String text, char[][] matrix, String function)
    {
        StringBuilder stringBuilder = new StringBuilder();
        char first;
        char second;
        int firstRow = 0;
        int firstCol = 0;
        int secondRow = 0;
        int secondCol = 0;

        System.out.println(text);

        for(int i = 0; i < text.length(); i += 2)
        {
            first = text.charAt(i);
            second = text.charAt(i + 1);

            for(int row = 0; row < 5; row++)
            {
                for(int column = 0; column < 5; column++)
                {
                    if(first == matrix[row][column])
                    {
                        firstRow = row;
                        firstCol = column;
                    }

                    if(second == matrix[row][column])
                    {
                        secondRow = row;
                        secondCol = column;
                    }
                }
            }

            // Both letters fall in the same row.
            if(firstRow == secondRow)
            {
                switch (function)
                {
                    case "ENCRYPT":
                        firstCol = (firstCol + 1) % 5;
                        secondCol = (secondCol + 1) % 5;
                        break;
                    case "DECRYPT":
                        firstCol = ((firstCol - 1) + 5) % 5;
                        secondCol = ((secondCol - 1) + 5) % 5;
                        break;
                }

                stringBuilder.append(matrix[firstRow][firstCol]);
                stringBuilder.append(matrix[secondRow][secondCol]);
            }

            // Both letters fall in the same column.
            else if(firstCol == secondCol)
            {
                switch (function)
                {
                    case "ENCRYPT":
                        firstRow = (firstRow + 1) % 5;
                        secondRow = (secondRow + 1) % 5;
                        break;
                    case "DECRYPT":
                        firstRow = ((firstRow - 1) + 5) % 5;
                        secondRow = ((secondRow - 1) + 5) % 5;
                        break;
                }

                stringBuilder.append(matrix[firstRow][firstCol]);
                stringBuilder.append(matrix[secondRow][secondCol]);
            }

            // Intersection
            else
            {
                stringBuilder.append(matrix[firstRow][secondCol]);
                stringBuilder.append(matrix[secondRow][firstCol]);
            }
        }

        return stringBuilder.toString();
    }

    public String vigenereCipher(String key, String text, String function)
    {
        key = key.toUpperCase();
        text = text.replace(" ", "").toUpperCase();

        int[] keyIntegers = new int[key.length()];
        int[] textIntegers = new int[text.length()];

        for(int i = 0; i < key.length(); i++)
            keyIntegers[i] = letterToInt.get(key.charAt(i));

        for(int i = 0; i < text.length(); i++)
            textIntegers[i] = letterToInt.get(text.charAt(i));

        StringBuilder stringBuilder = new StringBuilder();
        char character;

        switch(function)
        {
            case "ENCRYPT":
                for(int i = 0; i < textIntegers.length; i++)
                {
                    character = intToLetter.get(
                            (textIntegers[i] + keyIntegers[i % keyIntegers.length]) % 26);
                    stringBuilder.append(character);
                }
                break;
            case "DECRYPT":
                for(int i = 0; i < textIntegers.length; i++)
                {
                    character = intToLetter.get(
                            ((textIntegers[i] - keyIntegers[i % keyIntegers.length]) + 26) % 26);
                    stringBuilder.append(character);
                }
                break;
            default:
                System.out.println("ERROR: Not a valid function");
        }

        return stringBuilder.toString();
    }
}
