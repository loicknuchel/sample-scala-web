package com.sample.lib.scalautils

import cats.data.Validated
import cats.data.Validated.{Invalid, Valid}

object Extensions {

  implicit class ValidatedExtension[E, A](val in: Validated[E, A]) extends AnyVal {
    def get: A = in match {
      case Valid(a) => a
      case Invalid(e) => throw new NoSuchElementException(s"Invalid.get: $e")
    }
  }

}
