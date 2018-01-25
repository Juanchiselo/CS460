package Homework01;

import java.util.*;

public class Cryptanalysis
{
    private static Cryptanalysis instance = null;
    private char[] letterFrequency;

    public static Cryptanalysis getInstance()
    {
        if(instance == null)
            instance = new Cryptanalysis();
        return instance;
    }

    private Cryptanalysis()
    {
        letterFrequency = new char[]
                {'E', 'T', 'A', 'O', 'I', 'N', 'S', 'H', 'R',
                'D', 'L', 'C', 'U', 'M', 'W', 'F', 'G', 'Y',
                'P', 'B', 'V', 'K', 'J', 'X', 'Q', 'Z'};
    }

    public String AnalyzeText(String ciphertext)
    {
        int[] counter = new int[26];
        float[] frequency = new float[26];
        int index;
        ciphertext = ciphertext.replace(" ", "").toUpperCase();
        Map<Character, Float> analyzedFrequency = new HashMap<>(26);
        String plaintext = "";
        ArrayList<Character> sortedFrequency = new ArrayList<>(26);

        for(int i = 0; i < ciphertext.length(); i++)
        {
            index = Cryptography.getInstance().letterToInt.get(ciphertext.charAt(i));
            counter[index]++;
        }

        System.out.println("Letter\tRelative Frequency");
        for(int i = 0; i < counter.length; i++)
        {
            frequency[i] = ((float) counter[i] / ciphertext.length()) * 100;
            analyzedFrequency.put(Cryptography.getInstance().intToLetter.get(i), frequency[i]);
        }

        Set<Map.Entry<Character, Float>> set = analyzedFrequency.entrySet();
        List<Map.Entry<Character, Float>> list = new ArrayList<>(set);

        Collections.sort( list, (o1, o2) -> (o2.getValue()).compareTo( o1.getValue() ));

        for(Map.Entry<Character, Float> entry:list)
        {
            System.out.println(entry.getKey() + "\t\t" + entry.getValue());
            sortedFrequency.add(entry.getKey());
        }

        for(int i = 0; i < ciphertext.length(); i++)
            plaintext += letterFrequency[sortedFrequency.indexOf(ciphertext.charAt(i))];

        // TODO: Create a set of possible plain letters for similar frequencies to offer more plaintexts.
        return plaintext;
    }
}
