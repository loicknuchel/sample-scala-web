package com.sample.infra.testingutils

import com.sample.infra.storage._

object Values {
  val dbConf: DbConf = H2("org.postgresql.Driver", "jdbc:h2:mem:sample_db;MODE=PostgreSQL;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1")
  //val dbConf: DbConf = PostgreSQL("jdbc:postgresql:world", "l.knuchel", "l.knuchel")
}
