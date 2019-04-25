package com.solvemprobler.utils

import com.solvemprobler.OnlineRetailConfig
import com.typesafe.config.ConfigFactory
import pureconfig.generic.ProductHint
import pureconfig.{CamelCase, ConfigFieldMapping, loadConfig}
import pureconfig.generic.auto._

trait WithConfig {
  implicit def productHint[T] = ProductHint[T](ConfigFieldMapping(CamelCase, CamelCase))

  val config = loadConfig[OnlineRetailConfig](ConfigFactory.load(), "onlineRetailConfig") match {
    case Right(config) => config
    case Left(x) => throw new Exception("Unable to load configuration.")
  }
}