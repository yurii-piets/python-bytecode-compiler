print("\nLOOPS")

print("WHILE")
x = 0
while x < 5:
    print(x, end = " ")
    x+=1

print("\nFOR")

for x in range(2,10,3):
    print(x, end = " ")

print("\nFOREACH")


list = [1,2,3,4,1]

for x in list:
    print(x, end = " ")




for x in range(5):
    if x == 6:
        break
else:
    print("\nentered else")