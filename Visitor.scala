package mc.utils

/**
 * @author nhphung
 */
trait Visitor {
  def visit(ast: AST, c: Any): Any = ast.accept(this,c)
  def visitProgram(ast: Program, c: Any): Any
  def visitFuncDecl(ast: FuncDecl, c: Any): Any
  def visitIntType(ast: IntType.type, c: Any): Any
  def visitStringType(ast: StringType.type, c: Any): Any
  def visitPointerType(ast: PointerType, c: Any): Any
  def visitVoidType(ast: VoidType.type, c: Any): Any
  def visitCallExpr(ast: CallExpr, c: Any): Any
  def visitBlock(ast: Block, c: Any): Any
  def visitIntLiteral(ast: IntLiteral, c: Any): Any
}
