package encoder;

import lombok.ToString;

@ToString
public class HuffmanNode extends Node implements Comparable<HuffmanNode>{
    String encoded;
    Character character;

    public HuffmanNode(int length, String encoded, Character character) {
        this.key = length;
        this.encoded = encoded;
        this.character = character;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass()!= HuffmanNode.class)
            return false;
        obj = (HuffmanNode)obj;
        return ((HuffmanNode) obj).character == this.character && ((HuffmanNode) obj).encoded.equals(this.encoded);
    }

    @Override
    public int compareTo(HuffmanNode o) {
        if (this.key <  o.key)
            return -1;
        else if(this.key == o.key && this.character - o.character < 0)
            return -1;
        else if (this.key == o.key && this.character - o.character > 0)
            return 1;
        return 1;
    }
}
