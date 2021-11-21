import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class CleanMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private final static IntWritable zero = new IntWritable(0);
    private Text word = new Text();

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
	String[] splited = line.split(",");
	try
	{
		//select year,month and date as the first part
	    String cleanedData = splited[0]+'-'+splited[1]+'-'+splited[2];
	    int number = Integer.parseInt(splited[4]);
			//use number as the intwritable part
            context.write(new Text(cleanedData), new IntWritable(number));
	}
	catch(Exception e){
	}	    
    }
}            
