package practice

object Models {
  case class Employee(name: String, age: Int, current: Boolean)
  case class IceCream(name: String, price: Int, inStock: Boolean)

  sealed trait Shapes
  case class Rectangle(width: Double, height: Double) extends Shapes
  case class Circle(radius: Double) extends Shapes



}
