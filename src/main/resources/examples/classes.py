d1 = Dog("Scruffy", 5);

print(d1)
print(Dog.random())


class Dog:

    def __init__(self, name, age):
        self._name = name
        self._age = age
    def get_age(self):
        return self._age
    def get_name(self):
        return self._name

    def set_age(self, age):
        self._age = age
    def set_name(self, name):
        self._name = name

    def random():
        return 7

    def __str__(self):
        return "Dog:\nName " + self._name + "\nAge: " + str(self._age)