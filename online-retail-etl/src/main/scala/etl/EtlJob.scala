package etl

import org.apache.spark.sql.DataFrame
import utils.{EtlDefinition, WriteUtils}

trait EtlJob {
  def getEtlDefinition(df: DataFrame): EtlDefinition = {
    EtlDefinition(
      sourceDF = df,
      transform = transform(),
      write = write()
    )
  }

  def transform()(df: DataFrame): DataFrame

  def write()(df: DataFrame): Unit
}
