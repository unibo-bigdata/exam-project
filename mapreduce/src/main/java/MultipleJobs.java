package multipleJobs;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * MapReduce job to join yellowTaxi.seq and zone.seq.
 */
public class MultipleJobs {
	

	/**
	 * First Mapper
	 */
	public static class FirstMapper
	extends Mapper<IntWritable, Text, IntWritable, Text>{		

	public void map(IntWritable key, Text value, Context context) 
			throws IOException, InterruptedException {
 		    
			// Mapper logic
			
		}
		
	}
	
	/**
	 * First Reducer
	 */
	public static class FirstReducer
	    extends Reducer<IntWritable,Text,IntWritable,Text> {
	 
		public void reduce(IntWritable key, Iterable<Text> values, Context context) 
				throws IOException, InterruptedException {

			// Reducer logic
			
		}	 
	 
	}
	
	/**
	 * Second Mapper
	 */
	public static class SecondMapper
	extends Mapper<IntWritable, Text, IntWritable, Text>{		

	public void map(IntWritable key, Text value, Context context) 
			throws IOException, InterruptedException {
 		    
			// Mapper logic
			
		}
		
	}
	
	/**
	 * Second Reducer
	 */
	public static class SecondReducer
	    extends Reducer<IntWritable,Text,IntWritable,Text> {
	 
		public void reduce(IntWritable key, Iterable<Text> values, Context context) 
				throws IOException, InterruptedException {

			// Reducer logic
			
		}	 
	 
	}

	
	public static void main(String[] args) throws Exception {

		ArrayList<Job> jobs = new ArrayList<>();
		
		jobs.add( Job.getInstance(new Configuration(), "First job") );
		jobs.add( Job.getInstance(new Configuration(), "Second job") );
		
		// Output path and data type of first job 
		// should be the same as the input ones of the second job  

		for (Job job: jobs) {
            if (!job.waitForCompletion(true)) {
                System.exit(1);
            }
        }
		
		// Do not use the old, deprecated APIs with job2.addDependingJob(job1)
	 
	}
}
