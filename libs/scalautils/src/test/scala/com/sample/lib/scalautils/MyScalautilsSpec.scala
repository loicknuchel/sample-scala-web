package com.sample.lib.scalautils

import org.scalatest.{FunSpec, Matchers}

class MyScalautilsSpec extends FunSpec with Matchers {
  describe("MyScalautils") {
    it("should return scalautils") {
      MyScalautils.value shouldBe "scalautils"
    }
  }
}
