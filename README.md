# Sample Scala Web

This repo is a sample project of a principled scala web application.

## Structure

It is split in multiple modules to allow other project to depend only on some part and clarify dependencies (with libraries and between modules)
Modules :
- libs/scalautils: basic utils that can be useful everywhere
- core: core business logic, should depend on very few lib
- infra: connectors for the domain logic with other systems (db, api...)
- web: expose the service through HTTP (UI and API)

## Architecture principles

- hexagonal architecture

## Tech stack

- language: scala
- utils: cats, shapeless, refined
- config: hocon, pureconfig
- json serialization: circe
- database: postgres, h2, doobie, flyway
- web: play 2 / http4s, twirl
- UI: bootstrap
- API: REST / graphql, sangria
- tests: scalatest, scalacheck
- metrics: prometheus
- build: sbt / mill, coursier, sbt-buildinfo, sbt-updates, splain, sbt-dependency-graph, sbt-revolver
- code quality: scoverage, scalafmt, scalastyle, wartremover
- service: github, codacy
- doc: tut, swagger
