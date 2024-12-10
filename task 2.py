def fibonacci(n):
    if n<=1: return n 
    f=0
    s=1

    for i in range(2,n+1):
        c=f+s
        f=s
        s=c
    return s
print(fibonacci(10))