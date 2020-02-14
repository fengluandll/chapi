package chapi.ast.csharpast

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CSharpFullIdentListenerTest {

    @Test
    fun shouldIdentUsingSystem() {
        val code = """
using System; 
  
namespace HelloWorldApp { 
    class Geeks { 
        static void Main(string[] args) { 
            Console.WriteLine("Hello World!"); 
            Console.ReadKey(); 
        } 
    } 
} 
"""
        val codeFile = CSharpAnalyser().analysis(code, "hello.cs")
        assertEquals(codeFile.Imports.size, 1)
        assertEquals(codeFile.Imports[0].Source, "System")
    }

    @Test
    fun shouldIdentUsingNamespace() {
        val code = """
using SomeNameSpace.Nested;

"""
        val codeFile = CSharpAnalyser().analysis(code, "hello.cs")
        assertEquals(codeFile.Imports.size, 1)
        assertEquals(codeFile.Imports[0].Source, "SomeNameSpace.Nested")
    }

    @Test
    fun shouldIdentUsingAlias() {
        val code = """
using generics = System.Collections.Generic;

"""
        val codeFile = CSharpAnalyser().analysis(code, "hello.cs")
        assertEquals(codeFile.Imports.size, 1)
        assertEquals(codeFile.Imports[0].Source, "System.Collections.Generic")
        assertEquals(codeFile.Imports[0].AsName, "generics")
    }

    @Test
    fun shouldIdentDeclNameSpace() {
        val code = """
using System; 
  
namespace HelloWorldApp {

}
"""
        val codeContainer = CSharpAnalyser().analysis(code, "hello.cs")
        assertEquals(codeContainer.Containers.size, 1)
        assertEquals(codeContainer.Containers[0].PackageName, "HelloWorldApp")
    }

    @Test
    fun shouldIdentDeclNameSpaceInNameSpace() {
        val code = """
using System; 
  
namespace HelloWorldApp {
  namespace HelloWorldApp2 {
    namespace HelloWorldApp3 {
    
    }
  }
}
"""
        val codeContainer = CSharpAnalyser().analysis(code, "hello.cs")
        assertEquals(codeContainer.Containers.size, 1)
        assertEquals(codeContainer.Containers[0].PackageName, "HelloWorldApp")
        assertEquals(codeContainer.Containers[0].Containers[0].PackageName, "HelloWorldApp2")
        assertEquals(codeContainer.Containers[0].Containers[0].Containers[0].PackageName, "HelloWorldApp3")
    }

    @Test
    fun shouldIdentClassName() {
        val code = """
using System; 
  
namespace HelloWorldApp { 
    class Geeks { 
        static void Main(string[] args) { 
            Console.WriteLine("Hello World!"); 
            Console.ReadKey(); 
        } 
    } 
} 
"""
        val codeContainer = CSharpAnalyser().analysis(code, "hello.cs")
        println(codeContainer.toString())
        assertEquals(codeContainer.Containers[0].DataStructures.size, 1)
        assertEquals(codeContainer.Containers[0].DataStructures[0].NodeName, "Geeks")
    }
}
