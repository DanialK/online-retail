* Download the dataset from https://archive.ics.uci.edu/ml/datasets/online+retail and place it in `data/Online_Retail.csv` 
* Download elasticsearch form https://www.elastic.co/downloads/elasticsearch and run it
* run `sbt assembly` to create the jar at `target/scala-2.11/online_retail_etl-assembly-0.1.jar`
* submit the spark job `spark-submit --master local[*] target/scala-2.11/online_retail_etl-assembly-0.1.jar`