/**
 * @author nhphung
 */


import java.io.{File, PrintWriter}
import java.util.concurrent.TimeoutException

import mc.astgen._
import mc.checker._
import mc.parser._
import mc.utils._
import org.antlr.v4.runtime.{ANTLRFileStream, CommonTokenStream}

import scala.io.Source


trait TestChecker extends TestUtils {
  def test(infile:ANTLRFileStream,outfile:PrintWriter) = {
    val lexer = new MCLexer(infile);
    val tokens = new CommonTokenStream(lexer);
    val parser = new MCParser(tokens);
    val progtree = parser.program()
    
    val astbuild = new ASTGeneration()
    val ast = astbuild.visit(progtree).asInstanceOf[Program]
    //println(ast)
    val checker = new StaticChecker(ast)



    try {
        checker.check()
    } catch {
      case Undeclared(k, n)  => outfile.println("Undeclared "+k+": "+n)
      case Redeclared(k, n)  => outfile.println("Redeclared "+k+": "+n)
      case TypeMismatchInExpression(exp) => outfile.println("Type Mismatch In Expression: "+exp)
      case TypeMismatchInStatement(stmt) => outfile.println("Type Mismatch In Statement: "+stmt)
      case FunctionNotReturn(m) => outfile.println("Function Not Return: "+m)
      case BreakNotInLoop => outfile.println("Break Not In Loop")
      case ContinueNotInLoop => outfile.println("Continue Not In Loop")
    }
  }

  def checkCkr(input:String,expected:String,num:Int) = {
    val source = makeSource(input,num)
    val dest = new PrintWriter(new File(s"$soldir$sepa$num.txt"))

    try {
      timeoutAfter(500) {

          test(source, dest)


      }
    }
    catch {
      case te: TimeoutException => dest.println("Test runs timeout")
    }
    finally {
      dest.close
    }
    val result = Source.fromFile(s"$soldir$sepa$num.txt").getLines.mkString("")
    //val astgen = new AstRebuild
    //val ast = astgen.generate(result)
    //println(expected.toString)
    if (result == expected) true else false
  }
}