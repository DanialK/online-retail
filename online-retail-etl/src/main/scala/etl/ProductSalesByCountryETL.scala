package etl

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.sum
import utils.{EtlDefinition, WriteUtils}

case class ProductSalesByCountryETL(spark: SparkSession) extends EtlJob {
  import spark.implicits._

  def transform()(df: DataFrame): DataFrame = {
    df
      .groupBy($"country", $"stockCode")
      .agg(sum($"quantity" * $"unitPrice").as("sales"))
      .select($"country", $"stockCode", $"sales")
  }

  def write()(df: DataFrame): Unit = {
    WriteUtils.writeToEs(df, "productsbycountry", "productsbycountry")
  }
}
