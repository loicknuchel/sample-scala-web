package com.sample.web

import org.scalatest.{FunSpec, Matchers}

class MyWebSpec extends FunSpec with Matchers {
  describe("MyWeb") {
    it("should return web") {
      MyWeb.value shouldBe "web"
    }
  }
}
