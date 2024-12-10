def add(a,b):
    return a+b
def subtract(a,b):
    return a-b
def multiply(a,b):
    return a*b
def divide(a,b):
    if(b!=0):
        return a/b
    else:
        print("division by zero is not allowed")
        
def factorial(n):
    if n==0:
        return 1
    elif n>0:
        return n * factorial(n-1)
    else:
        print("not allowed")    
def power(base,exponenet):
    return base**exponenet
print(add(3,5))
print(subtract(8,5))
print(multiply(3,5))
print(divide(15,5))
print(factorial(5))
print(power(3,5))