 #!/bin/bash
echo "sleep..."
sleep 3

 for i in 1 2 3 4 5
 do 
 echo "threads 20 $i"
 ./bin/ycsb run rocksdbserver -s -P workloads/workloadb -p operationcount=100000 -threads 20 >> testResult/threads/workload2/rocksDB100000-threads20.txt
 done
 
 echo "sleep..."
sleep 3

 for i in 1 2 3 4 5
 do
 echo "threads 40 $i"
 ./bin/ycsb run rocksdbserver -s -P workloads/workloadb -p operationcount=100000 -threads 40 >> testResult/threads/workload2/rocksDB100000-threads40.txt
 done 

echo "sleep..."
sleep 3

 for i in 1 2 3 4 5
 do
 echo "threads 80 $i"
 ./bin/ycsb run rocksdbserver -s -P workloads/workloadb -p operationcount=100000 -threads 80 >> testResult/threads/workload2/rocksDB100000-threads80.txt
 done 

 echo "sleep..."
sleep 3

 for i in 1 2 3 4 5
 do 
 echo "threads 100 $i"
 ./bin/ycsb run rocksdbserver -s -P workloads/workloadb -p operationcount=100000 -threads 100 >> testResult/threads/workload2/rocksDB100000-threads100.txt
done 
