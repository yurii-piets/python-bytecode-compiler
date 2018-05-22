list = []

list.append("house")
list.append("mouse")
list.append("blouse")

print(list)
print("Length:", len(list))
print("Index 1:", list[1])
list.insert(1, "grouse")

print(list)
del(list[1])
print(list)



list2 = []

list2.append("house")
list2.append("mouse")
list2.append("blouse")

list.extend(list2)
print(list)

list3 = []
list3.append(list)
list3.append(list2)
print("List3:", list3)

print(list3[1][2])

list3[0] = 4
print(list3)
