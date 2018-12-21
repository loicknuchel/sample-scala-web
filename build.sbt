ThisBuild / version := "0.1-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.7"
ThisBuild / organization := "org.sample"

/**
  * Global settings
  */
// https://tpolecat.github.io/2017/04/25/scalac-flags.html
// https://github.com/MateuszKubuszok/SBTScalaMultiproject2.g8/blob/master/src/main/g8/project/Settings.scala
// https://github.com/pbassiner/sbt-multi-project-example/blob/master/build.sbt
val commonSettings = Seq(
  scalacOptions ++= Seq( // TODO find good options
    "-deprecation",
    "-Ypartial-unification"),
  addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.0-M4")
)

/**
  * Dependency management
  */
val http4s = Seq(
  "org.http4s" %% "http4s-dsl",
  "org.http4s" %% "http4s-blaze-server",
  "org.http4s" %% "http4s-circe").map(_ % "0.18.21")
val doobieVersion = "0.5.3" // 0.6.0 depends on fs2 1.0.0 which is incompatible with http4s 0.18.21
val doobie = Seq(
  "org.tpolecat" %% "doobie-core",
  "org.tpolecat" %% "doobie-h2",
  "org.tpolecat" %% "doobie-postgres").map(_ % doobieVersion)
val doobieTest = Seq(
  "org.tpolecat" %% "doobie-scalatest").map(_ % doobieVersion % Test)
val flyway = Seq(
  "org.flywaydb" % "flyway-core" % "5.1.4")
val circe = Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser",
  "io.circe" %% "circe-literal",
  "io.circe" %% "circe-java8").map(_ % "0.10.1")
val cats = Seq(
  "org.typelevel" %% "cats-core" % "1.4.0",
  "org.typelevel" %% "cats-effect" % "0.10.1")
val logback = Seq(
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "ch.qos.logback" % "logback-classic" % "1.2.3")
val webjars = Seq(
  "org.webjars.npm" % "jquery" % "3.3.1",
  "org.webjars.npm" % "bootstrap" % "4.1.3")
val scalaTest = Seq(
  "org.scalatest" %% "scalatest" % "3.0.5" % Test)
val scalaCheck = Seq(
  "org.scalacheck" %% "scalacheck" % "1.14.0" % Test,
  "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.0" % Test,
  "com.danielasfregola" %% "random-data-generator" % "2.6" % Test)

val scalautilsDependencies = cats ++ scalaTest
val coreDependencies = cats ++ scalaTest
val infraDependencies = doobie ++ circe ++ flyway ++ scalaTest ++ scalaCheck ++ doobieTest
val webHttp4sDependencies = http4s ++ webjars ++ logback ++ scalaTest ++ scalaCheck
val webPlay2Dependencies = scalaTest

/**
  * Project definition
  */
val scalautils = (project in file("libs/scalautils"))
  .disablePlugins(RevolverPlugin)
  .settings(
    name := "scalautils",
    libraryDependencies ++= scalautilsDependencies,
    commonSettings
  )

val core = (project in file("core"))
  .dependsOn(scalautils)
  .disablePlugins(RevolverPlugin)
  .settings(
    name := "core",
    libraryDependencies ++= coreDependencies,
    commonSettings
  )

val infra = (project in file("infra"))
  .dependsOn(core)
  .disablePlugins(RevolverPlugin)
  .settings(
    name := "infra",
    libraryDependencies ++= infraDependencies,
    commonSettings
  )

val webHttp4s = (project in file("web-http4s"))
  .dependsOn(core, infra)
  .enablePlugins(SbtTwirl, JavaAppPackaging)
  .settings(
    name := "web-http4s",
    libraryDependencies ++= webHttp4sDependencies,
    commonSettings
  )

val webPlay2 = (project in file("web-play2"))
  .dependsOn(core, infra)
  .enablePlugins(JavaAppPackaging)
  .disablePlugins(RevolverPlugin)
  .settings(
    name := "web-play2",
    libraryDependencies ++= webPlay2Dependencies,
    commonSettings
  )

val global = (project in file("."))
  .aggregate(scalautils, core, infra, webHttp4s, webPlay2) // send commands to every module
  .disablePlugins(RevolverPlugin)
  .settings(
    name := "sample-scala-web"
  )
