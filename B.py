def fun(s):
    goal="hello"
    count=0
    for i in s:
        if i==goal[count]:
            count+=1
        
            if count==5:
              return "YES"

    return "NO"
s=input()
print(fun(s))