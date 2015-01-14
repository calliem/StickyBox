game
====

First project for CompSci 308 Spring 2015

Name: Callie Mao 

Date started: 1/8/2015

Date finished: 1/14/2015

Hours worked: 25

Resources used: JavaFX documentation library

Main class file: Main.java

Data (non Java) files needed: coin.png, finish.png, rock.png, star.png

How to play the game: Lead StickyBox to the exit by using the left, right, up, and down arrow keys. StickyBox can only be controlled while he is stuck to an obstacle and he cannot change directions while he moves. In level 2, collect all items before leading StickyBox to the exit. 

Keys/Mouse input: left, right, up, down arrow keys to move; 'r' to restart the level if the user gets stuck or messes up

Cheat Keys: 'h' for a hint/solution to the game

Known bugs: None

Extra features: 
- Hint turns off upon key release
- StickyBox's movement is a bit more complex than simply moving a few pixels upon each tap. His movement had to be tailored to his movement direction (which is tracked similar to a velocity vector), he moves until he collides with an object and ignores all input until then, and he only collides with other objects if he is travelling in specific directions (ie. brushing against and/or passing an object will not necessarily trigger a collision). 

Impressions/Suggestions: I greatly enjoyed this assignment and had a lot of fun coding it. I would really like to add more features to this in the future if time allows and improve upon it.

