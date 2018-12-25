# 371aiProject

## Project by Sylar Mao and Saikorin


Movement Rule

- [ ] Baisc rule
- [ ] En passant 
- [ ] Promotion
- [ ] Castling
- [ ] stalemate
- [ ] checkmate
- [ ] check

AI build up
- [ ] ab pruning tree (within depth parameter)
- [ ] copy others idea? (like evaluation function/ fitness/ and etc.

GUI?
- [ ] build up a gui board to receive more grade? (need to be api style to implement either way)
- [ ] NEED a good OOP implement style

Output
- [ ] output in console
- [ ] dump all the output in text file (example shown)
```
		PrintStream out = new PrintStream(new FileOutputStream("d:/output.txt"));
		System.setOut(out);
   ```

Physical handin
- [ ] print out code
- [ ] 6-8 pages typed report
- [ ] Describe program 
- [ ] Including any reference

E-submit
- [ ] processable coding 
- [ ] reference
      
      
Algo reference
https://ask.helplib.com/resources/post_485255

## AB Pruning
[Alpha-beta pruning](http://www.frayn.net/beowulf/theory.html#abpruning) is a technique for enormously reducing the size of your game tree. Currently using the negamax algorithm we are searching every reply to every move in the tree. In the average chess position there are about 30 legal moves, and let's say for sake of argument that our program analyses 50,000 moves per second. Now, let's see how deep we can search.

| Depth (ply)  | Number of Positions |Time to Search|
| ------------- | ------------- | ------------- |
| 2  | 900 | 0.018 s  |
| 3  | 27,000  | 0.54 s  |
| 4  | 810,000	 | 16.2 s  |
| 5  | 24,300,000 | 8 minutes  |
| 6  | 729,000,000  | 4 hours  |



