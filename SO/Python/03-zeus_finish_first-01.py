import time, sys, os
from emoji import emojize


parent = emojize(":person:")
child = emojize(":baby:")
zeus = emojize(":old_man:")
fpid = os.getpid()  # The PID of the original father


for sequence in range(0, 3):
    spid = os.fork()
    pid = os.getpid()
    if spid != 0:
        print(
            f"{zeus}({fpid}) Had a new son {child}({spid}); but will wait for my sons to finish"
        )
    else:
        time.sleep(0.1)
        ppid = os.getppid()
        # If the original fpid matches the actual ppid.
        if fpid == ppid:
            print(f"{child}({pid}) Thanks {ppid} for waiting!")
        else:
            print(
                f"I'm {child}({pid}) my father was {fpid}; but now is {ppid}. I'm an orphan!"
            )
        sys.exit(0)

print(f"{zeus} Father finished.")
sys.exit(0)
