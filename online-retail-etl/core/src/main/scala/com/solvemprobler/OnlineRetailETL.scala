package com.solvemprobler

import com.solvemprobler.etl.{CountryETL, ProductSalesByCountryETL, ProductWeeklySalesETL, ProductsETL}
import com.solvemprobler.utils.{SparkSessionWrapper, WithConfig}
import org.apache.spark.sql.types._
import pureconfig.generic.auto._

object OnlineRetailETL extends SparkSessionWrapper with WithConfig {
  val schema: StructType = new StructType()
    .add(StructField("invoiceNo", StringType, false))
    .add(StructField("stockCode", StringType, false))
    .add(StructField("description", StringType, true))
    .add(StructField("quantity", IntegerType, true))
    .add(StructField("invoiceDate", StringType, true))
    .add(StructField("unitPrice", FloatType, true))
    .add(StructField("customerID", IntegerType, true))
    .add(StructField("country", StringType, true))

  def main(args: Array[String]): Unit = {
    import spark.implicits._

    val df = spark.read
      .format("com.databricks.spark.csv")
      .option("header", "true") //reading the headers
      .option("mode", "DROPMALFORMED")
      .schema(schema)
      .load(config.onlineRetailData)
      .filter($"description".isNotNull && $"stockCode".isNotNull)

    val jobs = List(
      ProductsETL(spark),
      CountryETL(spark),
      ProductSalesByCountryETL(spark),
      ProductWeeklySalesETL(spark)
    )

    jobs.foreach(job => {
      println("Starting", job.getClass.getName)
      job.getEtlDefinition(df).process()
    })

    spark.stop()
  }
}
