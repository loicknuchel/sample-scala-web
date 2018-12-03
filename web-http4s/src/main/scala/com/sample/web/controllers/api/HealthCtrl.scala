package com.sample.web.controllers.api

import java.time.Instant

import cats.effect.Effect
import com.sample.web.infra.Encoders._
import io.circe.syntax._
import org.http4s.HttpService
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

import scala.language.higherKinds

class HealthCtrl[F[_] : Effect](version: String, started: Instant) extends Http4sDsl[F] {
  val service: HttpService[F] = HttpService[F] {
    case GET -> Root => Ok(HealthCtrl.HealthResponse("ok", version, started).asJson)
  }
}

object HealthCtrl {

  case class HealthResponse(status: String, version: String, started: Instant)

}
