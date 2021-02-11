# Author Information

###### Name
Timothy Catana

###### Email Adress
tcatana@uoguelph.ca

###### Student ID 
1080041

###### Statement 
I Timothy Catana attest to the fact that I have done this assignment on my own without copying work from any other sources

# Game Information

## Navigation

Onve you have entered the game, you will be able to move using the arrow keys or using the classic "wasd" layout where "w" = up, 
"a" = left, "d" = right, "w" = up. You, the player, are also able to walk through doors (x) but not through walls (| or -).

## items

Currently, items are just there for show. You are able to walk over them and cause them to dissappear, but that does not do anything
to you, the player. Items who's ID's are non existant in rogue get eliminated at the creation of the rooms. Likewise, all item
placement and location error checking is done at the creation of the rooms.

## Doors

When placing the doors into the rooms, I have made sure of two things:
> A) All rooms have at least one door          

> B) All unused (unconnected) doors that were parsed from the rooms JSON were deleted    

So don't be surprised if a "door is missing". More than likely it has been purposely eliminated because it was not connected
to any other door, and thus, not useful.

# Instructions 

## Required Files
> Valid **fileLocations.json** file     

> Valid **rooms.json** file     

> Valid **symbols.json** file

## Preperation

#### fileLocations.json
make sure that in the A2 folder you have a valid **fileLocations.json** that contains the locations of the json files **rooms.json** and **symbols.json**.  

#### rooms.json
makesure that in the A2 you have a valid **rooms.json** that is written in the correct format shown in your cloned folder

#### rooms.json
makesure that in the A2 you have a valid **symbols.json** that is written in the correct format shown in your cloned folder

## Build
`gradle build`

## Excecution
`java -jar build/libs/a2.jar`

# Extra

## UI

since Windows doesn't use a "terminal", I have used to "WindowsUI.java" provided by Professor Judi.

## Game Text

For some reason, when I throw the InvalidMoveException, my text doesn't get printed out in the try-catch and I can't figure out why

## Note To Grader

I do not know why, but sometimes the built terminal doesn't function properly and does't allow me to get user input properly (it lags/freezes for a few seconds). Could you please make sure that this doesn't somehow occur during the testing process (if thats possible). Thanks.