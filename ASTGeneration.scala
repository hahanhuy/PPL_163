package mc.astgen
import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.ParserRuleContext
import java.io.{PrintWriter,File}
import org.antlr.v4.runtime.ANTLRFileStream
import mc.utils._
import scala.collection.JavaConverters._
import org.antlr.v4.runtime.tree._
import mc.parser._
import mc.parser.MCParser._

class ASTGeneration extends MCBaseVisitor[Any] {

  override def visitProgram(ctx:ProgramContext) = 
  			Program(List( 						
  						FuncDecl("main",List(),visit(ctx.mctype).asInstanceOf[Type],
								if (ctx.body != null) visit(ctx.body).asInstanceOf[Stmt] else Block(List(),List()))
  			       ))

	override def visitBody(ctx:BodyContext) =  //Block(List(),List(CallExpr(ctx.ID.getText, if (ctx.INTLIT != null) List(IntLiteral(ctx.INTLIT.getText.toInt)) else List())))
				Block(List(),List(visit(ctx.funcall).asInstanceOf[Stmt]))

	override def visitFuncall(ctx:FuncallContext) = CallExpr(ctx.ID.getText,if (ctx.exp == null) List() else List(visit(ctx.exp).asInstanceOf[Expr]))

	override def visitExp(ctx:ExpContext) = if (ctx.INTLIT != null) IntLiteral(ctx.INTLIT.getText.toInt) else visit(ctx.funcall)

	override def visitMctype(ctx:MctypeContext) = if (ctx.INTTYPE != null) IntType else VoidType


}