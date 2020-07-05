package practice

import practice.Models.{Circle, Employee, IceCream, Rectangle, Shapes}
import practice.CsvEncoder.CsvSyntax

object ScalaWorldLecture extends App {

  def encodeCsv[A](a : A)(implicit c: CsvEncoder[A]): List[String] = c.encode(a)

  println("Ryan".toCsv)
  println(true.toCsv)

  println(Employee("John", 42, true).toCsv)
  println(IceCream("Cornetto", 3, false).toCsv)

  println(Rectangle(2.0, 3.0).toCsv)
  println(Circle(2.0).toCsv)

}
