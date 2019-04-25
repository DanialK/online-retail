package com.solvemprobler.etl

import org.apache.spark.sql.{DataFrame, SparkSession}
import com.solvemprobler.utils.{EtlDefinition, WriteUtils}

case class CountryETL(spark: SparkSession) extends EtlJob {
  import spark.implicits._

  def transform()(df: DataFrame): DataFrame = {
    df.select($"country").distinct
  }

  def write()(df: DataFrame): Unit = {
    WriteUtils.writeToEs(df, "country", "country")
  }
}
