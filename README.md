# Sequence Diagram Editor
Sequence Diagram Editor is a simple editor for sequence diagrams. The main idea is to have is a simple script that generates as image; being a text file, the script can be versioned under a VCS.

## Script syntax
 
The script is composed by four sections:
* title
* abstract
* entities
* messages

Every section is identified by its name preceded by a dash (case unsensitive).

### Title
The title must be on one line

### Abstract
The abstract must be on one line too, but it's possible using a newline character '\n' inside the text to have multiple lines rendered on the diagram.

### Entities
The entities are defined with this form:
```sh
ID|LABEL[|TYPE]
```
where TYPE is optional and can have one of these values: (GENERIC,STORAGE,ACTOR). If missing, it will be defaulted to GENERIC. The ID is the identifier of the entity in the *messages* section.

### Messages
The messages between two entities are in form of:
```sh
ROW[..ENDING_ROW] | FROM[:TO] | MESSAGE[=>RESPONSE] [|ORIENTATION]
```
where:
* ROW is the row number where the message has to appear
* the optional ENDING_ROW is for drawing a sort of working thread on the receiver side
* FROM:TO sets the entity from which the message starts and to whom is directed. The :TO part is optional, if we need only to write a label at a specified row for this entity
* MESSAGE is the label of the message; if it contains the string "=>" the diagram will also show an immediate response generated from the TO entity towards the FROM entity, with the label identified by what follows the "=>"
* ORIENTATION is an optional value to specify on which side of the vertical line (starting from the entity) the label must be written

## Sample

This script:
```sh
- Title
This is the title 

- Abstract
This is the abstract of the diagram.\nUse newlines\nfor splitting it into more than one row.

- Entities
cc|Client 1|actor
ss|Server
db|Storage 1|storage
bu|Backup|storage

# messages are in form of ROW[..ENDING_ROW]|FROM:TO|MESSAGE[=>RESPONSE]|ORIENTATION where ORIENTATION = (LEFT,RIGHT)
- Messages
0|cc:ss|Update user data
0..2|ss|starts working
1|ss:db|Saving data=>OK
2|ss:cc|Update was ok
4|db:bu|Backup thread
5|bu:db|Process finished
```
will produce this diagram:
![Sequence Diagram Editor screenshot](https://raw.githubusercontent.com/andreaiacono/andreaiacono.github.io/master/img/SequenceDiagramEditor.png)
