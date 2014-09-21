# makefile for prog10
# Daniel Eisenberg
# CSc 352 Fall 2011, Program #10

# Macros

    CC          = gcc           # compiler
    LIBRARIES   = -lncurses     # required linked libraries

# Targets

all: linkedlist prog10

linkedlist: linkedlist.c
	$(CC) -c -o linkedlist.o linkedlist.c

prog10:     interf.o linkedlist.o
	$(CC) -o prog10 interf.o linkedlist.o prog10.c $(LIBRARIES)
