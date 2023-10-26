# Ali Mojahed Canonical Huffman Compresion

## Encoding 
Steps:

    1.Calculate Frequency of each character and store it into a priority queue
    2.Generate Huffman Tree
    3.Generate Huffman codes based on Huffamn Tree
    4.Generate Canonical Codes with Huffman codes
    5. the header is look like this 
         [number of char pairs][char][length][number of redundant bits at end to make 8 bits]
    6.encode the content
    
## Decoding
Steps:
    
    1.Read the whole file as a byte array
    2.read the header
    3.make canonical codes based on information of header
    4.read each byte and added to a string builder with binary form
    5.start reading byte by byte and check for occurance of each character.
      