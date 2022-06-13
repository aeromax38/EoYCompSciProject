# EoYCompSciProject
In this game, the player will choose the number of sides to a die that they will then try to guess the number of a roll. The score is calculated using a simple formula based on the difficulty and proximity of the guess. The user can view both their personal stats for the current session and the all-time leaderboard, as well as view the rules/explanation.

We used HashMaps for assigning each username to a key and their score to a value, so once a user asks for their score it will constantly be updated. The try/catch is implemented within the InputNum method. We also used a switch statement to understand what the user wants to do. It gives them the option to do necessary commands. 

Serialization was implemented by adding a persistent leaderboard. The current state of the leaderboard is saved each time "quit" is entered by the user, and the state is restored each time the program is initialized.

Authors: Max Richardson, Manish Nadendla, and Ryan Choudhury

Changelog:
5/12: Created
5/18: Changed game description
5/19: HashMap and switch command implementation (stat system)
5/20: Implementation of try/catch 
5/23: Validating user input with no error messages
6/2: New startup instructions & better-looking leaderboard
6/9: Implemented serialization with persistent leaderboard & code simplification