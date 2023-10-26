# Canonical Huffman Compresion

This repository is part of a project for the Data Structures course at Ferdowsi University of Mashhad. The project focuses on implementing the Canonical Huffman Encoding and Decoding algorithm. Canonical Huffman codes are a specific type of Huffman code known for their unique properties, allowing for a highly compact representation. Instead of explicitly storing the structure of the code tree, canonical Huffman codes are ordered in a way that only the codeword lengths need to be stored, significantly reducing the codebook's overhead.

## Overview
Huffman coding is a lossless data compression algorithm that assigns variable-length codes to input characters. Canonical Huffman codes are a variant of this algorithm, characterized by their efficient representation.

## Implementation Details
### Encoding
The encoding process involves the following steps:

1. Calculate the frequency of each character and store it in a priority queue.
2. Generate the Huffman Tree.
3. Generate Huffman codes based on the Huffman Tree.
4. Generate Canonical Codes with Huffman codes.
5. The header format is as follows:
[Number of character pairs] [Character] [Code length] [Number of redundant bits at the end to make 8 bits]
6. Encode the content using the generated Canonical Huffman codes.
### Decoding
The decoding process includes these steps:

1. Read the entire file as a byte array.
2. Read the header information.
3. Create Canonical Codes based on the header information.
4. Read each byte and add it to a StringBuilder in binary form.
5. Start reading byte by byte and check for the occurrence of each character, decoding the content.


      