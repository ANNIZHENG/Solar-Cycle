import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;

public class CountOriginalRecs {
    public static class CountOriginalRecsMapper extends Mapper<Object, Text, Text, IntWritable>{
        private final static IntWritable one = new IntWritable(1);
        private final Text word = new Text("Total count of the original records:");

        @Override
        protected void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
            if ((value.toString().length()>10)
                    &&(Character.isDigit(value.toString().charAt(0))
                    || (Character.isDigit(value.toString().charAt(2))))){

                if (value.toString().contains("\t")){ // for files with 'modif'
                    String[] values = value.toString().split("\t");
                    int index = 1;
                    while (index < values.length){
                        context.write(word, one);
                        index += 1;
                    }
                }
                else{ // otherwise (without 'modif')
                    String[] values = value.toString().split("  ");
                    int index = 2;
                    while (index < values.length) {
                        if (values[index].equals("")) {
                            index += 1;
                            continue;
                        } else {
                            context.write(word, one);
                            index += 1;
                        }
                    }
                }
            }
        }
    }
    public static class CountOriginalRecsReducer extends Reducer<Text, IntWritable, Text,IntWritable>{
        private IntWritable result = new IntWritable();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "original line count");
        job.setNumReduceTasks(1);
        job.setJarByClass(CountOriginalRecs.class);
        job.setMapperClass(CountOriginalRecsMapper.class);
        job.setCombinerClass(CountOriginalRecsReducer.class);
        job.setReducerClass(CountOriginalRecsReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}