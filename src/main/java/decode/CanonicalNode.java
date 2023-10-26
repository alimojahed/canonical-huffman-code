package decode;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class CanonicalNode implements Comparable<CanonicalNode>{
    int length;
    Character character;
    @Override
    public int compareTo(CanonicalNode o) {
        if (this.length <  o.length)
            return -1;
        else if(this.length == o.length && this.character - o.character < 0)
            return -1;
        else if (this.length == o.length && this.character - o.character > 0)
            return 1;
        return 1;
    }
}
