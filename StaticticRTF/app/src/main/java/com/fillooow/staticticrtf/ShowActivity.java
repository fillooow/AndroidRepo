package com.fillooow.staticticrtf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.PriorityQueue;

public class ShowActivity extends AppCompatActivity {
    private String editString;
    private StringBuffer parsedPrefixString;
    private ArrayList<String> chars;
    private ArrayList<Integer> counters;
    private ArrayList<String> parsedChars;
    private ArrayList<Integer> parsedCounters;
    private ArrayList<String> parsedPrefixAlphabet;
    private ArrayList<Double> parsedFrequency;
    private Double entropy;
    private double ratio;
    int counter = -1;

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
        parsedPrefixAlphabet = new ArrayList<>();
        entropy = 0.0;
        parsedPrefixString = new StringBuffer("");

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
        //sortText(counters);

        entropy = entropy(counters, editString.length());
        //TODO: начиная отсюда
        HuffmanTree tree = buildTree(chars, counters);
        //SYMBOL	WEIGHT	HUFFMAN CODE	FREQUENCY
        parseTree(tree, new String(), editString.length());
        for(String alp : parsedPrefixAlphabet)
            Log.d("alp", alp);
        if (doubleChars)
            printDoubleCodedStr(editString);
        else
            printCodedStr(editString);
        double tempRatioOriginal = editString.length()*24;
        double tempRatioReceived = parsedPrefixString.length()*2;
        ratio = tempRatioOriginal/tempRatioReceived;
        double ratioBit = ratio/8;

        ShowAdapter adapter = new ShowAdapter(chars, counters, editString.length());
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        entropyTV = (TextView) findViewById(R.id.entropyTV);
        entropyTV.setText(String.format(Locale.getDefault(), "%(.3f", entropy));
    }


    private void sortText(ArrayList<Integer> counters) {
        int low = 0;
        int high = counters.size();
        quicksort(counters, low, high-1);
    }

    private void quicksort(ArrayList<Integer> counters, int low, int high) {
        int i = low;
        int j = high;
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

    public void parseTree(HuffmanTree tree, String prefix, int count) {
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf)tree;
            counter++;

            // print out character, frequency, and code for this leaf (which is just the prefix)
            //entropy = entropy + ((double)i/length)*(Math.log((double)i/length)/Math.log(2));

            parsedChars.add(leaf.value);
            parsedCounters.add(leaf.frequency);
            parsedPrefixAlphabet.add(counter, prefix);
            parsedFrequency.add((double) leaf.frequency / count);

            for(String str:parsedPrefixAlphabet)
                Log.d("prefix start", str + " " + counter);


        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;

            // traverse left
            ///prefix.append('0');
            prefix = prefix + "0";
            parseTree(node.left, prefix, count);
            prefix = prefix.substring(0, prefix.length()-1);

            // traverse right
            prefix = prefix + "1";
            parseTree(node.right, prefix, count);
            prefix = prefix.substring(0, prefix.length()-1);
        }
    }

    private void printCodedStr(String s) {
        for (int i = 0; i<s.length(); i++) {
            Character ch = s.charAt(i); // Получаем букву
            int position = parsedChars.indexOf(ch.toString());
            //parsedPrefixAlphabet.get(parsedPrefixAlphabet.indexOf(pref))
            parsedPrefixString.append(parsedPrefixAlphabet.get(parsedChars.indexOf(ch.toString())));
        }
    }

    private void printDoubleCodedStr(String s) {
        for (int i = 0; i<s.length()-1; i++) {
            Character ch1 = s.charAt(i);
            Character ch2 = s.charAt(i+1);
            String ch = ch1.toString() + ch2.toString();
            int position = parsedChars.indexOf(ch);
            parsedPrefixString.append(parsedPrefixAlphabet.get(position));
        }
    }

    public void onButtonAlphabetClick(View view) {
        startActivity(new Intent(ShowActivity.this, ShowAlphabetActivity.class)
                .putStringArrayListExtra("chars", parsedChars)
                .putStringArrayListExtra("prefixes", parsedPrefixAlphabet));
    }

    public void onButtonPrefixClick(View view) {
        startActivity(new Intent(ShowActivity.this, ShowPrefixTextActivity.class)
                .putExtra("prefixString", parsedPrefixString.toString())
                .putExtra("ratio", ratio));
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


