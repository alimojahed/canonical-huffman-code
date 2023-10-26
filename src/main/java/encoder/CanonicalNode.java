package encoder;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class CanonicalNode implements Comparable<CanonicalNode>{
    String bits;
    Character character;
    @Override
    public int compareTo(CanonicalNode o) {
        if (this.bits.length() <  o.bits.length())
            return -1;
        else if(this.bits.length() == o.bits.length() && this.character - o.character < 0)
            return -1;
        else if (this.bits.length() == o.bits.length() && this.character - o.character > 0)
            return 1;
        return 1;
    }
}
