import random as r
import string as s
valasztas = 0
while valasztas is not int:
    try:
        valasztas = int(input('Szamokat vagy betuket szeretnel generalni(1 szamok 2 betuk): '))
        if valasztas > 0:
            break
        else:
            print("1v2")
    except ValueError:
        print('Kerlek 1 v 2')
db = 0
while db is not int:
    try:
        db = int(input('Hany darab szamot/szoveget szeretnel generalni: '))
        if valasztas == 1 or valasztas == 2:
            break
        else:
            print("xg")
    except ValueError:
        print('Kerlek szamot irj be')

if valasztas == 1:
    with open("ki.txt", "w") as f:
        for i in range(db):
            x = r.randint(1,20)
            f.write(str(x) + ';')
if valasztas == 2:
    alsoertek = int(input("Also ertek: "))
    felsoertek = int(input("Felso ertek: "))
    with open("ki.txt", "w") as f:
        for i in range(db):
            y = ""
            for i in range(r.randint(alsoertek, felsoertek)):
                x = r.choice(s.ascii_letters)
                y = y+x
            f.write(str(y) + ";")
            print("")