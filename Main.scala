

/**
 * @author nhphung
 */
package mc

import java.io.File
import java.net.URL
import java.net.MalformedURLException
import java.net.URLClassLoader
import java.util.concurrent.{Executors, TimeUnit}

import jasmin.Main
import org.antlr.v4.runtime.ANTLRFileStream
import org.antlr.v4.runtime.CommonTokenStream
import mc.parser._
import mc.astgen._
import mc.checker._
import mc.codegen._
import mc.utils._


trait Timed {
  def timeoutAfter(timeout: Long)(codeToTest: => Unit): Unit = {
    val executor = Executors.newSingleThreadExecutor
    val future = executor.submit(new Runnable {
      def run = codeToTest
    })

    try {
      future.get(timeout, TimeUnit.MILLISECONDS)
    }
    finally {
      executor.shutdown()
    }
  }
}

object Compiler extends  Timed {
  
  val sepa = File.separator // dung cho linux

  val testdir = "src"+sepa+"test"+sepa+"scala"+sepa

  def main(args: Array[String]): Unit = {
    if (args.length > 0) {
      val filename = args(0)
      try {
 /*       //--- Compiling ------
        // Stage 1: Lexical Analysis
        val infile = new ANTLRFileStream(filename)
        val lexer = new MCLexer(infile)
        val tokens = new CommonTokenStream(lexer);

        // Stage 2: Syntax Analysis
        val parser = new MCParser(tokens);
        val progtree = parser.program()

        // Stage 3: Intermediate Code (AST) Generation
        val astbuild = new ASTGeneration()
        val ast = astbuild.visit(progtree).asInstanceOf[Program]

        // Stage 4: Semantics Analysis
        val checker = new StaticChecker(ast)
        checker.check()

        // Stage 5: Optimization
        // Dont do any optimization

        // Stage 6: Target Code Generation
        val outdir = "result"
        val flder = new File(outdir)
        val succ = flder.mkdir
        CodeGenerator.gen(ast, flder)
        // ---- End compiling -----
*/val outdir = "result"
        val flder = new File(outdir)
        // Assembler
        Main.main(Array("-d",outdir,outdir+sepa+"MCClass.j"))

        //Load and run


        //try { // Convert File to a URL


      } catch {
        case e: RuntimeException => println(e.getMessage())
      }
    } else
      println("Usage: scala mc.Compiler <inputfile>")
  }
  

}