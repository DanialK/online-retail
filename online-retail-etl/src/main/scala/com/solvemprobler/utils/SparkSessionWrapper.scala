package com.solvemprobler.utils

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

trait SparkSessionWrapper {
  Logger.getLogger("org").setLevel(Level.FATAL)
  lazy val spark: SparkSession = {
    SparkSession
      .builder()
      .appName("SparkDev")
      .master("local[*]")
      .config("es.index.auto.create", "true")
      .getOrCreate()
  }
}