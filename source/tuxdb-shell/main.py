from database import Database
import readline

from builtin import *

if __name__ == "__main__":

    print("\n-- TuxDB Shell v1.0.0 --\n")
    print("Say: help()\n")
    
    db = Database()

    block = ""

    while (1):
        block = input("$ ")

        try:
            functionReturn = eval(block)

            if not functionReturn is None:
                print(functionReturn)
        except Exception as e:
            print(e)
