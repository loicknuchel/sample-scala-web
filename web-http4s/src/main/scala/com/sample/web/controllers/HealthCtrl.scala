package com.sample.web.controllers

import java.time.Instant

import cats.effect.Effect
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl

import scala.language.higherKinds

class HealthCtrl[F[_] : Effect](version: String, started: Instant) extends Http4sDsl[F] {

  import com.sample.web.controllers.HealthCtrl._

  val service: HttpService[F] = {
    import io.circe.syntax._
    import org.http4s.circe._
    HttpService[F] {
      case GET -> Root => Ok(HealthResponse("ok", version, started).asJson)
    }
  }
}

object HealthCtrl {

  case class HealthResponse(status: String, version: String, started: Instant)

  object HealthResponse {

    import io.circe.java8.time._

    implicit val encoder: Encoder[HealthResponse] = deriveEncoder
    implicit val decoder: Decoder[HealthResponse] = deriveDecoder
  }

}
