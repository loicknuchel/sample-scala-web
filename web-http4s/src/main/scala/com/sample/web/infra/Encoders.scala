package com.sample.web.infra

import com.sample.web.controllers.api.HealthCtrl.HealthResponse
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.java8.time._
import io.circe.{Decoder, Encoder}

object Encoders {
  private val _ = decodeInstant // to keep the `io.circe.java8.time._` import

  implicit val encoder: Encoder[HealthResponse] = deriveEncoder
  implicit val decoder: Decoder[HealthResponse] = deriveDecoder
}
