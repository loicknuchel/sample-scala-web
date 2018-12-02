package com.sample.core.domain

import cats.data.NonEmptyChain
import cats.data.Validated.Valid
import cats.implicits._
import com.sample.lib.scalautils.Extensions._
import org.scalatest.{FunSpec, Matchers}

class ValuesSpec extends FunSpec with Matchers {
  describe("FirstName") {
    import FirstName._
    val ref = "FirstName"
    val good = "toto"
    it("should not be empty") {
      val value = ""
      FirstName.from(value) shouldBe StringNonEmpty(ref, value).invalidNec
    }
    it("should not be too long") {
      val value = ("a" * maxLength) + good
      FirstName.from(value) shouldBe StringTooLong(ref, value, maxLength).invalidNec
    }
    it("should be valid") {
      val res = FirstName.from(good)
      res shouldBe a[Valid[_]]
      res.get.value shouldBe good
    }
  }

  describe("LastName") {
    import LastName._
    val ref = "LastName"
    val good = "toto"
    it("should not be empty") {
      val value = ""
      LastName.from(value) shouldBe StringNonEmpty(ref, value).invalidNec
    }
    it("should not be too long") {
      val value = ("a" * maxLength) + good
      LastName.from(value) shouldBe StringTooLong(ref, value, maxLength).invalidNec
    }
    it("should be valid") {
      val res = LastName.from(good)
      res shouldBe a[Valid[_]]
      res.get.value shouldBe good
    }
  }

  describe("Email") {
    import Email._
    val ref = "Email"
    val good = "toto@mail.com"
    it("should not be empty") {
      val value = ""
      Email.from(value) shouldBe NonEmptyChain(
        StringNonEmpty(ref, value),
        StringInvalidFormat(ref, value, "email")).invalid
    }
    it("should not be too long") {
      val value = ("a" * maxLength) + good
      Email.from(value) shouldBe StringTooLong(ref, value, maxLength).invalidNec
    }
    it("should contain a @") {
      val value = "totomail.com"
      Email.from(value) shouldBe StringInvalidFormat(ref, value, "email").invalidNec
    }
    it("should be valid") {
      val res = Email.from(good)
      res shouldBe a[Valid[_]]
      res.get.value shouldBe good
    }
  }

  describe("Url") {
    import Url._
    val ref = "Url"
    val good = "https://typelevel.org/cats"
    it("should not be empty") {
      val value = ""
      Url.from(value) shouldBe NonEmptyChain(
        StringNonEmpty(ref, value),
        StringInvalidFormat(ref, value, "url")).invalid
    }
    it("should not be too long") {
      val value = good + ("a" * maxLength)
      Url.from(value) shouldBe StringTooLong(ref, value, maxLength).invalidNec
    }
    it("should contain a @") {
      val value = "totomail.com"
      Url.from(value) shouldBe StringInvalidFormat(ref, value, "url").invalidNec
    }
    it("should be valid") {
      val res = Url.from(good)
      res shouldBe a[Valid[_]]
      res.get.value shouldBe good
    }
  }
}
