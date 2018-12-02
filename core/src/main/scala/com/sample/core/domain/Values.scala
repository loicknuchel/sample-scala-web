package com.sample.core.domain

import cats.data._
import cats.implicits._

import scala.util.matching.Regex

class NonEmptyString private(val value: String) extends AnyVal {
  override def toString: String = value
}

object NonEmptyString {
  def from(in: String): ValidatedNec[ValidationError, NonEmptyString] =
    StringNonEmpty.validator.validate(in)("NonEmptyString").map(_ => new NonEmptyString(in))
}


sealed trait Gender extends Product with Serializable

object Gender {

  case object Male extends Gender

  case object Female extends Gender

  case object Other extends Gender

}


class FirstName private(val value: String) extends AnyVal {
  override def toString: String = value
}

object FirstName {
  val maxLength = 30

  def from(in: String): ValidatedNec[ValidationError, FirstName] =
    Seq(
      StringNonEmpty.validator,
      StringTooLong.validator(maxLength)
    ).map(_.validate(in)("FirstName")).foldLeft(in.validNec[ValidationError])(_ |+| _).map(_ => new FirstName(in))
}


class LastName private(val value: String) extends AnyVal {
  override def toString: String = value
}

object LastName {
  val maxLength = 30

  def from(in: String): ValidatedNec[ValidationError, LastName] =
    Seq(
      StringNonEmpty.validator,
      StringTooLong.validator(maxLength)
    ).map(_.validate(in)("LastName")).foldLeft(in.validNec[ValidationError])(_ |+| _).map(_ => new LastName(in))
}


class CompanyName private(val value: String) extends AnyVal {
  override def toString: String = value
}

object CompanyName {
  val maxLength = 30

  def from(in: String): ValidatedNec[ValidationError, CompanyName] =
    Seq(
      StringNonEmpty.validator,
      StringTooLong.validator(maxLength)
    ).map(_.validate(in)("CompanyName")).foldLeft(in.validNec[ValidationError])(_ |+| _).map(_ => new CompanyName(in))
}


class Email private(val value: String) extends AnyVal {
  override def toString: String = value
}

object Email {
  val maxLength = 100
  val pattern: Regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])".r

  def from(in: String): ValidatedNec[ValidationError, Email] =
    Seq(
      StringNonEmpty.validator,
      StringTooLong.validator(maxLength),
      StringInvalidFormat.validator(pattern, Some("email"))
    ).map(_.validate(in)("Email")).foldLeft(in.validNec[ValidationError])(_ |+| _).map(_ => new Email(in))
}


class Url private(val value: String) extends AnyVal {
  override def toString: String = value
}

object Url {
  val maxLength = 1000
  val pattern: Regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]".r

  def from(in: String): ValidatedNec[ValidationError, Url] =
    Seq(
      StringNonEmpty.validator,
      StringTooLong.validator(maxLength),
      StringInvalidFormat.validator(pattern, Some("url"))
    ).map(_.validate(in)("Url")).foldLeft(in.validNec[ValidationError])(_ |+| _).map(_ => new Url(in))
}


class ImageUrl private(val value: String) extends AnyVal {
  override def toString: String = value
}

object ImageUrl {
  val maxLength = 1000
  val pattern: Regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]".r

  def from(in: String): ValidatedNec[ValidationError, ImageUrl] =
    Seq(
      StringNonEmpty.validator,
      StringTooLong.validator(maxLength),
      StringInvalidFormat.validator(pattern, Some("url"))
    ).map(_.validate(in)("ImageUrl")).foldLeft(in.validNec[ValidationError])(_ |+| _).map(_ => new ImageUrl(in))
}


class TwitterUrl private(val value: String) extends AnyVal {
  override def toString: String = value
}

object TwitterUrl {
  val maxLength = 1000
  val pattern: Regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]".r

  def from(in: String): ValidatedNec[ValidationError, TwitterUrl] =
    Seq(
      StringNonEmpty.validator,
      StringTooLong.validator(maxLength),
      StringInvalidFormat.validator(pattern, Some("url"))
    ).map(_.validate(in)("TwitterUrl")).foldLeft(in.validNec[ValidationError])(_ |+| _).map(_ => new TwitterUrl(in))
}


class LinkedInUrl private(val value: String) extends AnyVal {
  override def toString: String = value
}

object LinkedInUrl {
  val maxLength = 1000
  val pattern: Regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]".r

  def from(in: String): ValidatedNec[ValidationError, LinkedInUrl] =
    Seq(
      StringNonEmpty.validator,
      StringTooLong.validator(maxLength),
      StringInvalidFormat.validator(pattern, Some("url"))
    ).map(_.validate(in)("LinkedInUrl")).foldLeft(in.validNec[ValidationError])(_ |+| _).map(_ => new LinkedInUrl(in))
}
