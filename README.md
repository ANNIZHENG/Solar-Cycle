# Solar-Cycle

Steps we took to analyze change in Sunspot Change from 1992 to 2020 (Yizhou Wan):

1. Use code in etl_code/etl_sunSpot/MapReduce directory to normalize the raw file and retrieve only the number of sunspots of each day of the years.
2. Then I put it as a txt file and sent it to Final_Proj/Hive_Table/second_num as the final version for hive table (which was performed in NYU Peel).
3. I ran the commands in profiling_code/profiling_sunSpot/hive_command_for_sunspots.txt, which would create a main table and three sub tables presenting each cycle.
4. After creating the Hive table, we calculated the max, min and average number of sunspots for each cycle.
5. The results of these table commands can be found in the screenshots in ana_code/ana_sunSpot/analysis_using_hive directory


Steps we took to analyze change in Solar Magnetic Field Strength from 1992 to 2020 (Anni Zheng):

1. /data_ingest/data_ingest_sunMag directory contains two txt files each indicates the step of dowloading data from the internet and uploading them to Peel
2. /etl_code/etl_sunMag directory contains a /etl_code/etl_sunMag/sunMag file that uses MapReduce to convert raw data into data with specific format (*year, month, day, magnetic field strength*) that helps us to either interpret that data in Hive or visualize it. On the other hand, etl_code/etl_sunMag/peel_execute.txt contains command line codes for running MapReduce job on Peel.
3. /profiling_code/profiling_sunMag/hive_sunmag.txt contains command lines that load normalized data generated from the last step to a Hive table. It also contains commands used for data analysis.
4. /ana_code/ana_sunMag/analysis_using_hive.pdf contains screenshots of the execution of analytical hive commands. It mainly displays the result of calculating the maximum, minimum, and average magnetic field strength of each 11-year solar cycle, which we may use to compare between different solar cycles.

Regarding Two Shared Input Data for Running Solar Magnetic Field Strength Analysis:

There are two input data files which I shared to Professor Malavet and two graders. They are called "data" and "all_mag".
1. "data" contains every day's solar magnetic field strength data from year 1992 to 2020 downloaded from a website (http://wso.stanford.edu/#MeanField).
This is the input data the MapReduce job in /etl_code/etl_sunMag/sunMag/src/main/java/Clean.java file. 
2. "all_mag" contains a normalized txt file produced by Clean.java mentioned above.
It is the input data of the Hive job in profiling_code/profiling_sunmag.txt. Note that all Hice command lines should be run in the NYU Peel system (beeline).


Steps we took to analyze change in Sunspot Change from 1992 to 2020: (Yizhou Wan & Anni Zheng):

1. Address for raw data of solar luminosity is in: etl_code/etl_Luminosity/Lomnicky
2. For the Lomnicky folder, we use shell scripts written in the etl_code/etl_Luminosity/Data_Filter_Lomnicky/data_merge.py and this python code to produce total_year.txt 
3. We use total_years.txt for the further works performed in etl_code/etl_Luminosity/normalizeLuminosity which normalizes the data into a specific form that can be used for visualization.

Regarding Two Shared Input Data for Running Luminosity data in MapReduce:
1. One is called Lomnicky which is also located in etl_code/etl_Luminosity
2. Another is called all_luminosity/total_year.txt
