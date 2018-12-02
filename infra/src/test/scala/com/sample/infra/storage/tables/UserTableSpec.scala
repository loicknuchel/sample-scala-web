package com.sample.infra.storage.tables

import cats.effect.IO
import com.danielasfregola.randomdatagenerator.RandomDataGenerator
import com.sample.core.domain.{BasicUser, User}
import com.sample.infra.storage.SampleDbSql
import com.sample.infra.testingutils.Generators._
import com.sample.infra.testingutils.Values
import doobie.scalatest._
import org.scalatest.{BeforeAndAfterAll, FunSpec, Matchers}

class UserTableSpec extends FunSpec with Matchers with BeforeAndAfterAll with IOChecker with RandomDataGenerator {
  val db = new SampleDbSql(Values.dbConf)

  override def transactor: doobie.Transactor[IO] = db.xa

  override def beforeAll(): Unit = db.createTables().unsafeRunSync()

  override def afterAll(): Unit = db.dropTables().unsafeRunSync()

  describe("UserTable") {
    it("should select all") {
      check(UserTable.selectAll())
    }
    it("should select by id") {
      check(UserTable.selectById(User.Id.generate()))
    }
    it("should insert") {
      check(UserTable.insert(random[BasicUser]))
    }
  }
}
