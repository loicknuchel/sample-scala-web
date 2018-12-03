package com.sample.infra

import com.sample.core.domain.BasicUser
import com.sample.infra.testingutils.Generators._
import io.circe.parser._
import io.circe.syntax._
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FunSpec, Matchers}

class EncodersSpec extends FunSpec with Matchers with PropertyChecks {
  describe("Encoders") {
    import Encoders._
    it("should encode and decode BasicUser") {
      forAll(MinSuccessful(500)) { in: BasicUser =>
        val json = in.asJson
        val formatted = json.toString()
        val parsed = parse(formatted)
        val result = parsed.flatMap(_.as[BasicUser])
        parsed shouldBe Right(json)
        result shouldBe Right(in)
      }
    }
  }
}
