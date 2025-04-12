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
            print(f"{child}({pid}) Thanks {zeus}({ppid}) for waiting!")
        else:
            print(
                f"I'm {child}({pid}) my father was {fpid}; but now is {ppid}. I'm an orphan!"
            )
        sys.exit(0)

childProcExitInfo = os.wait()

print(
    f"{zeus} The exit code of my last son with PID {childProcExitInfo[0]} was {childProcExitInfo[1]}. My job is done"
)
sys.exit(0)
