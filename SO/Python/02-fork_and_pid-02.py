import time, sys, os
from emoji import emojize


parent = emojize(":person:")
child = emojize(":baby:")

# SPID is the way i know if it's a child with no sons
for sequence in range(0, 2):
    spid = os.fork()
    ppid = os.getppid()
    pid = os.getpid()
    if spid == 0:
        print(f"Soy el PID {pid} y no tengo hijos")
    else:
        print(f"Soy el PID {pid} y acabo de tener un hijo: {spid}")
