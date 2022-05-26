# TakeYourNote - my personal project


## What will the application do?
TakeYourNote is a note-taking desktop application. The meaning of TakeYourNote is to help users better manage their
notes!


## Who will use it?
Everyone who knows how to operate computer can use TakeYourNote as their note-taking
application.


## Why is this project of interest to you?
I usually use Microsoft Word, Onenote and Typora to take notes. However, I'm unsatisfied
all of them. I like using markdown because it is more efficient to edit and layout
content than Word. However, Typora doesn't have hierachical note levels which makes it
difficult to organize information. Onenote wonderfully succeed the function of Word and
provide good hierachical levels. However, I can't use Markdown in Onenote.
Word has some useful function like picture editting that doesn't exist in both Typora
and Onenote.

Therefore, I want somedays to make a note-taking application that can integrate those application of their
most useful functions. Basically a combination of Onenote and Typora. 

## User Stories
- As a user, I want to be able to add a note to a notebook folder
- As a user, I can create my own notebook folder
- As a user, I want to be able to delete note from a notebook folder and move it to trash folder
- As a user, I can add back the note from trash folder to a designated notebook folder
- As a user, I can search the note using title

- As a user, when I select the quit option from the application menu, 
  I want to be reminded to save my work. For example, if I create three notes to notebook and delete one from 
  notebook, I can save the state to keep two notes in notebook and one in trash folder
- As a user, I can reload that state from file and resume exactly where they left off at some earlier time

Pending functions:
- As a user, I can delete a note completely
- As a user, I can delete a notebook folder
- As a user, I can add tags to a note
- Use markdown to write down note in TakeYourNote.

# Phase 4: task 2
Wed Mar 30 15:55:59 PDT 2022
Event log cleared.
Wed Mar 30 15:56:07 PDT 2022
Folder testbook has been added.
Wed Mar 30 15:56:14 PDT 2022
Folder testbook has added note title1
Wed Mar 30 15:56:23 PDT 2022
Folder demobook has added note title2

# Phase 4: task 3
The structure is not so clear especially for the ui. I design ui based on dividing
swing elements. I separate swing elements into pieces and then assemble them in MyFrame. I think it is
not a perfect solution. I would better divide them based on function because it seems easy to read but because
of time limit, I can only use this dirty way to complete my task.
There is a especial thing that I want to mention - the design of Jtree that represent the hierarchy structure
of notes and folders in ui. I want if I click the node, I can see title and note content immediately on the JText area;
I want when I write down the content in the JText area, the content can remain here. The solution for getting
content and render it to the JtextArea is that using Tree Listener, get the title String (which is the object
that be passed to the DefaultTreeNode when the time it was created), and then traverse the folder list to find the note.
I think the solution is not perfect.
the data design is also not so effective. 
  