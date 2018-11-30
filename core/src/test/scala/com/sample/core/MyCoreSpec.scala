package com.sample.core

import org.scalatest.{FunSpec, Matchers}

class MyCoreSpec extends FunSpec with Matchers {
  describe("MyCore") {
    it("should return core") {
      MyCore.value shouldBe "core"
    }
  }
}
