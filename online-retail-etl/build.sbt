name := "online_retail_etl"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.3.0" % "provided",
  "org.apache.spark" %% "spark-sql" % "2.3.0" % "provided",
  "com.sksamuel.elastic4s" %% "elastic4s-core" % "6.3.7",
  "com.sksamuel.elastic4s" %% "elastic4s-http" % "6.3.7",
  "org.elasticsearch" %% "elasticsearch-spark-20" % "6.4.2",
  "com.github.pureconfig" %% "pureconfig" % "0.10.2"
)