t = int(input())


for i in range(t):
  
    n = int(input())
    arr = list(map(int, input().split()))

    max_value = max(arr)

    operations = 0
    for value in arr:
        operations =max(operations,max_value - value)
    

    print(operations)
