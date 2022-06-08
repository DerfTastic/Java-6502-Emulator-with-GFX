(My Video card/GFX capabilities on the bottom of this page)

# Java 6502 Emulator
 
This is a project I started because I wanted a place to write and test code for my Ben Eater 6502 kit. After seeing some other emulators written in C++ ~~(by sane people)~~, I tried downloading them but had trouble building them. So, I figured I would just write my own. It was a fun process, and was greatly helped by [OneLoneCoder's NES Emulator Tutorial](https://github.com/OneLoneCoder/olcNES). The LCD simulator was 100% me, and I'm proud of it.

UNIMPLEMENTED FEATURES:
 - All 65c02-specific features (it is functionally a regular 6502 target when compiling code).
 - DECIMAL MODE
 - LCD Memory (sorry, it's a character-level simulation)
 - LCD Reads (always returns not busy)
 - Most features on the 65C22 (will always read 0).

Some might ask, why write an emulator in Java? And I would respond: "Because no one else would." Sure, Java is terribly slow (more than 1000x slower than the original!), and the fact that Java's ```byte```s and ```short```s are a pain to work with because they're signed, but it's the language I'm best in so I don't care ;)

Feel free to fork it, improve it, whatever, just link back to here. Enjoy!

**The font isn't mine!**

## Controls
- C - Toggle Clock
- Space - Pulse Clock
- H/J - Decrement/Increment RAM Page
- K/L - Decrement/Increment ROM Page
- R - Reset
- S - Toggle Slow Clock
- I - Trigger Interrupt (the CA1 pin on the VIA)
   
You can load ```.bin``` files into RAM or ROM using the File Pickers in the top right. It should be fully compatible with any binary compiled for the 6502 kit, except if it uses any unimplemented features. I might get to these sometime in the future. If I do, the repo will be updated.

# World's Worst Video Card capabilities
I (DerfTastic) have since added graphical capabilities to this emulator as shown in Ben's "World's Worst Video Card" series (https://www.youtube.com/watch?v=2iURr3NBprc). It works just like the ones in the video and spawns a separate window, like the LCD. It even shows the portion of the screen that is invisible to the monitor (horizontal blanking period) as well as a little "pixel cursor" (just a little white box around the pixel that was last written to) for extra debugging help! 

## Screenshots
![Screenshot 1](screenshots/screenshot1.png?raw=true)
![Screenshot 2](screenshots/screenshot2.png?raw=true)
