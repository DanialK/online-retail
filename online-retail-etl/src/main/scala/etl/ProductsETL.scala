package etl

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import utils.{EtlDefinition, WriteUtils}

case class ProductsETL(spark: SparkSession) extends EtlJob {
  import spark.implicits._

  def transform()(df: DataFrame): DataFrame = {
    df.select($"stockCode", $"description").distinct
  }

  def write()(df: DataFrame): Unit = {
    WriteUtils.writeToEs(df, "products", "products")
  }
}
