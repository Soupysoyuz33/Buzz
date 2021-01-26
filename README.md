# Buzz
A side scroller, flight path game created with Java

Buzz is a game in which you have to get Buzz, the blue bottle fly, from one side of the hedge to the other.
You can use either space bar or a mouse click to get him to fly up and he will drop with his own weight.
The goal is to pass through the holes in the branches of the hedge and get him to the greatest Fly Tavern in 
existence... although you might not make it that far so aim to get the best score.

The game is made up of two classes, Buzz and Interfacer. Buzz provides the main code for the game while
Interfacer extends JPanel so that the paintComponent() method contains the rendering functionality from
Buzz. 

Buzz uses JFrame to create the window that displays the game at a fixed screen size. The game generates 
the gaps in the hedge, or gates, with the opening at random heights for each one. A space key release or
mouse click will increase the y position of the Rectangle which represents Buzz the fly and the user can
then steer the fly through the openings. Collisions with any part of the branches will cause the fly to
die and drop to the ground and GAME OVER! A score which counts how many gates have been succesfully passed
through is displayed at the top of the game screen. The game can be restarted from the beginning by simply
clicking or pressing space while Game Over is displayed on the screen.

This game was created with the help of the this Java Tutorial by Jaryt Bustard
https://www.youtube.com/watch?v=I1qTZaUcFX0&feature=emb_logo
