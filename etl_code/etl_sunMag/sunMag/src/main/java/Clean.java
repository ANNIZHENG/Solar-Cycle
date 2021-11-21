import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;

public class Clean {
    public static class CleanMapper extends Mapper<Object, Text, Text, NullWritable>{
       static final int year = 1993; // change made here

        @Override
        protected void map(Object key, Text value, Mapper<Object, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
            if ((value.toString().length()>10)
                    &&(Character.isDigit(value.toString().charAt(0))
                    || (Character.isDigit(value.toString().charAt(2))))){

                if (value.toString().contains("\t")){ // for files with 'modif'
                    String[] values = value.toString().split("\t");
                    String day = values[0];

                    int index = 1;
                    int month = 1;
                    while (index < values.length){
                        context.write(new Text(year + "," + month + "," + day + "," + values[index].trim()), NullWritable.get());
                        index += 1;
                        month += 1;
                    }
                }
                else{ // otherwise (without 'modif')
                    String[] values = value.toString().split("  ");

                    String day = values[1];
                    if (day.substring(0,1).equals("0"))
                        day = day.substring(1,2); // format day in a specific form

                    int index = 2;
                    int month = 1;
                    while (index < values.length) {
                        if (values[index].equals("")) {
                            index += 1;
                            continue;
                        } else { // output example line: 1992,1,1,37
                            context.write(new Text(year + "," + month + "," + day + "," + values[index].trim()), NullWritable.get());
                            month += 1;
                            index += 1;
                        }
                    }
                }
            }
        }
    }

    public static class CleanReducer extends Reducer<Text,Text,Text, NullWritable>{
        @Override
        protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
            context.write(key,NullWritable.get());
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Clean");
        job.setNumReduceTasks(1);
        job.setJarByClass(Clean.class);
        job.setMapperClass(CleanMapper.class);
        job.setCombinerClass(CleanReducer.class);
        job.setReducerClass(CleanReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
