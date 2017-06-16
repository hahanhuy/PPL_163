package mc.checker

/**
 * @author nhphung
 */

import mc.parser._
import mc.utils._
import java.io.{File, PrintWriter}

import mc.codegen.Val
import org.antlr.v4.runtime.ANTLRFileStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree._

import scala.collection.JavaConverters._

case class MType(partype:List[Type],rettype:Type) extends Type //FunctionType
case class Symbol(val name:String,val mtype:Type,val value:Any)

class StaticChecker(ast:AST) extends BaseVisitor with Utils {
    val global = List(Symbol("getInt",MType(List(),IntType),null),
                      Symbol("putIntLn",MType(List(IntType),VoidType),null))
    def check() = visit(ast,global)

    override def visitProgram(ast: Program, c: Any): Any = ast.decl.map(visit(_,c))

    override def visitFuncDecl(ast: FuncDecl, c: Any): Any = ast.body.asInstanceOf[Block].stmt.map(visit(_,c))

    override def visitCallExpr(ast: CallExpr, c: Any): Any = {
        val env = c.asInstanceOf[List[Symbol]]
        val at = ast.params.map(visit(_,c).asInstanceOf[Type])
        val res = lookup(ast.method,env,(x:Symbol)=>x.name)
        //println(res)
        res match {
            case Some(Symbol(_,MType(pl,rt),_)) =>
                if (pl.length!=at.length || pl.zip(at).exists(x => x._1 != x._2)) throw TypeMismatchInExpression(ast)
                else rt

            case Some(_) | None => throw Undeclared(Function,ast.method)
        }
    }

    override def visitIntLiteral(ast: IntLiteral, c: Any): Any = IntType
}
