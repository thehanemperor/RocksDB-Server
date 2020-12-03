 #!/bin/bash
 
 for i in {1..5} 
 do 
 ./bin/ycsb run rocksdbserver -s -P workloads/workloada -p operationcount=100000 -threads 10 >> testResult/threads/workload1/rocksDB100000-threads10.txt 
 done
 for i in {1..5}
 do 
 ./bin/ycsb run rocksdbserver -s -P workloads/workloada -p operationcount=100000 -threads 20 >> testResult/threads/workload1/rocksDB100000-threads20.txt
 done
 
 for i in {1..5}
 do
 ./bin/ycsb run rocksdbserver -s -P workloads/workloada -p operationcount=100000 -threads 40 >> testResult/threads/workload1/rocksDB100000-threads40.txt
 done 

 for i in {1..5}
 do
 ./bin/ycsb run rocksdbserver -s -P workloads/workloada -p operationcount=100000 -threads 80 >> testResult/threads/workload1/rocksDB100000-threads80.txt
 done 

 for i in {1..5}
 do 
 ./bin/ycsb run rocksdbserver -s -P workloads/workloada -p operationcount=100000 -threads 100 >> testResult/threads/workload1/rocksDB100000-threads100.txt
done 