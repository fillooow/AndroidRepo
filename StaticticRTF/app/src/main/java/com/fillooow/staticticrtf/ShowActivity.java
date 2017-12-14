package com.fillooow.staticticrtf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.PriorityQueue;

public class ShowActivity extends AppCompatActivity {
    private String editString;
    private StringBuffer parsedPrefixString;
    private ArrayList<String> chars;
    private ArrayList<Integer> counters;
    private ArrayList<String> parsedChars;
    private ArrayList<Integer> parsedCounters;
    private ArrayList<String> parsedPrefix;
    private ArrayList<Double> parsedFrequency;
    private Double entropy;
    private double ratio;

    private TextView entropyTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        chars = new ArrayList<>();
        counters = new ArrayList<>();
        parsedChars = new ArrayList<>();
        parsedCounters = new ArrayList<>();
        parsedFrequency = new ArrayList<>();
        parsedPrefix = new ArrayList<>();
        entropy = 0.0;

        editString = getIntent().getStringExtra("editString");
        boolean registerOff = getIntent().getBooleanExtra("registerOff", false);
        boolean spacesOff = getIntent().getBooleanExtra("spacesOff", false);
        boolean marksOff = getIntent().getBooleanExtra("marksOff", false);
        boolean doubleChars = getIntent().getBooleanExtra("doubleChars", false);

        if (registerOff)
            editString = editString.toUpperCase();
        if (spacesOff)
            editString = editString.replaceAll("\\s+","");
        if (marksOff)
            editString = editString.replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "");
        if (!doubleChars) {
            dismemberOfText(editString);
        } else {
            editString = editString.replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "");
            doubleDismemberOfText(editString);
        }
        sortText(counters);

        entropy = entropy(counters, editString.length());
        //TODO: начиная отсюда
        HuffmanTree tree = buildTree(chars, counters);
        //SYMBOL	WEIGHT	HUFFMAN CODE	FREQUENCY
        parseTree(tree, new StringBuffer(), editString.length());
        if (doubleChars)
            printDoubleCodedStr(editString);
        else
            printCodedStr(editString);
        double tempRatioOriginal = editString.length()*24;
        double tempRatioReceived = parsedPrefixString.length()*2;
        ratio = tempRatioOriginal/tempRatioReceived;
        double ratioBit = ratio/8;

        AdapterRTF adapter = new AdapterRTF(chars, counters, editString.length());
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        entropyTV = (TextView) findViewById(R.id.entropyTV);
        entropyTV.setText(String.format(Locale.getDefault(), "%(.3f", entropy));
    }


    private void sortText(ArrayList<Integer> counters) {
        int low = 0;
        int high = counters.size();
        Log.d("qwe", "high = " + high + ", size = " + counters.size());
        quicksort(counters, low, high-1);
    }

    private void quicksort(ArrayList<Integer> counters, int low, int high) {
        int i = low;
        int j = high;
        Log.d("qwe", "j = " + j);
        int avg = counters.get(low+(high-low)/2);
        do {
            while(counters.get(i) > avg)
                i++;
            while (counters.get(j) < avg)
                j--;
            if(i <= j) {
                int temp = counters.get(j);
                String tempS = chars.get(j);
                counters.set(j, counters.get(i));
                chars.set(j, chars.get(i));
                counters.set(i, temp);
                chars.set(i, tempS);
                i++;
                j--;
            }
        } while(i <= j);
        if (low < j)
            quicksort(counters, low, j);
        if (i < high)
            quicksort(counters, i, high);
    }

    private void doubleDismemberOfText(String string) {
        for (int i = 0; i < string.length()-1; i++) {
            Character ch1 = string.charAt(i);
            Character ch2 = string.charAt(i+1);
            String doubleCh = ch1.toString() + ch2.toString();
            if (!chars.contains(doubleCh)) {
                chars.add(doubleCh);
                counters.add(1);
            } else
                counters.set(chars.indexOf(doubleCh), counters.get(chars.indexOf(doubleCh)) + 1);
        }
    }

    private void dismemberOfText(String string) {
        for (int i = 0; i < string.length(); i++) {
            Character ch = string.charAt(i);
            if (!chars.contains(ch.toString())) {
                chars.add(ch.toString());
                counters.add(1);
            } else
                counters.set(chars.indexOf(ch.toString()), counters.get(chars.indexOf(ch.toString())) + 1);
        }
    }

    private double entropy(ArrayList<Integer> count, int length) {
        double entropy = 0;
        for (int i: count) {
            entropy = entropy + ((double)i/length)*(Math.log((double)i/length)/Math.log(2));
        }
        return entropy*(-1);
    }

    public HuffmanTree buildTree(ArrayList<String> charsAL, ArrayList<Integer> frequencies) {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
        // initially, we have a forest of leaves
        // one for each non-empty character
        for (int i = 0; i < charsAL.size(); i++) {
            trees.offer(new HuffmanLeaf(frequencies.get(i), charsAL.get(i)));
        }

        assert trees.size() > 0;
        // loop until there is only one tree left
        while (trees.size() > 1) {
            // two trees with least frequency
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();

            // put into new node and re-insert into queue
            trees.offer(new HuffmanNode(a, b));
        }
        return trees.poll();
    }

    public void parseTree(HuffmanTree tree, StringBuffer prefix, int count) {
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf)tree;

            // print out character, frequency, and code for this leaf (which is just the prefix)
            //entropy = entropy + ((double)i/length)*(Math.log((double)i/length)/Math.log(2));

            parsedChars.add(leaf.value);
            parsedCounters.add(leaf.frequency);
            parsedPrefix.add(prefix.toString());
            parsedFrequency.add((double) leaf.frequency/count);


        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;

            // traverse left
            prefix.append('0');
            parseTree(node.left, prefix, count);
            prefix.deleteCharAt(prefix.length()-1);

            // traverse right
            prefix.append('1');
            parseTree(node.right, prefix, count);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }

    private void printCodedStr(String s) {
        for (int i = 0; i<s.length(); i++) {
            Character ch = s.charAt(i);
            int position = parsedChars.indexOf(ch.toString());
            parsedPrefixString.append(parsedPrefix.get(position));
        }
    }

    private void printDoubleCodedStr(String s) {
        for (int i = 0; i<s.length()-1; i++) {
            Character ch1 = s.charAt(i);
            Character ch2 = s.charAt(i+1);
            String ch = ch1.toString() + ch2.toString();
            int position = parsedChars.indexOf(ch);
            parsedPrefixString.append(parsedPrefix.get(position));
        }
    }
}

abstract class HuffmanTree implements Comparable<HuffmanTree> {
    public final int frequency; // the frequency of this tree
    public HuffmanTree(int freq) { frequency = freq; }

    // compares on the frequency
    public int compareTo(HuffmanTree tree) {
        return frequency - tree.frequency;
    }
}

class HuffmanLeaf extends HuffmanTree {
    public final String value; // the character this leaf represents

    public HuffmanLeaf(int freq, String val) {
        super(freq);
        value = val;
    }
}

class HuffmanNode extends HuffmanTree {
    public final HuffmanTree left, right; // subtrees

    public HuffmanNode(HuffmanTree l, HuffmanTree r) {
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }
}

class HuffmaneCode {
    static double globalEntopy = 0;
    static StringBuffer globalCodedStr = new StringBuffer("");
    static HashMap<String, String> list = new HashMap<String, String>();
    static ArrayList<Integer> charFrequencies;
    static ArrayList<String> chars;

    // input is an array of frequencies, indexed by character code
    public static HuffmanTree buildTree(ArrayList<String> charsAL, ArrayList<Integer> frequencies) {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
        // initially, we have a forest of leaves
        // one for each non-empty character
        for (int i = 0; i < charsAL.size(); i++) {
            trees.offer(new HuffmanLeaf(frequencies.get(i), charsAL.get(i)));
        }

        assert trees.size() > 0;
        // loop until there is only one tree left
        while (trees.size() > 1) {
            // two trees with least frequency
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();

            // put into new node and re-insert into queue
            trees.offer(new HuffmanNode(a, b));
        }
        return trees.poll();
    }

    private static double printEntropy(double ent, HuffmanTree tree, StringBuffer prefix, int count) {
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf)tree;

            globalEntopy = globalEntopy + ((double)leaf.frequency/count)*(Math.log((double) leaf.frequency/count)/Math.log(2));
            String ch = leaf.value;

            list.put(ch, prefix.toString());
            //System.out.println(list.get(ch));


        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;

            // traverse left
            prefix.append('0');
            printEntropy(globalEntopy, node.left, prefix, count);
            prefix.deleteCharAt(prefix.length()-1);

            // traverse right
            prefix.append('1');
            printEntropy(globalEntopy, node.right, prefix, count);
            prefix.deleteCharAt(prefix.length()-1);
        }
        //System.out.println("entropy = " + ent);
        return globalEntopy;
    }

    private static void printCodedStr(StringBuffer sb1) {
        for (int i = 0; i<sb1.length(); i++)
            globalCodedStr.append(list.get(((Character)sb1.charAt(i)).toString()));
    }

    private static void printDoubleCodedStr(StringBuffer sb1) {
        for (int i = 0; i<sb1.length()-1; i++) {
            Character ch1 = sb1.charAt(i);
            Character ch2 = sb1.charAt(i+1);
            String ch = ch1.toString() + ch2.toString();
            globalCodedStr.append(list.get(ch));
        }
    }

    private static void printDoubleCodes(HuffmanTree tree, StringBuffer prefix, int count) {
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf)tree;

            // print out character, frequency, and code for this leaf (which is just the prefix)
            //entropy = entropy + ((double)i/length)*(Math.log((double)i/length)/Math.log(2));



            System.out.println(leaf.value + "\t" + leaf.frequency + "\t" + prefix + "\t" + (double) leaf.frequency/count);


        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;

            // traverse left
            prefix.append('0');
            printCodes(node.left, prefix, count);
            prefix.deleteCharAt(prefix.length()-1);

            // traverse right
            prefix.append('1');
            printCodes(node.right, prefix, count);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }

    public static void printCodes(HuffmanTree tree, StringBuffer prefix, int count) {
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf)tree;

            // print out character, frequency, and code for this leaf (which is just the prefix)
            //entropy = entropy + ((double)i/length)*(Math.log((double)i/length)/Math.log(2));



            System.out.println(leaf.value + "\t" + leaf.frequency + "\t" + prefix + "\t" + (double) leaf.frequency/count);


        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;

            // traverse left
            prefix.append('0');
            printCodes(node.left, prefix, count);
            prefix.deleteCharAt(prefix.length()-1);

            // traverse right
            prefix.append('1');
            printCodes(node.right, prefix, count);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }

    private static void sortText(ArrayList<String> chars, ArrayList<Integer> counters) {
        int low = 0;
        int high = charFrequencies.size();
        quicksort(low, high-1);
    }

    private static void quicksort(int low, int high) {
        int i = low;
        int j = high;
        //System.out.println(i + " " + j);
        int avg = charFrequencies.get(low+(high-low)/2);
        do {
            while(charFrequencies.get(i) > avg)
                i++;
            while (charFrequencies.get(j) < avg)
                j--;
            if(i <= j) {
                int temp = charFrequencies.get(j);
                String tempS = chars.get(j);
                charFrequencies.set(j, charFrequencies.get(i));
                chars.set(j, chars.get(i));
                charFrequencies.set(i, temp);
                chars.set(i, tempS);
                i++;
                j--;
            }
        } while(i <= j);
        if (low < j)
            quicksort(low, j);
        if (i < high)
            quicksort(i, high);
    }

    public static void parseDoubleChars(char c1, char c2){
        Character ch1 = c1;
        Character ch2 = c2;
        String ch = ch1.toString() + ch2.toString();

        if (!chars.contains(ch)) {
            chars.add(ch);
            charFrequencies.add(1);
        } else {
            charFrequencies.set(chars.indexOf(ch), charFrequencies.get(chars.indexOf(ch)) + 1);
        }

    }

    public static void parseOneChar(char c){
        Character ch = c;

        if (!chars.contains(ch.toString())) {
            chars.add(ch.toString());
            charFrequencies.add(1);
        } else {
            charFrequencies.set(chars.indexOf(ch.toString()), charFrequencies.get(chars.indexOf(ch.toString())) + 1);
        }

    }


    public static void main(String[] args) {
        String tempStr = "";

        //String s = "qwe";
        StringBuffer sb = new StringBuffer("");
        int counter = 0;

        try(FileReader reader = new FileReader("C:\\test.txt"))
        {
            // читаем посимвольно
            int c;
            while((c=reader.read())!=-1){

                if ((char) c != ' ') // TODO: Отключить пробел
                    sb.append((char) c);

                counter++;
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }


        //TODO: один регистр, если комментить, то надо поменять в строке ниже sb1 на sb
        {
            tempStr = sb.toString().toUpperCase();
        }
        //TODO: строка для удаления левых символов
        {
            //tempStr = sb.toString(); //TODO: раскомментить, если закомменчен блок сверху, который toUpperCase()
            tempStr = tempStr.replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "");
            counter = tempStr.length();
        }

        StringBuffer sb1 =  new StringBuffer(tempStr);
        System.out.println("sb1 = " + sb1);

        // we will assume that all our characters will have
        // code less than 256, for simplicity
        charFrequencies = new ArrayList<>();
        chars = new ArrayList<>();
        int[] charFreqs = new int[99999999];
        // read each character and record the frequencies
        //TODO: вот тут sb1 на sb, если мы не приводим к одному регистру
        /*for (char c : sb1.toString().toCharArray()) {
            if(c == ' ') {
                counter--;
                continue;
            }
            //charFreqs[c]++;

        }*/

        char[] arr = sb1.toString().toCharArray();

        for (int i = 0; i<sb1.toString().toCharArray().length-1; i++) {
            parseDoubleChars(arr[i], arr[i+1]);
        }

        sortText(chars, charFrequencies);
        System.out.println(charFrequencies.get(0) + " " + charFrequencies.get(1));

        // build tree
        HuffmanTree tree = buildTree(chars, charFrequencies);
        double entropy = 0.0;
        entropy = printEntropy(entropy, tree, new StringBuffer(), counter);
        entropy = entropy * (-1);
        System.out.println("entropy: " + entropy);

        // print out results

        System.out.println("SYMBOL\tWEIGHT\tHUFFMAN CODE\tFREQUENCY");
        printCodes(tree, new StringBuffer(), counter);
        //printDoubleCodes(tree, new StringBuffer(), counter);

        //printCodedStr(sb1);
        printDoubleCodedStr(sb1);
        System.out.println(globalCodedStr);
        System.out.println("Вес исходного сообщения: " + sb1.length() * 16 + " бит или " + sb1.length()*2 + " байт");
        System.out.println("Вес полученного сообщения: " + globalCodedStr.length() * 2 + " бит или " + (double) globalCodedStr.length()/4 + " байт");
        double koef1 =(double)(sb1.length()*16)/(globalCodedStr.length()*2);
        double koef2 =(double)(globalCodedStr.length()*2)/(sb1.length()*16);
        System.out.println("Коэффициенты: " + koef1 + " или " + koef2);
    }

}

