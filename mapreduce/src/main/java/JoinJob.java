package join;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;

/**
 * MapReduce job to join yellowTaxi.seq and zone.seq.
 */
public class JoinJob {
		
	/**
	 * Mapper for first dataset
	 */
	public static class FirstMapper
    	extends Mapper<IntWritable, Text, IntWritable, Text>{		

		public void map(IntWritable key, Text value, Context context) 
				throws IOException, InterruptedException {
 		    
			// Mapper logic
			
			// Output should be formatted as (joinKey, value), 
			// where the value also specifies which is the source. It can be either:
			// - a string formatted like "source-value" to be parsed by the reducer
			// - an object of a custom class that contains both information
			
		}
		
	}
	
	/**
	 * Mapper for second dataset
	 */
	public static class SecondMapper
	extends Mapper<IntWritable, Text, IntWritable, Text>{		

		public void map(IntWritable key, Text value, Context context) 
				throws IOException, InterruptedException {
 		    
			// Mapper logic
			
			// Output should be formatted as (joinKey, value), 
			// where the value also specifies which is the source. It can be either:
			// - a string formatted like "source-value" to be parsed by the reducer
			// - an object of a custom class that contains both information
			
		}
		
	}

	/**
	 * Reducer
	 */
	public static class JobReducer
	    extends Reducer<IntWritable,Text,IntWritable,Text> {
	 
		public void reduce(IntWritable key, Iterable<Text> values, Context context) 
				throws IOException, InterruptedException {
			
			List<String> firstDatasetRecords = new ArrayList<String>();
			List<String> secondDatasetRecords = new ArrayList<String>();
		 		 
			for(Text val : values) {

				// Parse values and recognize where it comes from
				
			}
		 
			for(String first : firstDatasetRecords) {
				for(String second : secondDatasetRecords) {

					// Reducer logic on joined records
					
				}		 
			} 		
		}	 
	 
	}

	
	public static void main(String[] args) throws Exception {
	
		Path firstInputPath = new Path(args[0]);
		Path secondInputPath = new Path(args[1]);
		
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "Join job");
		
		MultipleInputs.addInputPath(job, firstInputPath, KeyValueTextInputFormat.class, FirstMapper.class);
		MultipleInputs.addInputPath(job, secondInputPath, KeyValueTextInputFormat.class, SecondMapper.class);

		// Etc..
	 
	}
}
