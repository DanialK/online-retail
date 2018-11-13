package utils

import org.apache.spark.sql.{DataFrame, SaveMode}

object WriteUtils {
  def writeToEs(df: DataFrame, EsIndex: String, EsType: String): Unit = {
    df.write.format("org.elasticsearch.spark.sql")
      .mode(SaveMode.Append)
      .option("es.resource", s"$EsIndex/$EsType")
      .option("es.nodes", "$EsIndex/$EsType")
      .option("es.port", "9200")
      .save()
  }
}
