package com.solvemprobler.etl

import org.apache.spark.sql.functions.{sum, window, to_date, collect_set, avg, size, date_format}
import org.apache.spark.sql.{DataFrame, SparkSession}
import com.solvemprobler.utils.{EtlDefinition, WriteUtils}

case class ProductWeeklySalesETL(spark: SparkSession) extends EtlJob {
  import spark.implicits._

  def transform()(df: DataFrame): DataFrame = {
    df
      .withColumn("date", date_format(to_date($"invoiceDate", "dd/MM/yyyy hh:mm"), "yyyy-MM-dd 12:00:00"))
      .groupBy($"description", $"stockCode", window($"date", "1 week", "1 week", "6 days"))
      .agg(
        sum($"quantity" * $"unitPrice").as("sales"),
        avg($"unitPrice").as("avgUnitPrice"),
        sum($"quantity").as("volume"),
        size(collect_set($"customerID")).as("custCount"))
      .withColumn("weekEnding", to_date($"window.end", "dd/MM/yyyy"))
      .select($"stockCode", $"description",$"weekEnding", $"sales", $"avgUnitPrice", $"volume", $"custCount")
  }

  def write()(df: DataFrame): Unit = {
    WriteUtils.writeToEs(df, "weeklysales", "weeklysales")
  }
}
