package com.sample.lib.scalautils

import cats.data.Validated.{Invalid, Valid}
import cats.data.{NonEmptyChain, Validated}
import cats.effect.Effect

import scala.language.higherKinds

object Extensions {

  implicit class EffectExtension[A, F[_] : Effect](val in: F[A]) {
    def map[B](f: A => B): F[B] =
      implicitly[Effect[F]].map(in)(f)

    def flatMap[B](f: A => F[B]): F[B] =
      implicitly[Effect[F]].flatMap(in)(f)
  }

  implicit class ValidatedExtension[E, A](val in: Validated[E, A]) extends AnyVal {
    def get: A = in match {
      case Valid(a) => a
      case Invalid(e) => throw new NoSuchElementException(s"Invalid.get: $e")
    }
  }

  implicit class NonEmptyChainExtension[A](val in: NonEmptyChain[A]) extends AnyVal {
    def map[B](f: A => B): NonEmptyChain[B] =
      in.flatMap(a => NonEmptyChain.one(f(a)))

    def mkString(sep: String): String =
      in.toNonEmptyList.toList.mkString(sep)
  }

}
