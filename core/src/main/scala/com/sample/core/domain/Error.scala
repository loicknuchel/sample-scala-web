package com.sample.core.domain

import cats.data.ValidatedNec
import cats.implicits._

import scala.util.matching.Regex

sealed trait Error {
  def format: String
}

sealed trait ValidationError extends Error {
  val ref: String
  val original: String
}

trait Validator[A] {
  def isValid(in: A): Boolean

  def isInvalid(in: A): Boolean = !isValid(in)

  def build(ref: String, original: A): ValidationError

  def validate(in: A)(ref: String): ValidatedNec[ValidationError, A] =
    if (isValid(in)) in.valid else build(ref, in).invalidNec
}

case class StringNotParseable(ref: String, original: String, err: String) extends ValidationError {
  override def format: String = s"Unable to parse $ref: $err"
}

case class StringNonEmpty(ref: String, original: String) extends ValidationError {
  override def format: String = s"$ref should not be empty"
}

object StringNonEmpty {
  def validator: Validator[String] = new Validator[String] {
    override def isValid(in: String): Boolean = in.nonEmpty

    override def build(ref: String, original: String): ValidationError = StringNonEmpty(ref, original)
  }
}

case class StringTooLong(ref: String, original: String, max: Long) extends ValidationError {
  override def format: String = s"$ref should be less than $max chars"
}

object StringTooLong {
  def validator(max: Long): Validator[String] = new Validator[String] {
    override def isValid(in: String): Boolean = in.length < max

    override def build(ref: String, original: String): ValidationError = StringTooLong(ref, original, max)
  }
}

case class StringInvalidFormat(ref: String, original: String, pattern: String) extends ValidationError {
  override def format: String = s"$ref require to match the $pattern format"
}

object StringInvalidFormat {
  def validator(regex: Regex, format: Option[String] = None): Validator[String] = new Validator[String] {
    override def isValid(in: String): Boolean = regex.pattern.matcher(in).find()

    override def build(ref: String, original: String): ValidationError = StringInvalidFormat(ref, original, format.getOrElse(regex.regex))
  }
}
