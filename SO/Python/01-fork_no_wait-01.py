import time, sys, os
from emoji import emojize


parent = emojize(":person:")
child = emojize(":baby:")

# fork outside the for
os.fork()
for sequence in range(0, 2):
    ppid = os.getppid()
    my_pid = os.getpid()
    print(f"{parent} PPID:\t{ppid}\t{child} PID:\t{my_pid}\tSequence: {sequence}")
