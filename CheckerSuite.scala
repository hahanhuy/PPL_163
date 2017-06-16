import org.scalatest.FunSuite
import mc.checker._
import mc.utils._

/**
  * Created by nhphung on 4/29/17.
  */
class CheckerSuite extends FunSuite with TestChecker {
test("Undeclared Function") {
  val input = "void main () {writeIntLn(3);}"
  val expected = "Undeclared Function: writeIntLn"
  assert(checkCkr(input,expected,401))
}
  test("Type Mismatch In Expression: getInt") {
    val input = "void main () {getInt(3);}"
    val expected = "Type Mismatch In Expression: "+CallExpr("getInt",List(IntLiteral(3))).toString
    assert(checkCkr(input,expected,402))
  }
  test("Type Mismatch In Expression: putIntLn") {

    val input = "void main () {putIntLn();}"
    val expected = "Type Mismatch In Expression: "+CallExpr("putIntLn",List()).toString
    assert(checkCkr(input,expected,403))
  }
}
