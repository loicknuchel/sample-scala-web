ThisBuild / version := "0.1-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.7"
ThisBuild / organization := "org.sample"

// global settings definitions
val cats = "org.typelevel" %% "cats-core" % "1.5.0-RC1"
val circe = Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser",
  "io.circe" %% "circe-literal",
  "io.circe" %% "circe-java8").map(_ % "0.10.1")
val http4s = Seq(
  "org.http4s" %% "http4s-dsl",
  "org.http4s" %% "http4s-blaze-server",
  "org.http4s" %% "http4s-circe").map(_ % "0.18.21")
val slf4j = "org.slf4j" % "slf4j-api" % "1.7.25"
val logback = "ch.qos.logback" % "logback-classic" % "1.2.3"
val webjars = Seq(
  "org.webjars.npm" % "jquery" % "3.3.1",
  "org.webjars.npm" % "bootstrap" % "4.1.3")
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"

// https://tpolecat.github.io/2017/04/25/scalac-flags.html
// https://github.com/MateuszKubuszok/SBTScalaMultiproject2.g8/blob/master/src/main/g8/project/Settings.scala
// https://github.com/pbassiner/sbt-multi-project-example/blob/master/build.sbt
val commonSettings = Seq(
  scalacOptions ++= Seq( // TODO find good options
    "-deprecation",
    "-Ypartial-unification")
)

val scalautils = (project in file("libs/scalautils"))
  .disablePlugins(RevolverPlugin)
  .settings(
    name := "scalautils",
    libraryDependencies ++= Seq(cats, scalaTest % Test),
    commonSettings
  )

val core = (project in file("core"))
  .dependsOn(scalautils)
  .disablePlugins(RevolverPlugin)
  .settings(
    name := "core",
    libraryDependencies ++= Seq(cats, scalaTest % Test),
    commonSettings
  )

val infra = (project in file("infra"))
  .dependsOn(core)
  .disablePlugins(RevolverPlugin)
  .settings(
    name := "infra",
    libraryDependencies ++= Seq(cats, scalaTest % Test),
    commonSettings
  )

val webHttp4s = (project in file("web-http4s"))
  .dependsOn(core, infra)
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "web-http4s",
    libraryDependencies ++= http4s ++ circe ++ webjars ++ Seq(slf4j, logback, scalaTest % Test),
    commonSettings
  )

val webPlay2 = (project in file("web-play2"))
  .dependsOn(core, infra)
  .enablePlugins(JavaAppPackaging)
  .disablePlugins(RevolverPlugin)
  .settings(
    name := "web-play2",
    libraryDependencies ++= Seq(scalaTest % Test),
    commonSettings
  )

val global = (project in file("."))
  .aggregate(scalautils, core, infra, webHttp4s, webPlay2) // send commands to every module
  .disablePlugins(RevolverPlugin)
  .settings(
    name := "sample-scala-web"
  )
