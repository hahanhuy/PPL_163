package mc.utils

/**
 * @author nhphung
 */


trait AST {
        def accept(v: Visitor, param: Any) =  v.visit(this,param)
}

case class Program(val decl: List[Decl]) extends AST {
        override def toString = "Program(" + decl.mkString("[",",","]") + ")"
        override def accept(v: Visitor, param: Any) = v.visitProgram(this,param)
}


trait Decl extends AST 

case class FuncDecl(val name: String, val param: List[Decl], val returnType: Type,val body: Stmt) extends Decl {
        override def toString = "FuncDecl(" + name + "," + param.mkString("[",",","]")  + "," +  returnType +"," + body + ")"
        override def accept(v: Visitor, param: Any) = v.visitFuncDecl(this,param)
}

/*        TYPE        */
trait Type extends AST
object IntType extends Type {
        override def toString = "IntType"
        override def accept(v: Visitor, param: Any) = v.visitIntType(this,param)
}
object VoidType extends Type {
        override def toString = "VoidType"
        override def accept(v: Visitor, param: Any) = v.visitVoidType(this,param)
}
object StringType extends Type {
        override def toString = "StringType"
        override def accept(v: Visitor, param: Any) = v.visitStringType(this,param)
}
case class PointerType(pointTo:Type) extends Type {
        override def toString = "PointerType"
        override def accept(v: Visitor, param: Any) = v.visitPointerType(this,param)
}
/*        expr        */
trait Expr extends Stmt 


case class CallExpr(val method: String, val params: List[Expr]) extends Expr {
        override def toString = "CallExp("  + method + "," + params.mkString("[",",","]")+ ")"
        override def accept(v: Visitor, param: Any) = v.visitCallExpr(this,param)
}


/*        STMT        */
trait Stmt extends AST
case class Block(val decl: List[Decl], val stmt: List[Stmt]) extends Stmt {
        override def toString = "Block(" +  decl.mkString("[",",","]") + "," + stmt.mkString("[",",","]") + ")"
        override def accept(v: Visitor, param: Any) = v.visitBlock(this,param)
}



/*        LITERAL        */
trait Literal extends Expr
case class IntLiteral(val value: Int) extends Literal {
        override def toString = "IntLit(" + value.toString + ")"
        override def accept(v: Visitor, param: Any) = v.visitIntLiteral(this,param)
}

