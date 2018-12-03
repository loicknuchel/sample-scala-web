package com.sample.web.infra

import com.sample.web.controllers.api.HealthCtrl.HealthResponse
import com.sample.web.testingutils.Generators._
import io.circe.parser.parse
import io.circe.syntax._
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FunSpec, Matchers}

class EncodersSpec extends FunSpec with Matchers with PropertyChecks {
  describe("Encoders") {
    import Encoders._
    it("should encode and decode HealthResponse") {
      forAll(MinSuccessful(500)) { in: HealthResponse =>
        val json = in.asJson
        val formatted = json.toString()
        val parsed = parse(formatted)
        val result = parsed.flatMap(_.as[HealthResponse])
        parsed shouldBe Right(json)
        result shouldBe Right(in)
      }
    }
  }
}
