package encoder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CanonicalHuffmanEncoder encoder = new CanonicalHuffmanEncoder("C:\\Users\\User\\IdeaProjects\\canonical-huffman\\src\\main\\java\\a.txt");
        encoder.encode();
    }
}
/*
! : 00
e : 01
l : 100
  : 1010
H : 1011
h : 1100
o : 1101
r : 1110
t : 1111
 */