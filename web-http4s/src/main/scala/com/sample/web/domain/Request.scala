package com.sample.web.domain

import com.sample.core.domain.BasicUser
import org.http4s.Request

import scala.language.higherKinds

sealed trait DomainRequest {
  val http: Request[Any]

  def pathInfo: String = http.pathInfo
}

case class NoAuthRequest(http: Request[Any]) extends DomainRequest

case class AuthAwareRequest(http: Request[Any], user: Option[BasicUser]) extends DomainRequest

case class AuthedRequest(http: Request[Any], user: BasicUser) extends DomainRequest
