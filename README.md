game
====

First project for CompSci 308 Spring 2015

Name: Callie Mao 

Date started: 1/8/2015

Date finished: 1/15/2015

Hours worked: 27

Resources used: JavaFX documentation library

Main class file: /StickyBox/src/Main.java

Data (non Java) files needed: coin.png, finish.png, rock.png, star.png

How to play the game: Lead StickyBox to the exit by using the left, right, up, and down arrow keys. StickyBox can only move in one direction (he cannot change directions while moving) but can stick to obstacles along the way.  In level 2, collect all items before leading StickyBox to the exit. 

Keys/Mouse input: left, right, up, down arrow keys to move

Cheat Keys:  'r' to restart the level, 's' to skip a level, 'h' to get a hint and reveal solution boxes

Known bugs: None

Extra features: 
- Hint turns off upon key release
- StickyBox's movement is a bit more complex than simply moving a few pixels upon each tap. His movement had to be tailored to his movement direction (which is tracked similar to a velocity vector), he moves until he collides with an object and ignores all input until then, and he only collides with other objects if he is travelling in specific directions (ie. brushing against and/or passing an object will not necessarily trigger a collision). 

Impressions/Suggestions: I greatly enjoyed this assignment and had a lot of fun coding it. I would really like to add more features to this in the future if time allows and improve upon it. One of the more difficult aspects was what constitutes "good design". There were many different options to write into the game, some involving robust master classes and other less comprehensive solutions that were easier to understand for simple game code. JavaFX was also very helpful in writing the game, but it could also be limiting at times; there were several times where I had to retailor my entire code to fix a small limitation and sacrifice generality/reusability for it (ie. using ImageView instead of Node because Node doesn't have a getX() method even though most of its subclasses did)

