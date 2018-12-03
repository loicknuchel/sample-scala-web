package com.sample.infra

import cats.data.ValidatedNec
import com.sample.core.domain.{BasicUser, DomainError, User}
import com.sample.lib.scalautils.Extensions._
import io.circe._
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.java8.time._

object Encoders {
  private val _ = decodeInstant // to keep the `io.circe.java8.time._` import

  implicit val encUserId: Encoder[User.Id] = Encoder.encodeString.contramap[User.Id](_.value)
  implicit val decUserId: Decoder[User.Id] = Decoder.decodeString.emap(format(User.Id.from))
  implicit val encBasicUser: Encoder[BasicUser] = deriveEncoder
  implicit val decBasicUser: Decoder[BasicUser] = deriveDecoder

  private def format[A](validate: String => ValidatedNec[DomainError, A])(str: String): Either[String, A] =
    validate(str).leftMap(_.map(_.format).mkString(", ")).toEither
}
