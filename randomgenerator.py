import random as r
import string as s

valasztas = 0
while valasztas is not int:
    try:
        valasztas = int(input('Számokat vagy betűket szeretnél generálni?\n1 Számok generálása, 2 Betűk generálása: '))
        if valasztas == 1 or valasztas == 2:
            break
        else:
            print("Kérlek az 1es vagy 2es számot írd be!")
    except ValueError:
        print('Számot írj be!')
db = 0

while db is not int:
    try:
        db = int(input('Hány darab számot/betűt szeretnál generálni: '))
        if db > 0:
            break
        else:
            print("Nullánál nagyobb számot!")
    except ValueError:
        print('Kérlek számot írj be!')

if valasztas == 1:
    with open("ki.txt", "w") as f:
        for i in range(db):
            x = r.randint(1,20)
            f.write(str(x) + ';')

if valasztas == 2:
    alsoertek = int(input("Alsó értek: "))
    felsoertek = int(input("Felső értek: "))
    with open("ki.txt", "w") as f:
        for i in range(db):
            y = ""
            for i in range(r.randint(alsoertek, felsoertek)):
                x = r.choice(s.ascii_letters)
                y = y+x
            f.write(str(y) + ";")

print("Ellenőzéséi folyamat elkezdődött!")
iva = 0
igazvhamis = False
while iva is not int:
    try:
        iva = int(input('Szöveget vagy számot szeretnél ellenőrizni?\n1 Számok ellenőrzése, 2 Betűk ellenőrzése: '))
        if iva == 1 or iva == 2:
            break
        else:
            print("A kettő szám közül válassz!")
    except ValueError:
        print('Kérlek számot írj be!')

db2 = 0
if iva == 1:
    with open("ki.txt", "r", encoding="UTF-8") as f:
        x = f.readline().split(";")
        for i in range(len(x)-1):
            igazvhamis = x[i].isnumeric()
            if igazvhamis == True:
                print(f"Az {x[i]} adat szám!")
                db2 += 1
            else:
                print(f"Az {x[i]} adat nem szám!")

if iva == 2:
    with open("ki.txt", "r", encoding="UTF-8") as f:
        x = f.readline().split(";")
        for i in range(len(x)-1):
            igazvhamis = x[i].isalpha()
            if igazvhamis == True:
                print(f"Az {x[i]} adat betű!")
                db2 += 1
            else:
                print(f"Az {x[i]} adat nem betű!")

if db2 == len(x)-1:
    print("Minden adat helyes")
else:
    print("Valamelyik adat nem helyes")