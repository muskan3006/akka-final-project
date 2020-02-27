package com.knoldus

import org.scalatest._

class LogAnalysisSpec extends FunSuite with BeforeAndAfterAll {

    var logAnalysis: LogAnalysis = _

    override def beforeAll(): Unit = {
      logAnalysis = new LogAnalysis
    }

    override def afterAll(): Unit = {
      if (logAnalysis != null) {
        logAnalysis = null
      }
    }
test("testing totalCount"){
  val actual = logAnalysis.totalCount
  val expected = (0,535,1561)
  assert(actual==expected)
}

  test("testing average"){
    val actual = logAnalysis.average((0,535,1561))
    val expected = Map("averageErrors" -> 0, "averageInfo" -> 89, "averageWarn" -> 260)
    assert(actual==expected)
  }

}
