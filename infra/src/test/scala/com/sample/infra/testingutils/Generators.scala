package com.sample.infra.testingutils

import java.time.Instant

import cats.data.NonEmptyList
import com.sample.core.domain._
import com.sample.lib.scalautils.Extensions._
import org.scalacheck.Arbitrary._
import org.scalacheck.ScalacheckShapeless._
import org.scalacheck.{Arbitrary, Gen}

import scala.language.implicitConversions

object Generators {
  private val _ = coproductCogen // to keep the `org.scalacheck.ScalacheckShapeless._` import
  private implicit def gen[A](a: Arbitrary[A]): Gen[A] = a.arbitrary

  implicit def aNonEmptyList[A](g: Gen[A]) = Arbitrary(Gen.nonEmptyListOf(g).map(NonEmptyList.fromListUnsafe))

  implicit val aInstant = Arbitrary(Gen.calendar.map(c => Instant.ofEpochMilli(c.getTimeInMillis)))
  implicit val aNonEmptyString = Arbitrary(arbChar.flatMap(c => arbString.map(s => NonEmptyString.from(s + c).get)))
  implicit val aGender = Arbitrary(Gen.oneOf(Gender.Male, Gender.Female, Gender.Other))
  implicit val aFirstName = Arbitrary(aNonEmptyString.map(s => FirstName.from(s.value.take(FirstName.maxLength)).get))
  implicit val aLastName = Arbitrary(aNonEmptyString.map(s => LastName.from(s.value.take(LastName.maxLength)).get))
  implicit val aCompanyName = Arbitrary(aNonEmptyString.map(s => CompanyName.from(s.value.take(CompanyName.maxLength)).get))
  implicit val aUserId = Arbitrary(Gen.uuid.map(id => User.Id.from(id.toString).get))
  implicit val aBasicUser = implicitly[Arbitrary[BasicUser]]
  //implicit val aUser = implicitly[Arbitrary[User]]
}
