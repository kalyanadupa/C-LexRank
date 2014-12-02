C-LexRank
=========

This project is the implementation of a Generating Extractive Summaries of Scientific Paradigms (Vahed Qazvinian, Dragomir R. Radev) which used Lex Rank Algorithm given in  "LexRank: Graph-based Lexical Centrality as Salience in Text Summarization" (Gune ¨ ¸s Erkan and Radev)

It has three phases: 

1. Finding the Cosine similarity of each node pair in the document and forming the weighted edge graph having each sentence in the graph as node.

2. Detecting Communities in the above graph using the Clauset, Newman Moore Algorithm.

3. Choosing the appropriate node from each community using the Lex ranking method given in Lex Rank paper by Erkan and Radev. 

Thereby we will have the required text summary of the document.
