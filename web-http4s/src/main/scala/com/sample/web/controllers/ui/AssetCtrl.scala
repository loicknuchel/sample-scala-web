package com.sample.web.controllers.ui

import cats.effect.Effect
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl
import org.http4s.server.staticcontent.WebjarService.{Config, WebjarAsset}
import org.http4s.server.staticcontent.webjarService

import scala.language.higherKinds

class AssetCtrl[F[_] : Effect] extends Http4sDsl[F] {
  val service: HttpService[F] = webjarService(
    Config(filter = (asset: WebjarAsset) => asset.asset.endsWith(".js") || asset.asset.endsWith(".css"))
  )
}
