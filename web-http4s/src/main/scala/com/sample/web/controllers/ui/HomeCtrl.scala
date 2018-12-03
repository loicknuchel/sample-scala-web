package com.sample.web.controllers.ui

import cats.effect.Effect
import org.http4s.dsl.Http4sDsl
import org.http4s.{Header, HttpService}

import scala.language.higherKinds

class HomeCtrl[F[_] : Effect] extends Http4sDsl[F] {
  val service: HttpService[F] = HttpService[F] {
    case GET -> Root => Ok(
      """<!doctype html>
        |<html lang="en">
        |  <head>
        |    <meta charset="utf-8">
        |    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        |
        |    <link rel="stylesheet" href="/assets/bootstrap/4.1.3/dist/css/bootstrap.min.css">
        |
        |    <title>Hello, world!</title>
        |  </head>
        |  <body>
        |    <h1>Hello, world!</h1>
        |
        |    <script src="/assets/jquery/3.3.1/dist/jquery.slim.min.js"></script>
        |    <script src="/assets/bootstrap/4.1.3/dist/js/bootstrap.bundle.min.js"></script>
        |  </body>
        |</html>
      """.stripMargin, Header("Content-type", "text/html"))
  }
}
