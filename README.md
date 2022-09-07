# Rolling Potato

## Project Proposal
*What will the application do?*
- The application rolling potato is a mini-game that allows the player to control the height of the potato by tapping 
the space key to avoid obstacles that comes in the way of the rolling potato. 
- And the goal of the game is to get as far as possible without hitting any obstacles, which will yield a higher score 
in the end.

*Who will use it?*
- People who are bored and looking for a fun, brainless, relaxing activity to do while killing time.
- Someone who is interested in improving their reaction time and agility

*Why is this project of interest to you?*
- It is a fun project where I will get to have a polished finished product that is straight forward to future employers
- It is able to demonstrate my knowledge in using java language to code a project with a graphic user interface that 
incorporates fundamental concepts learned from CPSC 210 object-oriented programming including abstraction, 
encapsulation, inheritance, and polymorphism


## User Stories
- As a user, I want to be able to avoid the obstacles by controlling when the potato jumps with keyboard 
controls
- As a user, I want to be able to have my score added to my game history and the leadership board of that game session
- As a user, I want to be able to view my past scores in the game session
- As a user, I want to be able to view a list of the scores in the order of high to low on the leadership board loaded 
from a file
- As a user, I want to be able to have the choice of saving my scores in the game session to a file
- As a user, I want to be able to be able to load all past scores from a file
- As a user, I want my scores in the current game session saved to the leadership 
board file if it is higher than the top 5 scores

## Phase 4: Task 2
*Sample of the Events*

Fri Nov 26 13:45:01 PST 2021
New score entry username got a score of 6900 on 2021-11-26 13:45 is added to Player History

Fri Nov 26 13:45:01 PST 2021
Score entry username got a score of 5720 on 2021-11-26 05:08 is removed from Leadership Board

Fri Nov 26 13:45:01 PST 2021
New score entry username got a score of 6900 on 2021-11-26 13:45 is added to Leadership Board

## Phase 4: Task 3
*Reflect on the design presented in your UML class diagram.*
- apply the observer pattern to reduce coupling
  - for example, if there is a new score that needs to be added, then notify all observers (playerHistory, 
  LeadershipBoard and the GamePanel)
- I would refactor PlayerHistory so that it has a list of different players, instead of a meshed up list of all of the 
  past scores by different players
- I would also refactor RollingPotatoApp to make other classes that represent panels on the main application, 
    just like the gamePanel. This could possibly increase cohesion as the rollingPotatoApp class can just be responsible
  for processing user inputs, and the other panel classes can then respond according to the user inputs