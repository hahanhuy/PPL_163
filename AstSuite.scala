import org.scalatest.FunSuite
import mc.utils._

/**
  * Created by nhphung on 4/29/17.
  */
class AstSuite extends FunSuite with TestAst {
test("a simple program") {
  val input = "void main () {}"
  val expected = Program(List(FuncDecl("main",List(),VoidType,Block(List(),List()))))
  assert(checkAst(input,expected,301))
}
  test("another simple program") {
    val input = "int main () {}"
    val expected = Program(List(FuncDecl("main",List(),IntType,Block(List(),List()))))
    assert(checkAst(input,expected,302))
  }
  test("call putIntLn") {
    val input = "void main () {putIntLn(5);}"
    val expected = Program(List(FuncDecl("main",List(),VoidType,Block(List(),List(CallExpr("putIntLn",List(IntLiteral(5))))))))
    assert(checkAst(input,expected,303))
  }
}
