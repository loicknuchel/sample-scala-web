package com.sample.core.domain

import java.time.Instant
import java.util.UUID

import cats.data.ValidatedNec
import cats.implicits._

import scala.util.Try

case class BasicUser(id: User.Id, firstName: String, lastName: String, email: String, created: Instant)

case class User(id: User.Id,
                gender: Gender,
                firstName: FirstName,
                lastName: LastName,
                email: Email,
                emailVerified: Option[Instant],
                bio: String,
                avatar: ImageUrl,
                company: CompanyName,
                twitter: TwitterUrl,
                linkedin: LinkedInUrl,
                created: Instant,
                updated: Instant)

object User {

  class Id private(val value: String) extends AnyVal {
    override def toString: String = value
  }

  object Id {
    def generate(): Id =
      new Id(UUID.randomUUID().toString)

    def from(in: String): ValidatedNec[ValidationError, Id] =
      Try(UUID.fromString(in)).fold(
        e => StringNotParseable("User.Id", in, e.getMessage).invalidNec,
        uuid => new Id(uuid.toString).valid
      )
  }

}
