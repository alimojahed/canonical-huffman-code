package encoder;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class HuffmanCodeGenerator {
    PriorityQueue<FrequencyPair> frequencyPairs = new PriorityQueue<>();

    private Map<Character, String> huffmanNodes = new HashMap();
    private String content;
    private FrequencyPair root;
    public HuffmanCodeGenerator(String content)
    {
        this.content = content;
        fillFrequencyQueue();
        makeTree();
    }

    private void fillFrequencyQueue()
    {
        log.info("Huffman : filling frequency queue");
        Map<Character, Integer> map = new HashMap<>();
        for (Character c: content.toCharArray())
        {
            map.merge(c, 1, Integer::sum);
        }
        log.info("map of huffman code: " + map);
        for (Character c: map.keySet())
        {
            frequencyPairs.enqueue(new FrequencyPair(map.get(c), c));
        }



    }

    public FrequencyPair makeTree()
    {
        log.info("MAKING HUFFMAN TREE");
        PriorityQueue<FrequencyPair> queue = frequencyPairs;
        while (queue.size()> 1)
        {
            var first = queue.dequeue();
            var second = queue.dequeue();
            var newIntervalNode = new FrequencyPair(first.key+second.key, first, second, true);
            queue.enqueue(newIntervalNode);
        }
        this.root = queue.dequeue();
        return this.root;
    }

    public void generateCodes()
    {
        generateCodes(root, "");
    }

    private void generateCodes(FrequencyPair node, String bits)
    {

        if (node.leftChild == null && node.rightChild == null && !node.isInterval)
        {
            huffmanNodes.put(node.character, bits);
            return;
        }
        generateCodes(node.leftChild, bits+"0");
        generateCodes(node.rightChild, bits+"1");
    }

    public ArrayList<HuffmanNode> getHuffmanNodes() {
        generateCodes();
        ArrayList<HuffmanNode> nodes = new ArrayList<>();
        for (Character c: huffmanNodes.keySet())
        {
            nodes.add(new HuffmanNode(huffmanNodes.get(c).length(),huffmanNodes.get(c), c ));
        }
        return nodes;
    }
}
