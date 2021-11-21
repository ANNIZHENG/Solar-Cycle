# Solar-Cycle

Steps we took to analyze change in Solar Magnetic Field Strength from 1992 to 2020:

1. /data_ingest/data_ingest_sunMag directory contains two txt files each indicates the step of dowloading data from the internet and uploading them to Peel
2. /etl_code/etl_sunMag directory contains a /etl_code/etl_sunMag/sunMag file that uses MapReduce to convert raw data into data with specific format (year, month, day, magnetic field strength) that helps us to either interpret that data in Hive or visualize it. On the other hand, etl_code/etl_sunMag/peel_execute.txt contains command line codes for running MapReduce job on Peel.
3. /profiling_code/profiling_sunMag/hive_sunmag.txt contains command lines that load normalized data generated from the last step to a Hive table. It also contains commands used for data analysis.
4. /ana_code/ana_sunMag/analysis_using_hive.pdf contains screenshots of the execution of analytical hive commands. It mainly displays the result of calculating the maximum, minimum, and average magnetic field strength of each 11-year solar cycle, which we may use to compare between different solar cycles.


Regarding Two Shared Input Data for Running Solar Magnetic Field Strength Analysis:

There are two input data files which I shared to Professor Malavet and two graders (TAs). They are called "data" and "all_mag".

1. "data" contains every day's solar magnetic field strength data from year 1992 to 2020 downloaded from a website (http://wso.stanford.edu/#MeanField).
This is the input data the MapReduce job in /etl_code/etl_sunMag/sunMag/src/main/java/Clean.java file. 
2. "all_mag" contains a normalized txt file produced by Clean.java mentioned above.
It is the input data of the Hive job in profiling_code/profiling_sunmag.txt. Note that all Hice command lines should be run in the NYU Peel system (beeline). 
