name := "online-retail-etl"
organization in ThisBuild := "com.solvemprobler"
scalaVersion in ThisBuild := "2.11.12"
version in ThisBuild := "0.1"

lazy val dependencies = new {

  val sparkVersion = "2.3.0"
  val spark = "org.apache.spark" %% "spark-core" % sparkVersion % Provided
  val sparkSql = "org.apache.spark" %% "spark-sql" % "2.3.0" % Provided

  val elastic4sVersion = "6.3.7"
  val elastic4sCore = "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion
  val elastic4sHttp = "com.sksamuel.elastic4s" %% "elastic4s-http" % elastic4sVersion

  val elasticsearchSpark = "org.elasticsearch" %% "elasticsearch-spark-20" % "6.4.2"

  val pureConfig = "com.github.pureconfig" %% "pureconfig" % "0.10.2"

  val slf4jApi = "org.slf4j" % "slf4j-api" % "1.7.26"
  val slf4jLog4j = "org.slf4j" % "slf4j-log4j12" % "1.7.26"

}

lazy val commonDependencies = Seq(
  dependencies.spark,
  dependencies.sparkSql,
  dependencies.elastic4sCore,
  dependencies.elastic4sHttp,
  dependencies.elasticsearchSpark,
  dependencies.pureConfig,
  dependencies.slf4jApi,
  dependencies.slf4jLog4j
)

lazy val assemblySettings = Seq(
  assemblyJarName in assembly := name.value + ".jar",
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case _                             => MergeStrategy.first
  }
)

lazy val settings = Seq(
  scalacOptions ++=  Seq(
    "-unchecked",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-deprecation",
    "-encoding",
    "utf8"
  ),
  resolvers ++= Seq(
    "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots"),
    Resolver.bintrayRepo("ovotech", "maven")
  )
)

lazy val global = project
  .in(file("."))
  .settings(settings)
  .aggregate(
    core
  )

lazy val core = project
  .in(file("./core"))
  .settings(
    settings,
    assemblySettings,
    libraryDependencies ++= commonDependencies
  )