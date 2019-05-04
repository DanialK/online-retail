package com.solvemprobler.utils

import org.apache.spark.sql.{DataFrame, SaveMode}
import pureconfig.generic.auto._

object WriteUtils extends WithConfig {
  def writeToEs(df: DataFrame, EsIndex: String, EsType: String): Unit = {
    df.write.format("org.elasticsearch.spark.sql")
      .mode(SaveMode.Append)
      .option("es.resource", s"$EsIndex/$EsType")
      .option("es.nodes", config.elasticsearch)
      .option("es.port", "9200")
      .save()

//    df.write.parquet(s"data/result/$EsIndex/$EsType")
  }
}
