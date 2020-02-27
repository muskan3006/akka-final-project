package com.knoldus

import java.io.File

import scala.io.Source

/**
 * It has all the methods that calculate the sum of errors,sum of info and sum of warnings in all the files in the directory
 */
object Helper {
  /**
   * countError method gives the sum of errors in all the files in the directory
   *
   * @param file a file from the directory
   * @return
   */
  def countError(file: File): Int = {
    val source = Source.fromFile(file)
    val content = source.getLines.toList
    content.foldLeft(0) { (result, line) => {
      if (line.contains("[ERROR]")) {
        result + 1
      } else {
        result
      }

    }
    }
  }

  /**
   * countError method gives the sum of info in all the files in the directory
   *
   * @param file a file from the directory
   * @return
   */
  def countInfo(file: File): Int = {
    val source = Source.fromFile(file)
    val content = source.getLines.toList
    content.foldLeft(0) { (result, line) => {
      if (line.contains("[INFO]")) {
        result + 1
      } else {
        result
      }

    }
    }
  }

  /**
   * countError method gives the sum of warnings in all the files in the directory
   *
   * @param file a file from the directory
   * @return
   */
  def countWarn(file: File): Int = {
    val source = Source.fromFile(file)
    val content = source.getLines.toList
    content.foldLeft(0) { (result, line) => {
      if (line.contains("[WARN]")) {
        result + 1
      } else {
        result
      }

    }
    }
  }
}
