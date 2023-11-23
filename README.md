# Exam project

Big Data course (81932), University of Bologna.

As of A.Y. 2021/22, the project must be carried out on your AWS Virtual Lab.

## Instructions

- Find a suitable dataset
  - One or more files/tables/collections of data
  - Minimum 5GB of data
- Notify the teacher about the dataset and the main job that you mean to carry out
  - The execution plan of the main job must include **at least 2 shuffles**; some sample patterns are shown below
- Load the dataset on S3
  - **Follow the instructions below** to enable access to your bucket from other accounts
  - Consider working on a sample of the dataset for faster development and debugging
- Write a notebook "narrating" an analytical session
  - Include the configuration of the executors (see example below); feel free to use more powerful machines (and more powerful executors) if needed
    ```
    %%configure -f
    {"executorMemory":"8G", "numExecutors":2, "executorCores":3, "conf": {"spark.dynamicAllocation.enabled": "false"}}
    ```
  - Describe the schema and the content of the dataset
  - Include a pre-processing section if needed
  - Define some simple jobs to do some initial assessment of the dataset
  - Define and discuss the main job
    - The main job should start from non-cached data (for the sake of performance evaluation)
    - Evaluate multiple execution plans
    - Evaluate optimization strategies (caching, partitioning, broadcast, etc.)
    - Discuss if/when/why a strategy/execution plan is better than the other(s)
  - Check and discuss results (e.g., using Power BI, Tableau, Matplotlib, whichever you prefer)
  - **Important**: the code in the notebook should be optimized for its execution as if it was a production job; for instance:
    - Do not cache when not needed
    - Do not collect unnecessarily
- To deliver the project, make sure that you have at least 10$ left in your credits, then notify the teacher via email and send:
  - The notebook (with all the cells executed)
  - The .ppk (or .pem) file used to connect to the cluster via SSH
  - Any additional material (e.g., Power BI or Tableau file, or pictures used in the notebook)
- Evaluation
  - 0 for sufficient projects
  - 1 or 2 points (to be added to the vote of the oral exam) based on technical complexity, performance evaluation, and creativity


## Datasets

Go on [Kaggle](https://www.kaggle.com/datasets?fileType=csv&sizeStart=5%2CGB) and look for CSV datasets with minimum 5GB of data. Many of them contain multimedia files (e.g., audio tracks, images), so you need to search through the pages for valid textual datasets. Some examples:

- [US Stock Dataset](https://www.kaggle.com/datasets/footballjoe789/us-stock-dataset?select=Data)
- [Ethereum Blockchain](https://www.kaggle.com/datasets/buryhuang/ethereum-blockchain)
- [COVID-19 Complete Twitter Dataset](https://www.kaggle.com/datasets/imoore/covid19-complete-twitter-dataset-daily-updates)
- [Official Reddit r/place Dataset CSV](https://www.kaggle.com/datasets/antoinecarpentier/redditrplacecsv)
- [Simulated Transactions](https://www.kaggle.com/datasets/conorsully1/simulated-transactions)

Alternative examples:

- [Amazon Reviews](https://s3.amazonaws.com/amazon-reviews-pds/readme.html)
- [NYC Taxi trips](https://www1.nyc.gov/site/tlc/about/tlc-trip-record-data.page)
- [List 1](https://www.quora.com/Where-can-I-find-large-datasets-open-to-the-public)
- [List 2](https://hadoopilluminated.com/hadoop_illuminated/Public_Bigdata_Sets.html)
- [BigDataBench (datasets + generator)](https://www.benchcouncil.org/BigDataBench/download.html#DownloadGenerator)
- [Other benchmarks](https://www.predictiveanalyticstoday.com/bigdata-benchmark-suites/)

If you find some broken links, please notify the teacher. Thanks.


## S3 configuration

Follow these instructions to enable access to your bucket from other accounts. This is needed to allow the teacher to access the datasets of your project from a different account.

Beware that the following will make the data in your bucket public, so don't follow this approach in a production/private/sensitive environment.

  - Go to S3, select your bucket, and go to the "Authorizations" ("Autorizzazioni") tab
  - Click on "Edit" ("Modifica") on the "Block public access" ("Blocco dell'accesso pubblico") card
    - Uncheck all options and save
  - Click on "Edit" ("Modifica") on the "Bucket's policy" ("Policy del bucket") tab
    - Enter the following policy, replace the name of the bucket with your bucket (if you prefer, you can restrict the policy to the folder containing the dataset) and save
      ```
      {
      "Version": "2012-10-17",
      "Statement": [
          {
          "Sid": "PublicStatement",
          "Effect": "Allow",
          "Principal": "*",
          "Action": "s3:*",
          "Resource": "arn:aws:s3:::unibo-bd2324-egallinucci/*"
          }
      ]
      }
      ```

## Main job patterns

Here are some typical job patterns to get an execution plan with at least 2 shuffles. Consider the MovieLens dataset used in Lab 303.

- *Join and aggregate*: this is trivial if your dataset is composed by multiple files/tables; for instance:
  - Join movies with ratings
  - Aggregate to calculate the average rating of each genre
- *Double aggregation*: do a first pre-aggregation on two (or more) attributes, then further aggregating on a subset of those attributes; for instance:
  - Aggregate ratings by movieid and year, to get the average rating of each movie in each year
  - Aggregate by year to calculate the average of the average ratings for each year (which could be interpreted as the "average quality" of the movies seen every year)
- *Self-join*: do a first aggregation on one attribute, then join the result with the original dataset and re-aggregate on a different attribute; for instance:
  - Aggregate by movieid to calculate the average rating and the number of ratings for each movie, then calculate a "Class" attribute as follows
    - If the count is <10, classify the movie as "Insufficient ratings"
    - If the count is >=10 and the average rating is <=2, classify the movie as "Bad"
    - If the count is >=10 and the average rating is >2 and <=4, classify the movie as "Average"
    - If the count is >=10 and the average rating is >4, classify the movie as "Good"
  - Join the result with the ratings on the movieid
  - Aggregate by class and year to see how many ratings are give each year on the different classes of movies