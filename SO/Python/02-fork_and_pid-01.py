import time, sys, os
from emoji import emojize


parent = emojize(":person:")
child = emojize(":baby:")

# Think... what is inside spid?
for sequence in range(0, 2):
    spid = os.fork()
    ppid = os.getppid()
    pid = os.getpid()
    print(f"{parent} PPID:\t{ppid}\t{child} PID:\t{pid}\t spid: {spid}")
