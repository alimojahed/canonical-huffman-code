package decode;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;

import java.lang.reflect.Array;
import java.util.*;

@Slf4j
public class CanonicalHuffmanDecoder {
    private byte[] content;
    private FileInputStream file; // to store the last read position
    private ArrayList<CanonicalNode> canonicalNodes = new ArrayList<>();
    private int indexOfStartingOfContents = 0;
    private Map<String, Character> map = new HashMap<>();
    private int numberOfRedundantBits = 0;
    private String path;
    public CanonicalHuffmanDecoder(File file) throws IOException {
        this.path = file.getPath();
        this.file = new FileInputStream(file);
//        checkFileExistence(); //input stream will do that
        readContent();
        canonicalNodes();
        generateCanonical();

    }

    public CanonicalHuffmanDecoder(String path) throws IOException {
        this(new File(path));
    }


//    private void checkFileExistence() throws FileNotFoundException {
//        if (!file.exists())
//            throw new FileNotFoundException();
//    }

    public void decode() throws IOException
    {
        decode(this.path + "_DECODED");
    }

    public void decode(String path) throws IOException {
        StringBuilder builder = new StringBuilder();
//        builder.append(0);
        numberOfRedundantBits = content[indexOfStartingOfContents];
        log.info(String.valueOf(indexOfStartingOfContents));
        for (int i=indexOfStartingOfContents+1; i<content.length; i++)
        {
            String temp = String.format("%8s", Integer.toBinaryString((byte)content[i])).replace(' ', '0');
            builder.append(temp.substring(temp.length()-8));
            log.info(String.valueOf(content[i]) + " :: " + temp.substring(temp.length()-8) + " : " + temp);
        }
        log.info(Arrays.toString(content));
        log.info(builder.toString());
        log.info(String.valueOf(numberOfRedundantBits));
        for (int i=0; i<numberOfRedundantBits;i++) {
            builder.delete(builder.length()-1, builder.length());
        }
//        builder.append(String.format("%8s", Integer.toBinaryString(content[content.length-1])).replace(' ', '0').substring(0,8-numberOfRedundantBits));

        int position = 0;
        StringBuilder original = new StringBuilder();
        for (int i = 1; i<=builder.length(); i++)
        {
            String tempCode = builder.substring(position, i);
            if (map.containsKey(tempCode)) {
                original.append(map.get(tempCode));
                position = i;

            }
        }
        log.info("DECODED CONTENT: " + original.toString());
        OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(new File(path)));
        output.append(original.toString());
        output.flush();
        output.close();
        log.info("DECODED FILE SAVE TO" + new File(path).getAbsolutePath());

    }

    private void canonicalNodes()
    {
        int numberOfCharPairs = content[0];
        indexOfStartingOfContents = 1;
        for (int i=1; i<=numberOfCharPairs;i++)
        {
            char character = (char) content[indexOfStartingOfContents];
            indexOfStartingOfContents ++;
            int length = content[indexOfStartingOfContents];
            indexOfStartingOfContents ++;
            CanonicalNode node = new CanonicalNode(length, character);
            canonicalNodes.add(node);
        }

        Collections.sort(canonicalNodes);
        log.info("CANONICAL NODES: " + canonicalNodes);
        log.info(String.valueOf(indexOfStartingOfContents));
    }

    private void generateCanonical()
    {
        log.info("GENERATING CANONICAL CODE BOOK...");
        int canonicalCode = -1, currentLength = canonicalNodes.get(0).length, lastLength = canonicalNodes.get(0).length;
        for (CanonicalNode node: canonicalNodes)
        {
            currentLength = node.length;
            canonicalCode = (canonicalCode + 1) << currentLength - lastLength;
            //printf("%*c", n, ' ');
//        System.out.println(String.format("%*s", 2, 0, "Hello")); not working :(
//            System.out.println(String.format("%%ds", temp, s));
            String code = (String.format("%"+currentLength+"s", Integer.toBinaryString(canonicalCode))).replace(' ', '0');
            log.info("CODE GENERATED: " + node.character + " : " + code);

            map.put(code, node.character);
            lastLength = currentLength;
        }


        log.info("CANONICAL CODE BOOK GENERATED!!");
        log.info("CANONICAL BOOK: " + map);
    }
    private void readContent() throws IOException {
        this.content = IOUtils.toByteArray(file);
        log.info(Arrays.toString(content));
    }
}
