package com.sample.core.domain

import cats.data.Validated.Valid
import cats.implicits._
import com.sample.lib.scalautils.Extensions._
import org.scalatest.{FunSpec, Matchers}

class UserSpec extends FunSpec with Matchers {
  describe("User") {
    describe("Id") {
      it("should always be valid") {
        val notOk = User.Id.from("abc")
        notOk shouldBe StringNotParseable("User.Id", "abc", "Invalid UUID string: abc").invalidNec
        val ok = User.Id.from("dd430266-fa59-4e5d-a331-945ec67b22bd")
        ok shouldBe a[Valid[_]]
        ok.get.value shouldBe "dd430266-fa59-4e5d-a331-945ec67b22bd"
      }
    }
  }
}
