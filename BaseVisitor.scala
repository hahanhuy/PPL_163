package mc.utils

/**
 * @author nhphung
 */
class BaseVisitor extends Visitor {
  def visitProgram(ast: Program, c: Any): Any = null
  def visitFuncDecl(ast: FuncDecl, c: Any): Any = null
  def visitIntType(ast: IntType.type, c: Any): Any  = null
  def visitStringType(ast: StringType.type, c: Any): Any  = null
  def visitPointerType(ast: PointerType, c: Any): Any  = null
  def visitVoidType(ast: VoidType.type, c: Any): Any = null
  def visitCallExpr(ast: CallExpr, c: Any): Any = null
  def visitBlock(ast: Block, c: Any): Any = null
  def visitIntLiteral(ast: IntLiteral, c: Any): Any = null
}
