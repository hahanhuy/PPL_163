

import util.parsing.combinator.JavaTokenParsers
import mc.utils._

//For making AST from String of AST
abstract class Item
case class ItemList(items: List[Item]) extends Item {
  override def toString = "[" + items.mkString(",") + "]"
}
case class Ident(name: String) extends Item {
  override def toString = name
}
case class Term(name: String, args: List[Item]) extends Item {
  override def toString = name + "(" + args.mkString(",") + ")"
}
case class IntItem(value: Int) extends Item {
  override def toString = "" + value 
}



class AstRebuild extends IntermediateMCParser {
    def generate(str: String) : AST = {
      val result = parseAll(item, str )
      //println(result.get)
      val ast = ASTGenerator.program(result.get)
      ast
    }
  }

class IntermediateMCParser extends JavaTokenParsers {
  def item: Parser[Item] =
    "[" ~> repsep(item, ",") <~ "]" ^^ { ItemList(_) }|
    ident ~ opt(("(" ~> repsep(item, (","|";")) <~  ")") ) ^^ {
      case id ~ None      => Ident(id)
      case id ~ Some(lst) => Term(id, lst)
    } |
    floatingPointNumber ^^ { x =>  IntItem(x.toInt);
    }

}

object ASTGenerator {
  def typ(item: Item): Type = item match {
    case Ident(name) => name match {
      case "IntType"                                                        => IntType
      case "VoidType"                                                       => VoidType
      case _                                                                => throw new IllegalStateException("Type Wrong")
    }
    case _                                                                  => throw new IllegalStateException("Type Wrong")
  }

  def expr(item: Item): Expr = item match {
    case Term("IntLit", List(IntItem(value)))                       => IntLiteral(value)
    case Term("CallExp", List(Ident(name), ItemList(list)))         => CallExpr(name, list.map(a => expr(a)))
    case _                                                          => throw new IllegalStateException("Expression Wrong")
  }

  def stmt(item: Item): Stmt = item match {

    case Term("Block", List(ItemList(decls), ItemList(stmts)))         => Block(decls.map(b => declare(b)), stmts.map(b => stmt(b)))
    case _                                                             => expr(item)
  }


  def declare(item: Item): Decl = item match {
    case Term("FuncDecl", List(Ident(name), ItemList(args),rtyp,blck))        => FuncDecl(name, args.map(a => declare(a)),typ(rtyp),stmt(blck))
    case _                                                                    => throw new IllegalStateException("Declaration Wrong")
  }

  def program(item: Item): Program = item match {
    case Term("Program", List(ItemList(decs)))                                => Program(decs.map(a => declare(a)))
    case _                                                                    => throw new IllegalStateException("Program Wrong")
  }
}

