package encoder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CanonicalHuffmanEncoder {
    private HuffmanCodeGenerator huffmanCodeGenerator;
    private ArrayList<CanonicalNode> canonicalCodeBook = new ArrayList<>();
    private Map<Character, String> canonicalCodeBookMap = new HashMap<>(); //<length of each bit string, frequency of that length>
    private ArrayList<HuffmanNode> huffmanNodes;
    private File file;
    private String content;
    private String outputFilePath;
    public CanonicalHuffmanEncoder(File file) throws IOException {
        this.file = file;
        checkFileExistence();
        readContent();
        huffmanCodeGenerator = new HuffmanCodeGenerator(content);
        huffmanCodeGenerator.generateCodes();
        huffmanNodes = getHuffmanNodes();
        log.info(huffmanNodes.toString());

    }

    public CanonicalHuffmanEncoder(String path) throws IOException {
        this(new File(path));
    }


    private void checkFileExistence() throws FileNotFoundException {
        if (!file.exists())
            throw new FileNotFoundException();
    }

    public ArrayList<HuffmanNode> getHuffmanNodes() {
        huffmanNodes = huffmanCodeGenerator.getHuffmanNodes();
        Collections.sort(huffmanNodes);

        return huffmanNodes;
    }

    public void encode() throws IOException
    {
        encode(file.getPath().replace(file.getName(), file.getName()+"_Encoded"));
    }

    public void encode(String path) throws IOException {
        log.info("START ENCODING...");
        generateCanonical();
        makeOutputFile(path);
        log.info("ENCODING COMPLETE YOU CAN FIND THE ENCODED FILE AT: " + this.outputFilePath);
    }
    //decode => (NUMBER_OF_CHAR_LENGTH_PAIRS)(char-codelength)(number of bits add to make full bytes)(ENCODED_TEXT)
    private void makeOutputFile(String path) throws IOException {
        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(path));
        String usedChars="";
        byte numberOfContents = (byte) 0;
        log.info("STARTING MAKING META DATA...");
        ArrayList<Byte> charPairs = new ArrayList<>();
        for (CanonicalNode node: canonicalCodeBook)
        {
            byte codeLength = (byte) node.bits.length();
            byte asciiCode = (byte)((int)node.character);
            charPairs.add(asciiCode);
            charPairs.add(codeLength);
            numberOfContents = (byte)(numberOfContents + 1);
            log.info(charPairs.toString());
        }
        log.info("META DATA CREATED!!");
        log.info("HEADER" + charPairs);
        log.info("START ENCODING CONTENT WITH CODE BOOKS...");
        byte redundantBitsAtEnd = (byte) 0;
        ArrayList<Byte> encodedContentBytes = new ArrayList<>();
        StringBuilder bytes = new StringBuilder();
        int stringBuilderIndex = 0;
        ArrayList<String> test = new ArrayList<>();
        for (Character character: content.toCharArray()) {
            bytes.append(canonicalCodeBookMap.get(character));
            if (bytes.length() - stringBuilderIndex >= 8) {
                encodedContentBytes.add((byte) Integer.parseInt(bytes.substring(stringBuilderIndex, stringBuilderIndex + 8), 2));
                test.add(bytes.substring(stringBuilderIndex, stringBuilderIndex + 8));
//                encodedContentBytes.add((byte) Integer.parseInt(bytes.substring(0, 0 + 8), 2));
//                log.info("test: " + (byte) Integer.parseInt(bytes.substring(stringBuilderIndex, stringBuilderIndex + 8), 2));
                stringBuilderIndex += 8;
//                bytes.delete(0, 8);
            }
        }

        if (bytes.length()-stringBuilderIndex > 0)
        {
            redundantBitsAtEnd = (byte) (8 - (bytes.length()-stringBuilderIndex));
            String temp = String.format("%8s", bytes.substring(stringBuilderIndex)).replace(' ', '0');
            temp = temp.substring(temp.length()-8);
            encodedContentBytes.add((byte)Integer.parseInt(temp, 2));
        }

        log.info(String.valueOf(test));
        log.info("Redundant bit: " + redundantBitsAtEnd);
        log.info(String.valueOf(bytes.length()));
        log.info(String.valueOf(stringBuilderIndex));
        log.info("CONTENT ENCODE TO BYTES!!!");
        log.info("ENCODED CONTENT: " + encodedContentBytes);
        log.info("START WRITING ON FILE ...");
        output.write(numberOfContents);
        log.info(String.valueOf(numberOfContents));
        for (Byte charPair : charPairs) {
            output.write(charPair);
            log.info(charPair.toString());
        }
        output.write(redundantBitsAtEnd == 8?0:redundantBitsAtEnd);
        log.info("red: " + String.valueOf(redundantBitsAtEnd == 8?0:redundantBitsAtEnd));
        for (Byte b: encodedContentBytes)
        {
            output.write(b);
            log.info(String.valueOf(b));
        }
        this.outputFilePath = new File(path).getAbsolutePath();
        output.flush();
        output.close();
        log.info("WRITING FINISHED!!");
    }

    private void generateCanonical()
    {

        log.info("GENERATING CANONICAL CODE BOOK...");

        int canonicalCode = -1, currentLength = huffmanNodes.get(0).key, lastLength = huffmanNodes.get(0).key;
        for (HuffmanNode node: huffmanNodes)
        {
            currentLength = node.key;
            canonicalCode = (canonicalCode + 1) << currentLength - lastLength;
            //printf("%*c", n, ' ');
//        System.out.println(String.format("%*s", 2, 0, "Hello")); not working :(
//            System.out.println(String.format("%%ds", temp, s));
            String code = (String.format("%"+currentLength+"s", Integer.toBinaryString(canonicalCode))).replace(' ', '0');
            log.info("CODE GENERATED: " + node.character + " : " + code);
            canonicalCodeBook.add(new CanonicalNode(code, node.character));
            canonicalCodeBookMap.put(node.character, code);
        }


        Collections.sort(canonicalCodeBook);
        log.info("CANONICAL CODE BOOK GENERATED!!");
        log.info(canonicalCodeBook.toString());
    }

    private void readContent() throws IOException {
        FileInputStream input = new FileInputStream(this.file);
        this.content = IOUtils.toString(input, StandardCharsets.US_ASCII);
    }

}
