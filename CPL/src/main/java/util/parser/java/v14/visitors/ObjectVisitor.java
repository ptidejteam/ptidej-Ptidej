/*******************************************************************************
 * Copyright (c) 2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
//
// Generated by JTB 1.2.2
//

package util.parser.java.v14.visitors;
import util.parser.java.v14.nodes.*;

/**
 * All Object visitors must implement this interface.
 */
public interface ObjectVisitor {
   //
   // Object Auto class visitors
   //
   public Object visit(NodeList n, Object argu);
   public Object visit(NodeListOptional n, Object argu);
   public Object visit(NodeOptional n, Object argu);
   public Object visit(NodeSequence n, Object argu);
   public Object visit(NodeToken n, Object argu);

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> [ PackageDeclaration() ]
    * f1 -> ( ImportDeclaration() )*
    * f2 -> ( TypeDeclaration() )*
    * f3 -> <EOF>
    */
   public Object visit(CompilationUnit n, Object argu);

   /**
    * f0 -> "package"
    * f1 -> Name()
    * f2 -> ";"
    */
   public Object visit(PackageDeclaration n, Object argu);

   /**
    * f0 -> "import"
    * f1 -> Name()
    * f2 -> [ "." "*" ]
    * f3 -> ";"
    */
   public Object visit(ImportDeclaration n, Object argu);

   /**
    * f0 -> ClassDeclaration()
    *       | InterfaceDeclaration()
    *       | ";"
    */
   public Object visit(TypeDeclaration n, Object argu);

   /**
    * f0 -> ( "abstract" | "final" | "public" | "strictfp" )*
    * f1 -> UnmodifiedClassDeclaration()
    */
   public Object visit(ClassDeclaration n, Object argu);

   /**
    * f0 -> "class"
    * f1 -> <IDENTIFIER>
    * f2 -> [ "extends" Name() ]
    * f3 -> [ "implements" NameList() ]
    * f4 -> ClassBody()
    */
   public Object visit(UnmodifiedClassDeclaration n, Object argu);

   /**
    * f0 -> "{"
    * f1 -> ( ClassBodyDeclaration() )*
    * f2 -> "}"
    */
   public Object visit(ClassBody n, Object argu);

   /**
    * f0 -> ( "static" | "abstract" | "final" | "public" | "protected" | "private" | "strictfp" )*
    * f1 -> UnmodifiedClassDeclaration()
    */
   public Object visit(NestedClassDeclaration n, Object argu);

   /**
    * f0 -> Initializer()
    *       | NestedClassDeclaration()
    *       | NestedInterfaceDeclaration()
    *       | ConstructorDeclaration()
    *       | MethodDeclaration()
    *       | FieldDeclaration()
    */
   public Object visit(ClassBodyDeclaration n, Object argu);

   /**
    * f0 -> ( "public" | "protected" | "private" | "static" | "abstract" | "final" | "native" | "synchronized" | "strictfp" )*
    * f1 -> ResultType()
    * f2 -> <IDENTIFIER>
    * f3 -> "("
    */
   public Object visit(MethodDeclarationLookahead n, Object argu);

   /**
    * f0 -> ( "abstract" | "public" | "strictfp" )*
    * f1 -> UnmodifiedInterfaceDeclaration()
    */
   public Object visit(InterfaceDeclaration n, Object argu);

   /**
    * f0 -> ( "static" | "abstract" | "final" | "public" | "protected" | "private" | "strictfp" )*
    * f1 -> UnmodifiedInterfaceDeclaration()
    */
   public Object visit(NestedInterfaceDeclaration n, Object argu);

   /**
    * f0 -> "interface"
    * f1 -> <IDENTIFIER>
    * f2 -> [ "extends" NameList() ]
    * f3 -> "{"
    * f4 -> ( InterfaceMemberDeclaration() )*
    * f5 -> "}"
    */
   public Object visit(UnmodifiedInterfaceDeclaration n, Object argu);

   /**
    * f0 -> NestedClassDeclaration()
    *       | NestedInterfaceDeclaration()
    *       | MethodDeclaration()
    *       | FieldDeclaration()
    */
   public Object visit(InterfaceMemberDeclaration n, Object argu);

   /**
    * f0 -> ( "public" | "protected" | "private" | "static" | "final" | "transient" | "volatile" )*
    * f1 -> Type()
    * f2 -> VariableDeclarator()
    * f3 -> ( "," VariableDeclarator() )*
    * f4 -> ";"
    */
   public Object visit(FieldDeclaration n, Object argu);

   /**
    * f0 -> VariableDeclaratorId()
    * f1 -> [ "=" VariableInitializer() ]
    */
   public Object visit(VariableDeclarator n, Object argu);

   /**
    * f0 -> <IDENTIFIER>
    * f1 -> ( "[" "]" )*
    */
   public Object visit(VariableDeclaratorId n, Object argu);

   /**
    * f0 -> ArrayInitializer()
    *       | Expression()
    */
   public Object visit(VariableInitializer n, Object argu);

   /**
    * f0 -> "{"
    * f1 -> [ VariableInitializer() ( "," VariableInitializer() )* ]
    * f2 -> [ "," ]
    * f3 -> "}"
    */
   public Object visit(ArrayInitializer n, Object argu);

   /**
    * f0 -> ( "public" | "protected" | "private" | "static" | "abstract" | "final" | "native" | "synchronized" | "strictfp" )*
    * f1 -> ResultType()
    * f2 -> MethodDeclarator()
    * f3 -> [ "throws" NameList() ]
    * f4 -> ( Block() | ";" )
    */
   public Object visit(MethodDeclaration n, Object argu);

   /**
    * f0 -> <IDENTIFIER>
    * f1 -> FormalParameters()
    * f2 -> ( "[" "]" )*
    */
   public Object visit(MethodDeclarator n, Object argu);

   /**
    * f0 -> "("
    * f1 -> [ FormalParameter() ( "," FormalParameter() )* ]
    * f2 -> ")"
    */
   public Object visit(FormalParameters n, Object argu);

   /**
    * f0 -> [ "final" ]
    * f1 -> Type()
    * f2 -> VariableDeclaratorId()
    */
   public Object visit(FormalParameter n, Object argu);

   /**
    * f0 -> [ "public" | "protected" | "private" ]
    * f1 -> <IDENTIFIER>
    * f2 -> FormalParameters()
    * f3 -> [ "throws" NameList() ]
    * f4 -> "{"
    * f5 -> [ ExplicitConstructorInvocation() ]
    * f6 -> ( BlockStatement() )*
    * f7 -> "}"
    */
   public Object visit(ConstructorDeclaration n, Object argu);

   /**
    * f0 -> "this" Arguments() ";"
    *       | [ PrimaryExpression() "." ] "super" Arguments() ";"
    */
   public Object visit(ExplicitConstructorInvocation n, Object argu);

   /**
    * f0 -> [ "static" ]
    * f1 -> Block()
    */
   public Object visit(Initializer n, Object argu);

   /**
    * f0 -> ( PrimitiveType() | Name() )
    * f1 -> ( "[" "]" )*
    */
   public Object visit(Type n, Object argu);

   /**
    * f0 -> "boolean"
    *       | "char"
    *       | "byte"
    *       | "short"
    *       | "int"
    *       | "long"
    *       | "float"
    *       | "double"
    */
   public Object visit(PrimitiveType n, Object argu);

   /**
    * f0 -> "void"
    *       | Type()
    */
   public Object visit(ResultType n, Object argu);

   /**
    * f0 -> <IDENTIFIER>
    * f1 -> ( "." <IDENTIFIER> )*
    */
   public Object visit(Name n, Object argu);

   /**
    * f0 -> Name()
    * f1 -> ( "," Name() )*
    */
   public Object visit(NameList n, Object argu);

   /**
    * f0 -> ConditionalExpression()
    * f1 -> [ AssignmentOperator() Expression() ]
    */
   public Object visit(Expression n, Object argu);

   /**
    * f0 -> "="
    *       | "*="
    *       | "/="
    *       | "%="
    *       | "+="
    *       | "-="
    *       | "<<="
    *       | ">>="
    *       | ">>>="
    *       | "&="
    *       | "^="
    *       | "|="
    */
   public Object visit(AssignmentOperator n, Object argu);

   /**
    * f0 -> ConditionalOrExpression()
    * f1 -> [ "?" Expression() ":" ConditionalExpression() ]
    */
   public Object visit(ConditionalExpression n, Object argu);

   /**
    * f0 -> ConditionalAndExpression()
    * f1 -> ( "||" ConditionalAndExpression() )*
    */
   public Object visit(ConditionalOrExpression n, Object argu);

   /**
    * f0 -> InclusiveOrExpression()
    * f1 -> ( "&&" InclusiveOrExpression() )*
    */
   public Object visit(ConditionalAndExpression n, Object argu);

   /**
    * f0 -> ExclusiveOrExpression()
    * f1 -> ( "|" ExclusiveOrExpression() )*
    */
   public Object visit(InclusiveOrExpression n, Object argu);

   /**
    * f0 -> AndExpression()
    * f1 -> ( "^" AndExpression() )*
    */
   public Object visit(ExclusiveOrExpression n, Object argu);

   /**
    * f0 -> EqualityExpression()
    * f1 -> ( "&" EqualityExpression() )*
    */
   public Object visit(AndExpression n, Object argu);

   /**
    * f0 -> InstanceOfExpression()
    * f1 -> ( ( "==" | "!=" ) InstanceOfExpression() )*
    */
   public Object visit(EqualityExpression n, Object argu);

   /**
    * f0 -> RelationalExpression()
    * f1 -> [ "instanceof" Type() ]
    */
   public Object visit(InstanceOfExpression n, Object argu);

   /**
    * f0 -> ShiftExpression()
    * f1 -> ( ( "<" | ">" | "<=" | ">=" ) ShiftExpression() )*
    */
   public Object visit(RelationalExpression n, Object argu);

   /**
    * f0 -> AdditiveExpression()
    * f1 -> ( ( "<<" | ">>" | ">>>" ) AdditiveExpression() )*
    */
   public Object visit(ShiftExpression n, Object argu);

   /**
    * f0 -> MultiplicativeExpression()
    * f1 -> ( ( "+" | "-" ) MultiplicativeExpression() )*
    */
   public Object visit(AdditiveExpression n, Object argu);

   /**
    * f0 -> UnaryExpression()
    * f1 -> ( ( "*" | "/" | "%" ) UnaryExpression() )*
    */
   public Object visit(MultiplicativeExpression n, Object argu);

   /**
    * f0 -> ( "+" | "-" ) UnaryExpression()
    *       | PreIncrementExpression()
    *       | PreDecrementExpression()
    *       | UnaryExpressionNotPlusMinus()
    */
   public Object visit(UnaryExpression n, Object argu);

   /**
    * f0 -> "++"
    * f1 -> PrimaryExpression()
    */
   public Object visit(PreIncrementExpression n, Object argu);

   /**
    * f0 -> "--"
    * f1 -> PrimaryExpression()
    */
   public Object visit(PreDecrementExpression n, Object argu);

   /**
    * f0 -> ( "~" | "!" ) UnaryExpression()
    *       | CastExpression()
    *       | PostfixExpression()
    */
   public Object visit(UnaryExpressionNotPlusMinus n, Object argu);

   /**
    * f0 -> "(" PrimitiveType()
    *       | "(" Name() "[" "]"
    *       | "(" Name() ")" ( "~" | "!" | "(" | <IDENTIFIER> | "this" | "super" | "new" | Literal() )
    */
   public Object visit(CastLookahead n, Object argu);

   /**
    * f0 -> PrimaryExpression()
    * f1 -> [ "++" | "--" ]
    */
   public Object visit(PostfixExpression n, Object argu);

   /**
    * f0 -> "(" Type() ")" UnaryExpression()
    *       | "(" Type() ")" UnaryExpressionNotPlusMinus()
    */
   public Object visit(CastExpression n, Object argu);

   /**
    * f0 -> PrimaryPrefix()
    * f1 -> ( PrimarySuffix() )*
    */
   public Object visit(PrimaryExpression n, Object argu);

   /**
    * f0 -> Literal()
    *       | "this"
    *       | "super" "." <IDENTIFIER>
    *       | "(" Expression() ")"
    *       | AllocationExpression()
    *       | ResultType() "." "class"
    *       | Name()
    */
   public Object visit(PrimaryPrefix n, Object argu);

   /**
    * f0 -> "." "this"
    *       | "." AllocationExpression()
    *       | "[" Expression() "]"
    *       | "." <IDENTIFIER>
    *       | Arguments()
    */
   public Object visit(PrimarySuffix n, Object argu);

   /**
    * f0 -> <INTEGER_LITERAL>
    *       | <FLOATING_POINT_LITERAL>
    *       | <CHARACTER_LITERAL>
    *       | <STRING_LITERAL>
    *       | BooleanLiteral()
    *       | NullLiteral()
    */
   public Object visit(Literal n, Object argu);

   /**
    * f0 -> "true"
    *       | "false"
    */
   public Object visit(BooleanLiteral n, Object argu);

   /**
    * f0 -> "null"
    */
   public Object visit(NullLiteral n, Object argu);

   /**
    * f0 -> "("
    * f1 -> [ ArgumentList() ]
    * f2 -> ")"
    */
   public Object visit(Arguments n, Object argu);

   /**
    * f0 -> Expression()
    * f1 -> ( "," Expression() )*
    */
   public Object visit(ArgumentList n, Object argu);

   /**
    * f0 -> "new" PrimitiveType() ArrayDimsAndInits()
    *       | "new" Name() ( ArrayDimsAndInits() | Arguments() [ ClassBody() ] )
    */
   public Object visit(AllocationExpression n, Object argu);

   /**
    * f0 -> ( "[" Expression() "]" )+ ( "[" "]" )*
    *       | ( "[" "]" )+ ArrayInitializer()
    */
   public Object visit(ArrayDimsAndInits n, Object argu);

   /**
    * f0 -> LabeledStatement()
    *       | Block()
    *       | EmptyStatement()
    *       | StatementExpression() ";"
    *       | SwitchStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | DoStatement()
    *       | ForStatement()
    *       | BreakStatement()
    *       | ContinueStatement()
    *       | ReturnStatement()
    *       | ThrowStatement()
    *       | SynchronizedStatement()
    *       | TryStatement()
    *       | AssertStatement()
    */
   public Object visit(Statement n, Object argu);

   /**
    * f0 -> <IDENTIFIER>
    * f1 -> ":"
    * f2 -> Statement()
    */
   public Object visit(LabeledStatement n, Object argu);

   /**
    * f0 -> "{"
    * f1 -> ( BlockStatement() )*
    * f2 -> "}"
    */
   public Object visit(Block n, Object argu);

   /**
    * f0 -> LocalVariableDeclaration() ";"
    *       | Statement()
    *       | UnmodifiedClassDeclaration()
    *       | UnmodifiedInterfaceDeclaration()
    */
   public Object visit(BlockStatement n, Object argu);

   /**
    * f0 -> [ "final" ]
    * f1 -> Type()
    * f2 -> VariableDeclarator()
    * f3 -> ( "," VariableDeclarator() )*
    */
   public Object visit(LocalVariableDeclaration n, Object argu);

   /**
    * f0 -> ";"
    */
   public Object visit(EmptyStatement n, Object argu);

   /**
    * f0 -> PreIncrementExpression()
    *       | PreDecrementExpression()
    *       | PrimaryExpression() [ "++" | "--" | AssignmentOperator() Expression() ]
    */
   public Object visit(StatementExpression n, Object argu);

   /**
    * f0 -> "switch"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> "{"
    * f5 -> ( SwitchLabel() ( BlockStatement() )* )*
    * f6 -> "}"
    */
   public Object visit(SwitchStatement n, Object argu);

   /**
    * f0 -> "case" Expression() ":"
    *       | "default" ":"
    */
   public Object visit(SwitchLabel n, Object argu);

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> [ "else" Statement() ]
    */
   public Object visit(IfStatement n, Object argu);

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public Object visit(WhileStatement n, Object argu);

   /**
    * f0 -> "do"
    * f1 -> Statement()
    * f2 -> "while"
    * f3 -> "("
    * f4 -> Expression()
    * f5 -> ")"
    * f6 -> ";"
    */
   public Object visit(DoStatement n, Object argu);

   /**
    * f0 -> "for"
    * f1 -> "("
    * f2 -> [ ForInit() ]
    * f3 -> ";"
    * f4 -> [ Expression() ]
    * f5 -> ";"
    * f6 -> [ ForUpdate() ]
    * f7 -> ")"
    * f8 -> Statement()
    */
   public Object visit(ForStatement n, Object argu);

   /**
    * f0 -> LocalVariableDeclaration()
    *       | StatementExpressionList()
    */
   public Object visit(ForInit n, Object argu);

   /**
    * f0 -> StatementExpression()
    * f1 -> ( "," StatementExpression() )*
    */
   public Object visit(StatementExpressionList n, Object argu);

   /**
    * f0 -> StatementExpressionList()
    */
   public Object visit(ForUpdate n, Object argu);

   /**
    * f0 -> "break"
    * f1 -> [ <IDENTIFIER> ]
    * f2 -> ";"
    */
   public Object visit(BreakStatement n, Object argu);

   /**
    * f0 -> "continue"
    * f1 -> [ <IDENTIFIER> ]
    * f2 -> ";"
    */
   public Object visit(ContinueStatement n, Object argu);

   /**
    * f0 -> "return"
    * f1 -> [ Expression() ]
    * f2 -> ";"
    */
   public Object visit(ReturnStatement n, Object argu);

   /**
    * f0 -> "throw"
    * f1 -> Expression()
    * f2 -> ";"
    */
   public Object visit(ThrowStatement n, Object argu);

   /**
    * f0 -> "synchronized"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Block()
    */
   public Object visit(SynchronizedStatement n, Object argu);

   /**
    * f0 -> "try"
    * f1 -> Block()
    * f2 -> ( "catch" "(" FormalParameter() ")" Block() )*
    * f3 -> [ "finally" Block() ]
    */
   public Object visit(TryStatement n, Object argu);

   /**
    * f0 -> "assert"
    * f1 -> Expression()
    * f2 -> [ ":" Expression() ]
    * f3 -> ";"
    */
   public Object visit(AssertStatement n, Object argu);

}
