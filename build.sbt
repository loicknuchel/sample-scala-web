ThisBuild / version := "0.1"
ThisBuild / scalaVersion := "2.12.7"
ThisBuild / organization := "org.sample"

// global settings definitions
val cats = "org.typelevel" %% "cats-core" % "1.5.0-RC1"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"

val scalacArgs = Seq("-Ypartial-unification")

val scalautils = (project in file("libs/scalautils"))
  .settings(
    name := "scalautils",
    libraryDependencies ++= Seq(cats, scalaTest % Test),
    scalacOptions ++= scalacArgs
  )

val core = (project in file("core"))
  .dependsOn(scalautils)
  .settings(
    name := "core",
    libraryDependencies ++= Seq(cats, scalaTest % Test),
    scalacOptions ++= scalacArgs
  )

val infra = (project in file("infra"))
  .dependsOn(core)
  .settings(
    name := "infra",
    libraryDependencies ++= Seq(cats, scalaTest % Test),
    scalacOptions ++= scalacArgs
  )

val webHttp4s = (project in file("web-http4s"))
  .dependsOn(core)
  .dependsOn(infra)
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "web-http4s",
    libraryDependencies ++= Seq(scalaTest % Test),
    scalacOptions ++= scalacArgs
  )

val webPlay2 = (project in file("web-play2"))
  .dependsOn(core)
  .dependsOn(infra)
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "web-play2",
    libraryDependencies ++= Seq(scalaTest % Test),
    scalacOptions ++= scalacArgs
  )
