package com.sample.infra

import org.scalatest.{FunSpec, Matchers}

class MyInfraSpec extends FunSpec with Matchers {
  describe("MyInfra") {
    it("should return infra") {
      MyInfra.value shouldBe "infra"
    }
  }
}
