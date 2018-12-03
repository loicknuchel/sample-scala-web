package com.sample.web.testingutils

import java.time.Instant

import com.sample.web.controllers.api.HealthCtrl.HealthResponse
import org.scalacheck.ScalacheckShapeless._
import org.scalacheck.{Arbitrary, Gen}

object Generators {
  private val _ = coproductCogen // to keep the `org.scalacheck.ScalacheckShapeless._` import
  implicit val aInstant = Arbitrary(Gen.calendar.map(c => Instant.ofEpochMilli(c.getTimeInMillis)))

  implicit val aHealthResponse = implicitly[Arbitrary[HealthResponse]]
}
