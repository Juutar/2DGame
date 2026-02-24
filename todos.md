## Todos

- gave save (literally just save the level number.)
- Game load & reset
- remove princess & dragon folders from level files
- friendly fire
- re-arrange levels in order of scorched ground
- refactor player actions into Action class
- fix the bridges

## Say in interview

- the **tutorial** works as follows: each player is told what keyset they can use, and they are left to figure out by themselves what actions each of them can perform. There are no lives that produce a final game over and the reset iteration is very fast, so within ~1min of tutorial the players should have figured out all the mechanics and be comfortable to move forward.
- the **features** include:
  - collaboration gameplay
  - collecting the key to open the door
  - the dragon spitting fire
  - the start & end screens
  - the sound effects
  - the load & save
  - the specialized grounds
  - the bridges and buttons
- The **engineering** includes:
  - levels stored in minimal json files
  - levels are fetched until the next is not found, so there is no need to modify any code to add new levels to the game
  - the princess and dragon are a single class, whose behavior changes only in minor places depending on the tiles they face
  - the model only reasons in terms of tile placement and not in screen pixels
  - the sound logic is abstracted away in the AudioPlayer class
  - the tile logic is abstracted away in the TileMap class.
- The **theme** includes:
  - the art
  - the fire, bridge & chest mechanics
  - the key inventory
  - the levels in reverse order
  - the purple shading
  - the background music
