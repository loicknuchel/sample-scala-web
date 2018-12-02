package com.sample.infra.storage

import java.time.Instant

import com.sample.core.domain.{BasicUser, User}
import com.sample.infra.testingutils.Values
import org.scalatest.{BeforeAndAfterEach, FunSpec, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global

class SampleDbSqlSpec extends FunSpec with Matchers with BeforeAndAfterEach {
  val db = new SampleDbSql(Values.dbConf)

  override def beforeEach(): Unit = db.createTables().unsafeRunSync()

  override def afterEach(): Unit = db.dropTables().unsafeRunSync()

  describe("SampleDbSql") {
    it("should create and select a user") {
      val user = BasicUser(User.Id.generate(), "first-name", "last-name", "email", Instant.now())
      db.findUsers().unsafeRunSync() shouldBe Seq()
      db.find(user.id).unsafeRunSync() shouldBe None
      db.create(user).unsafeRunSync() shouldBe user
      db.findUsers().unsafeRunSync() shouldBe Seq(user)
      db.find(user.id).unsafeRunSync() shouldBe Some(user)
    }
  }
}
