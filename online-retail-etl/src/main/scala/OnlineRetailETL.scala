import etl.{CountryETL, ProductSalesByCountryETL, ProductWeeklySalesETL, ProductsETL}
import org.apache.spark.sql.types._

object OnlineRetailETL extends SparkSessionWrapper {
  import spark.implicits._
  def main(args: Array[String]): Unit = {

    val schema = new StructType()
      .add(StructField("invoiceNo", StringType, false))
      .add(StructField("stockCode", StringType, false))
      .add(StructField("description", StringType, true))
      .add(StructField("quantity", IntegerType, true))
      .add(StructField("invoiceDate", StringType, true))
      .add(StructField("unitPrice", FloatType, true))
      .add(StructField("customerID", IntegerType, true))
      .add(StructField("country", StringType, true))

    val df = spark.read
      .format("com.databricks.spark.csv")
      .option("header", "true") //reading the headers
      .option("mode", "DROPMALFORMED")
      .schema(schema)
      .load("data/Online_Retail.csv")
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
