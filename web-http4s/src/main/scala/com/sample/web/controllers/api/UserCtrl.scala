package com.sample.web.controllers.api

import cats.effect.Effect
import com.sample.core.storage.SampleDb
import com.sample.infra.Encoders._
import com.sample.lib.scalautils.Extensions._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

import scala.language.higherKinds

// TODO: test controllers
class UserCtrl[F[_] : Effect](db: SampleDb[F]) extends Http4sDsl[F] {
  val service: HttpService[F] = HttpService[F] {
    case GET -> Root => db.findUsers().flatMap(users => Ok(users.asJson))
  }
}
