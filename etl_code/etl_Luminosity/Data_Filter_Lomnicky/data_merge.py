import re


 '''
 shell script part before working on this:
 ls years_data > years.txt
 '''
f=open('years.txt')
c=f.read()
#print(c)
f.close()
f1 = open('total_data.txt',"a")
for filename in c.split('\n'):
    f = open("years_data/"+filename+"/"+filename+".TBL")
    c = f.read()
    f.close()
    f1.write(c)

f1.close()
f1 = open('total_data.txt')
c1 = f1.read()
splited = c1.split('\n')
year = 1992
f2 =  open('total_year.txt',"a")
for a in range(len(splited)):
    if a%46 >=5 and a%46<=41 and len(splited[a])>0:
        f2.write(str(year+(a//46))+","+re.sub('\s+', ',', splited[a])+"\n")