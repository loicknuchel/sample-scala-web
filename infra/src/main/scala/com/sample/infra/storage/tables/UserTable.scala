package com.sample.infra.storage.tables

import com.sample.core.domain.{BasicUser, User}
import com.sample.infra.utils.DoobieUtils
import com.sample.infra.utils.DoobieUtils.Mappings._
import doobie.Fragment
import doobie.implicits._

object UserTable {
  val table = "users"
  val fields = Seq("id", "first_name", "last_name", "email", "created")

  def selectAll(): doobie.Query0[BasicUser] =
    select(fr"")

  def selectById(id: User.Id): doobie.Query0[BasicUser] =
    select(fr"where id = $id")

  def insert(user: BasicUser): doobie.Update0 =
    DoobieUtils.insert(table, fields, fr"${user.id}, ${user.firstName}, ${user.lastName}, ${user.email}, ${user.created}").update

  private def select(where: Fragment): doobie.Query0[BasicUser] =
    (DoobieUtils.select(table, fields) ++ where).query[BasicUser]
}
