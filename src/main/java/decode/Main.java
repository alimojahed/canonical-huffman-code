package decode;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CanonicalHuffmanDecoder decoder = new CanonicalHuffmanDecoder("C:\\Users\\User\\IdeaProjects\\canonical-huffman\\src\\main\\java\\a.txt_Encoded");
        decoder.decode();
    }
}
