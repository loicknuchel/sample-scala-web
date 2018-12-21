package com.sample.web

import java.time.Instant

import cats.effect.{Effect, IO}
import com.sample.infra.storage.{DbConf, H2, SampleDbSql}
import com.sample.web.controllers.{api, ui}
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
    // TODO get conf from application.conf
    val version = "app version" // FIXME should get version from build
    //val dbConf: DbConf = PostgreSQL("jdbc:postgresql:world", "l.knuchel", "l.knuchel")
    val dbConf: DbConf = H2("org.postgresql.Driver", "jdbc:h2:mem:sample_db;MODE=PostgreSQL;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1")
    val db = new SampleDbSql(dbConf)
    db.createTables().unsafeRunSync()
    val app = buildApp(8888)(
      Routes.home -> new ui.HomeCtrl[IO].service,
      Routes.users -> new ui.UserCtrl[IO](db).service,
      "/api/users" -> new api.UserCtrl[IO](db).service,
      "/api/status" -> new api.HealthCtrl[IO](version, started).service,
      Routes.assets -> new ui.AssetCtrl[IO].service)
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

object Routes {
  def home: String = "/"

  def users: String = "/users"

  def assets: String = "/assets"

  def asset(path: String): String = s"$assets/$path"
}
