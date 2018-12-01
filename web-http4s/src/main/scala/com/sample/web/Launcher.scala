package com.sample.web

import java.time.Instant

import cats.effect.{Effect, IO}
import com.sample.web.controllers.{AssetCtrl, HealthCtrl, HomeCtrl}
import fs2.StreamApp
import org.http4s.HttpService
import org.http4s.server.blaze.BlazeBuilder
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext
import scala.language.higherKinds

object Launcher {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    import scala.concurrent.ExecutionContext.Implicits.global
    logger.info("Launching webserver (http4s)")
    val started = Instant.now()
    val version = "app version" // FIXME should get version
    val app = buildApp(8888)(
      "/" -> new HomeCtrl[IO].service,
      "/api/status" -> new HealthCtrl[IO](version, started).service,
      "/assets" -> new AssetCtrl[IO].service)
    app.main(args)
  }

  private def buildApp[F[_] : Effect](port: Int)(services: (String, HttpService[F])*)(implicit ex: ExecutionContext): StreamApp[F] =
    new StreamApp[F]() {
      override def stream(args: List[String], requestShutdown: F[Unit]): fs2.Stream[F, StreamApp.ExitCode] = {
        services
          .foldLeft(BlazeBuilder[F]) { case (builder, (prefix, srv)) => builder.mountService(srv, prefix) }
          .bindHttp(port, "0.0.0.0")
          .serve
      }
    }
}
