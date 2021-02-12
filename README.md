# Author Information

###### Name
Timothy Catana

# Game Information

## Navigation

Classic "wasd" navigation convention
&nbsp; &nbsp; &nbsp;*W* = move up
&nbsp; &nbsp; &nbsp;*A* = move left
&nbsp; &nbsp; &nbsp;*S* = move down
&nbsp; &nbsp; &nbsp;*D* = move right
<br>
Classic arrow keys navigation convention
&nbsp; &nbsp; &nbsp;*↑* = move up
&nbsp; &nbsp; &nbsp;*←* = move left
&nbsp; &nbsp; &nbsp;*→* = move down
&nbsp; &nbsp; &nbsp;*↓* = move right
<br>

## Symbols

&nbsp; &nbsp; &nbsp;*"|"* = wall<br>
&nbsp; &nbsp; &nbsp;*"_"* = wall<br>
&nbsp; &nbsp; &nbsp;*"+"* = door<br>
&nbsp; &nbsp; &nbsp;*"."* = floor
<br>
<br>
&nbsp; &nbsp; &nbsp;- The 4 symbols above should be left untouched
<br>
&nbsp; &nbsp; &nbsp;- Feel free to modify any other symbol in *symbols.json*

## items

- Walking over an item will make it dissappear
- There is no working inventory
- Items are not usable

## Doors
> A) All rooms have at least one door                     
> B) All unused (unconnected) doors that were parsed from the rooms JSON were deleted  

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
