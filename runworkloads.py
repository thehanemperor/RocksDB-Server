import os 
import sys

def runWorkload(runType,dbType):
    parent = os.getcwd()
    print(runType,dbType)
    exePath = os.path.join(parent,"bin/ycsb.bat")
    outPath = os.path.join(parent,"testResult/threads/workload{}/{}DB-threads{}.txt")
    print(parent)
    
    # if runType == "run":
    #     run(parent,exePath,outPath,dbType,"4","2")  
    #     run(parent,exePath,outPath,dbType,"4","4")
    #     run(parent,exePath,outPath,dbType,"4","8")
    #     run(parent,exePath,outPath,dbType,"4","10")
    #     run(parent,exePath,outPath,dbType,"4","20")
    # else:
    #     load(parent,exePath,outPath,dbType,"5","20")
    #     # load(parent,exePath,outPath,dbType,"5","4")
    #     # load(parent,exePath,outPath,dbType,"5","8")
    #     # load(parent,exePath,outPath,dbType,"5","10")
    #     # load(parent,exePath,outPath,dbType,"5","20")
    print("done")

def run(parent,exePath ,outPath,dbType,workload,threads):
    for i in range(5):
        currPath = os.path.join(parent,"testResult\\threads\\workload{}\\{}DB-threads{}.txt".format(workload,dbType,threads)) 
        print(exePath,currPath)
        os.system("""{} run {} -s -P workloads/workload4 -p operationcount=100000 -threads {} >> {}""".format(exePath, dbType, threads,currPath))

def load(parent,exePath ,outPath,dbType,workload,threads):
    
    currPath = os.path.join(parent,"testResult\\threads\\workload{}\\{}DB-threads{}.txt".format(workload,dbType,threads)) 
    print(exePath,currPath)
    os.system("""{} load {} -s -P workloads/workload4 -p recordcount=100000 -threads {} >> {}""".format(exePath, dbType, threads,currPath))

if __name__ == "__main__":
    arg1,arg2 = sys.argv[1],sys.argv[2]
    
    dbType = "rocksdbserver" if "rock" in arg2 else "mongodb"
    runWorkload(arg1,dbType)    