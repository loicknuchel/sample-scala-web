package com.sample.web.controllers.utils

import cats.effect.Effect
import org.http4s.dsl.Http4sDsl
import org.http4s.{Header, Response, Status}
import play.twirl.api.HtmlFormat

import scala.language.higherKinds

class UiCtrl[F[_] : Effect] extends Http4sDsl[F] {

  object response {
    //def Ok(html: HtmlFormat.Appendable): F[Response[F]] = Status.Ok(html.toString(), Header("Content-type", "text/html"))
  }

}
