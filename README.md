# TicTacToe

Project requires JDK 1.7+, maven.

Compile project first:

```
mvn compile
```

All loosing combinations are saved in lines.txt by default.

In order to run a game execute:
```
mvn exec:java
```

There are 2 optional command line arguments: [debug flag] [file name]. 

With debug flag on there is additional output on how AI picks next move.

File name allows to load and save from altenative file.

Example:
```
mvn exec:java -Dexec.args="true savefile.txt"  
```


Player makes a move with a command 'row,column'. For example:

```
>2,2
```

To exit the game at any time:

```
>e
```
