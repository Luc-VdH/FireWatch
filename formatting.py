fle = open("MOP02J-20200322-L2V18.0.3.csv",'r')
fle2 = open("output.csv", 'w')
lns = []
splitty = []
fle.readline()
for line in fle:
    splitty = line.split(',')
    lns.append(splitty[0])
    lns.append(splitty[1])
    lns.append(splitty[2])
    lns.append(splitty[13])
    fle2.write(",".join(lns))
    lns.clear()

fle.close()
fle2.close()
