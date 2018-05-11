//### This file created by BYACC 1.8(/Java extension  1.14)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 5 "sintac.y"
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
//#line 24 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:Object
String   yytext;//user variable to return contextual strings
Object yyval; //used to return semantic vals from action routines
Object yylval;//the 'lval' (result) I got from yylex()
Object valstk[] = new Object[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new Object();
  yylval=new Object();
  valptr=-1;
}
final void val_push(Object val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    Object[] newstack = new Object[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final Object val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final Object val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short OR=257;
public final static short AND=258;
public final static short IGUAL=259;
public final static short DISTINTO=260;
public final static short MENORIGUAL=261;
public final static short MAYORIGUAL=262;
public final static short VAR=263;
public final static short IDENT=264;
public final static short INT=265;
public final static short REAL=266;
public final static short CHARACTER=267;
public final static short LITERALINT=268;
public final static short STRUCT=269;
public final static short WHILE=270;
public final static short IF=271;
public final static short ELSE=272;
public final static short RETURN=273;
public final static short NULL=274;
public final static short READ=275;
public final static short PRINT=276;
public final static short PRINTSP=277;
public final static short PRINTLN=278;
public final static short CAST=279;
public final static short LITERALREAL=280;
public final static short LITERALCHAR=281;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    2,    3,    7,    6,    6,
    6,    6,    6,    5,    8,    8,    9,    4,   11,   11,
   10,   10,   13,   13,   14,   12,   15,   15,   16,   16,
   17,   17,   17,   17,   17,   17,   17,   17,   17,   17,
   17,   17,   17,   18,   18,   18,   18,   18,   18,   18,
   18,   18,   18,   18,   18,   18,   18,   18,   18,   18,
   18,   18,   18,   18,   18,   19,   19,   20,   20,
};
final static short yylen[] = {                            2,
    1,    0,    2,    1,    1,    1,    5,    5,    1,    1,
    1,    1,    4,    6,    0,    2,    4,    8,    0,    2,
    0,    1,    1,    3,    3,    2,    0,    2,    0,    2,
    7,    7,   11,    2,    3,    3,    3,    3,    3,    3,
    2,    5,    4,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    7,    1,    1,    1,
    1,    4,    4,    3,    2,    0,    1,    1,    3,
};
final static short yydefred[] = {                         2,
    0,    0,    0,    0,    0,    3,    4,    5,    6,    0,
    0,    0,    0,    0,    0,    0,   23,   15,    0,   12,
    9,   10,   11,    0,    0,    0,    0,    0,    0,    7,
   25,    0,    0,   24,    0,    0,   16,    0,   20,   27,
    0,   14,   13,    0,    0,    0,   18,    0,   28,    0,
   17,    0,    0,    0,    0,   58,    0,    0,    0,    0,
    0,    0,    0,    0,   59,   60,   30,    0,    0,    0,
    0,    0,    0,    0,    0,   34,    0,    0,    0,    0,
    0,   41,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   44,    0,    0,    0,    0,    0,   35,   36,   37,
   38,   39,   40,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   64,    0,    8,
    0,    0,    0,    0,    0,    0,   63,   43,   62,   42,
    0,   29,   29,    0,    0,    0,    0,   31,    0,   57,
    0,   29,    0,   33,
};
final static short yydgoto[] = {                          1,
    2,    6,    7,    8,    9,   24,   49,   28,   37,   15,
   33,   44,   16,   17,   45,   50,   67,   68,  104,  105,
};
final static short yysindex[] = {                         0,
    0, -217, -258,  -23, -256,    0,    0,    0,    0,  -27,
 -238,  -90,  -51,  -13,    9,   12,    0,    0, -206,    0,
    0,    0,    0,    4,  -51,    6, -238, -123,  -28,    0,
    0,  -51,  -57,    0,   10,    8,    0,  -51,    0,    0,
  -51,    0,    0,  -56, -193,   15,    0, -191,    0,   57,
    0,   13,   83,   83,   36,    0,   37,   38,   75,   83,
   83,   83,   78,   20,    0,    0,    0,  448,  -51,   41,
    2,  -32,   83,   83,   83,    0,   23,  456,  481,  489,
  511,    0,  517,  -51,   83,   83,   83,   83,   83,   83,
   83,   83,   83,   83,   83,   83,   83, -180,   83,   26,
   83,    0,  791,   45,   43,  150,  337,    0,    0,    0,
    0,    0,    0,   27,  797,  803,  824,  824,   -8,   -8,
   -8,   -8,    2,    2,  -34,  -34,  564,    0,  738,    0,
   47,   32,   83,  -29,  -25,   55,    0,    0,    0,    0,
  791,    0,    0,   83,  -33,  -15,  372,    0, -176,    0,
  -24,    0,    3,    0,
};
final static short yyrindex[] = {                         0,
    0,  100,    0,    0,    0,    0,    0,    0,    0,    0,
   60,    0,    0,    0,    0,   61,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -20,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   21,    0,    0,    0,    0,  -21,
    0,    0,    0,    0,  744,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  108,
  -40,    0,   64,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   64,    0,  -17,    0,   65,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  139,  836,  660,  814,  392,  414,
  421,  427,  379,  386,  115,  143,    0,    0,    0,    0,
    0,  770,    0,    0,    0,    0,    0,    0,    0,    0,
   14,    0,    0,    0,    0,    0,    0,    0,   39,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,    0,   -9,    0,    0,    0,    0,
    0,    0,    0,   80,    0, -101,    0,  849,   11,    0,
};
final static int YYTABLESIZE=1094;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         53,
   65,   36,   65,   65,   65,   10,   54,   12,  102,   95,
   93,   98,   94,   98,   96,   31,   11,   53,   65,   65,
   65,   65,   39,   68,   54,   14,   68,   89,   43,   90,
   13,   46,   18,   95,   93,   53,   94,   98,   96,   19,
  145,  146,   54,   95,   25,    3,    4,   98,   96,   26,
  153,    5,   65,   29,   69,   27,   97,   69,   97,  100,
   29,   29,   30,   32,   38,   40,   42,   41,   47,   48,
   69,   32,   52,   51,  114,   73,   74,   75,   32,   84,
  101,  108,   97,  128,  130,  132,  133,  139,  136,   53,
  140,  148,   97,  142,  144,  151,   54,  143,  152,    1,
   21,   22,   19,   26,   66,   67,   34,   53,    0,  149,
   53,  131,    0,    0,   54,   53,    0,   54,    0,    0,
    0,    0,   54,    0,    0,    0,    0,  154,    0,    0,
    0,    0,    0,   76,    0,    0,   82,    0,    0,    0,
   35,    0,    0,    0,    0,   29,    0,    0,   61,   61,
   61,   61,   61,   61,   61,   47,   47,   47,   47,   47,
    0,   47,    0,   32,    0,    0,   61,   61,   61,   61,
    0,    0,    0,   47,   47,   47,   47,    0,    0,   53,
    0,    0,   53,   48,   48,   48,   48,   48,    0,   48,
  134,   95,   93,    0,   94,   98,   96,   53,   61,   53,
   61,   48,   48,   48,   48,    0,    0,   47,    0,   89,
    0,   90,   20,   21,   22,   23,   65,   65,   65,   65,
   65,   65,    0,    0,   85,   86,   87,   88,   91,   92,
   55,   53,    0,    0,   56,   48,   57,   58,    0,   59,
   97,   60,   61,   62,   63,   64,   65,   66,   55,    0,
    0,    0,   56,    0,   57,   58,    0,   59,    0,   60,
   61,   62,   63,   64,   65,   66,   55,    0,    0,    0,
   56,    0,   57,   58,    0,   59,    0,   60,   61,   62,
   63,   64,   65,   66,   29,    0,    0,    0,   29,    0,
   29,   29,    0,   29,    0,   29,   29,   29,   29,   29,
   29,   29,   32,    0,    0,    0,   32,    0,   32,   32,
    0,   32,    0,   32,   32,   32,   32,   32,   32,   32,
   55,    0,    0,    0,   56,    0,   57,   58,    0,   59,
    0,   60,   61,   62,   63,   64,   65,   66,   70,    0,
    0,   70,   56,    0,    0,   56,   70,    0,   77,    0,
   56,    0,    0,   64,   65,   66,   64,   65,   66,    0,
    0,   64,   65,   66,   61,   61,   61,   61,   61,   61,
    0,   47,   47,   47,   47,   47,   47,  135,   95,   93,
    0,   94,   98,   96,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   53,   89,    0,   90,   48,
   48,   48,   48,   48,   48,    0,   85,   86,   87,   88,
   91,   92,  150,   95,   93,    0,   94,   98,   96,   45,
    0,   45,   45,   45,    0,    0,   46,   97,   46,   46,
   46,   89,   50,   90,    0,   50,    0,   45,   45,   45,
   45,    0,    0,    0,   46,   46,   46,   46,    0,    0,
   50,   50,   50,   50,   49,    0,    0,   49,    0,    0,
    0,   56,   97,    0,   56,    0,    0,   54,    0,    0,
   54,   45,   49,   49,   49,   49,    0,    0,   46,   56,
   56,   56,   56,    0,   50,   54,   54,   54,   54,   95,
   93,    0,   94,   98,   96,    0,    0,   95,   93,    0,
   94,   98,   96,    0,    0,    0,   49,   89,   99,   90,
    0,    0,    0,   56,  109,   89,    0,   90,    0,   54,
    0,    0,   95,   93,    0,   94,   98,   96,    0,    0,
   95,   93,    0,   94,   98,   96,    0,    0,   97,  110,
   89,    0,   90,    0,    0,    0,   97,  111,   89,    0,
   90,    0,   95,   93,    0,   94,   98,   96,   95,   93,
    0,   94,   98,   96,    0,    0,    0,    0,    0,  112,
   89,   97,   90,    0,    0,  113,   89,    0,   90,   97,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   85,   86,   87,   88,   91,   92,    0,
    0,   97,    0,    0,    0,   95,   93,   97,   94,   98,
   96,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   89,    0,   90,    0,    0,   85,   86,
   87,   88,   91,   92,    0,   45,   45,   45,   45,   45,
   45,    0,   46,   46,   46,   46,   46,   46,   50,   50,
   50,   50,   50,   50,   97,    0,  137,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   49,   49,   49,   49,   49,   49,    0,   56,   56,   56,
   56,   56,   56,   54,   54,   54,   54,   54,   54,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   51,    0,    0,   51,   85,   86,   87,   88,   91,   92,
    0,    0,   85,   86,   87,   88,   91,   92,   51,    0,
   51,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   85,   86,   87,
   88,   91,   92,    0,    0,   85,   86,   87,   88,   91,
   92,    0,   51,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   85,   86,   87,
   88,   91,   92,   85,   86,   87,   88,   91,   92,   95,
   93,    0,   94,   98,   96,   61,   61,    0,   61,   61,
   61,    0,    0,    0,    0,    0,  138,   89,    0,   90,
    0,    0,    0,   61,   61,   61,    0,    0,    0,    0,
    0,   62,   62,    0,   62,   62,   62,    0,    0,    0,
   85,   86,   87,   88,   91,   92,    0,    0,   97,   62,
   62,   62,   95,   93,   61,   94,   98,   96,   95,   93,
    0,   94,   98,   96,   95,   93,    0,   94,   98,   96,
   89,    0,   90,    0,   55,    0,   89,   55,   90,    0,
   62,    0,   89,    0,   90,   95,   93,    0,   94,   98,
   96,    0,   55,    0,   55,    0,   52,    0,    0,   52,
    0,   97,    0,   89,    0,   90,    0,   97,    0,    0,
    0,    0,    0,   97,   52,    0,   52,    0,    0,    0,
    0,   71,   72,    0,    0,    0,   55,   78,   79,   80,
   81,   83,    0,    0,   97,    0,   51,   51,   51,   51,
    0,  103,  106,  107,    0,    0,    0,    0,   52,    0,
    0,    0,    0,  115,  116,  117,  118,  119,  120,  121,
  122,  123,  124,  125,  126,  127,    0,  129,    0,  103,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  141,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  147,    0,   85,   86,   87,   88,   91,   92,
   61,   61,   61,   61,   61,   61,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   62,   62,   62,   62,
   62,   62,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   85,   86,   87,
   88,   91,   92,    0,   86,   87,   88,   91,   92,    0,
    0,   87,   88,   91,   92,    0,    0,    0,    0,    0,
   55,   55,   55,   55,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   91,   92,    0,    0,    0,    0,
    0,    0,   52,   52,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   41,  125,   43,   44,   45,  264,   40,  264,   41,   42,
   43,   46,   45,   46,   47,   25,   40,   33,   59,   60,
   61,   62,   32,   41,   40,  264,   44,   60,   38,   62,
   58,   41,  123,   42,   43,   33,   45,   46,   47,   91,
  142,  143,   40,   42,   58,  263,  264,   46,   47,   41,
  152,  269,   93,   33,   41,   44,   91,   44,   91,   69,
   40,  268,   59,   58,   93,  123,   59,   58,  125,  263,
   58,   33,  264,   59,   84,   40,   40,   40,   40,   60,
   40,   59,   91,  264,   59,   41,   44,   41,   62,   33,
   59,  125,   91,  123,   40,  272,   40,  123,  123,    0,
   41,   41,  123,  125,   41,   41,   27,   33,   -1,  125,
   33,  101,   -1,   -1,   40,   33,   -1,   40,   -1,   -1,
   -1,   -1,   40,   -1,   -1,   -1,   -1,  125,   -1,   -1,
   -1,   -1,   -1,   59,   -1,   -1,   59,   -1,   -1,   -1,
  264,   -1,   -1,   -1,   -1,  125,   -1,   -1,   41,   42,
   43,   44,   45,   46,   47,   41,   42,   43,   44,   45,
   -1,   47,   -1,  125,   -1,   -1,   59,   60,   61,   62,
   -1,   -1,   -1,   59,   60,   61,   62,   -1,   -1,   41,
   -1,   -1,   44,   41,   42,   43,   44,   45,   -1,   47,
   41,   42,   43,   -1,   45,   46,   47,   59,   91,   61,
   93,   59,   60,   61,   62,   -1,   -1,   93,   -1,   60,
   -1,   62,  264,  265,  266,  267,  257,  258,  259,  260,
  261,  262,   -1,   -1,  257,  258,  259,  260,  261,  262,
  264,   93,   -1,   -1,  268,   93,  270,  271,   -1,  273,
   91,  275,  276,  277,  278,  279,  280,  281,  264,   -1,
   -1,   -1,  268,   -1,  270,  271,   -1,  273,   -1,  275,
  276,  277,  278,  279,  280,  281,  264,   -1,   -1,   -1,
  268,   -1,  270,  271,   -1,  273,   -1,  275,  276,  277,
  278,  279,  280,  281,  264,   -1,   -1,   -1,  268,   -1,
  270,  271,   -1,  273,   -1,  275,  276,  277,  278,  279,
  280,  281,  264,   -1,   -1,   -1,  268,   -1,  270,  271,
   -1,  273,   -1,  275,  276,  277,  278,  279,  280,  281,
  264,   -1,   -1,   -1,  268,   -1,  270,  271,   -1,  273,
   -1,  275,  276,  277,  278,  279,  280,  281,  264,   -1,
   -1,  264,  268,   -1,   -1,  268,  264,   -1,  274,   -1,
  268,   -1,   -1,  279,  280,  281,  279,  280,  281,   -1,
   -1,  279,  280,  281,  257,  258,  259,  260,  261,  262,
   -1,  257,  258,  259,  260,  261,  262,   41,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,   60,   -1,   62,  257,
  258,  259,  260,  261,  262,   -1,  257,  258,  259,  260,
  261,  262,   41,   42,   43,   -1,   45,   46,   47,   41,
   -1,   43,   44,   45,   -1,   -1,   41,   91,   43,   44,
   45,   60,   41,   62,   -1,   44,   -1,   59,   60,   61,
   62,   -1,   -1,   -1,   59,   60,   61,   62,   -1,   -1,
   59,   60,   61,   62,   41,   -1,   -1,   44,   -1,   -1,
   -1,   41,   91,   -1,   44,   -1,   -1,   41,   -1,   -1,
   44,   93,   59,   60,   61,   62,   -1,   -1,   93,   59,
   60,   61,   62,   -1,   93,   59,   60,   61,   62,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   -1,   93,   60,   61,   62,
   -1,   -1,   -1,   93,   59,   60,   -1,   62,   -1,   93,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   91,   59,
   60,   -1,   62,   -1,   -1,   -1,   91,   59,   60,   -1,
   62,   -1,   42,   43,   -1,   45,   46,   47,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   59,
   60,   91,   62,   -1,   -1,   59,   60,   -1,   62,   91,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,   -1,
   -1,   91,   -1,   -1,   -1,   42,   43,   91,   45,   46,
   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   60,   -1,   62,   -1,   -1,  257,  258,
  259,  260,  261,  262,   -1,  257,  258,  259,  260,  261,
  262,   -1,  257,  258,  259,  260,  261,  262,  257,  258,
  259,  260,  261,  262,   91,   -1,   93,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  257,  258,  259,  260,  261,  262,   -1,  257,  258,  259,
  260,  261,  262,  257,  258,  259,  260,  261,  262,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   41,   -1,   -1,   44,  257,  258,  259,  260,  261,  262,
   -1,   -1,  257,  258,  259,  260,  261,  262,   59,   -1,
   61,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,   -1,   -1,  257,  258,  259,  260,  261,
  262,   -1,   93,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,  257,  258,  259,  260,  261,  262,   42,
   43,   -1,   45,   46,   47,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   -1,   -1,   59,   60,   -1,   62,
   -1,   -1,   -1,   60,   61,   62,   -1,   -1,   -1,   -1,
   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,
  257,  258,  259,  260,  261,  262,   -1,   -1,   91,   60,
   61,   62,   42,   43,   91,   45,   46,   47,   42,   43,
   -1,   45,   46,   47,   42,   43,   -1,   45,   46,   47,
   60,   -1,   62,   -1,   41,   -1,   60,   44,   62,   -1,
   91,   -1,   60,   -1,   62,   42,   43,   -1,   45,   46,
   47,   -1,   59,   -1,   61,   -1,   41,   -1,   -1,   44,
   -1,   91,   -1,   60,   -1,   62,   -1,   91,   -1,   -1,
   -1,   -1,   -1,   91,   59,   -1,   61,   -1,   -1,   -1,
   -1,   53,   54,   -1,   -1,   -1,   93,   59,   60,   61,
   62,   63,   -1,   -1,   91,   -1,  257,  258,  259,  260,
   -1,   73,   74,   75,   -1,   -1,   -1,   -1,   93,   -1,
   -1,   -1,   -1,   85,   86,   87,   88,   89,   90,   91,
   92,   93,   94,   95,   96,   97,   -1,   99,   -1,  101,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  133,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  144,   -1,  257,  258,  259,  260,  261,  262,
  257,  258,  259,  260,  261,  262,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,
  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,   -1,  258,  259,  260,  261,  262,   -1,
   -1,  259,  260,  261,  262,   -1,   -1,   -1,   -1,   -1,
  257,  258,  259,  260,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  261,  262,   -1,   -1,   -1,   -1,
   -1,   -1,  257,  258,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=281;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,null,null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"\"OR\"","\"AND\"","\"IGUAL\"",
"\"DISTINTO\"","\"MENORIGUAL\"","\"MAYORIGUAL\"","\"VAR\"","\"IDENT\"",
"\"INT\"","\"REAL\"","\"CHARACTER\"","\"LITERALINT\"","\"STRUCT\"","\"WHILE\"",
"\"IF\"","\"ELSE\"","\"RETURN\"","\"NULL\"","\"READ\"","\"PRINT\"",
"\"PRINTSP\"","\"PRINTLN\"","\"CAST\"","\"LITERALREAL\"","\"LITERALCHAR\"",
};
final static String yyrule[] = {
"$accept : programa",
"programa : definiciones",
"definiciones :",
"definiciones : definiciones definicion",
"definicion : definicionVariable",
"definicion : definicionFuncion",
"definicion : definicionEstructura",
"definicionVariable : \"VAR\" \"IDENT\" ':' tipo ';'",
"definicionVariableLocal : \"VAR\" \"IDENT\" ':' tipo ';'",
"tipo : \"INT\"",
"tipo : \"REAL\"",
"tipo : \"CHARACTER\"",
"tipo : \"IDENT\"",
"tipo : '[' \"LITERALINT\" ']' tipo",
"definicionEstructura : \"STRUCT\" \"IDENT\" '{' definicionCampos '}' ';'",
"definicionCampos :",
"definicionCampos : definicionCampos definicionCampo",
"definicionCampo : \"IDENT\" ':' tipo ';'",
"definicionFuncion : \"IDENT\" '(' parametrosFuncion ')' retorno '{' cuerpo '}'",
"retorno :",
"retorno : ':' tipo",
"parametrosFuncion :",
"parametrosFuncion : parametroFuncion",
"parametroFuncion : definicionparametroFuncion",
"parametroFuncion : parametroFuncion ',' definicionparametroFuncion",
"definicionparametroFuncion : \"IDENT\" ':' tipo",
"cuerpo : definicionVariablesFuncion sentencias",
"definicionVariablesFuncion :",
"definicionVariablesFuncion : definicionVariablesFuncion definicionVariableLocal",
"sentencias :",
"sentencias : sentencias sentencia",
"sentencia : \"WHILE\" '(' expresion ')' '{' sentencias '}'",
"sentencia : \"IF\" '(' expresion ')' '{' sentencias '}'",
"sentencia : \"IF\" '(' expresion ')' '{' sentencias '}' \"ELSE\" '{' sentencias '}'",
"sentencia : \"RETURN\" ';'",
"sentencia : \"RETURN\" \"NULL\" ';'",
"sentencia : \"RETURN\" expresion ';'",
"sentencia : \"READ\" expresion ';'",
"sentencia : \"PRINT\" expresion ';'",
"sentencia : \"PRINTSP\" expresion ';'",
"sentencia : \"PRINTLN\" expresion ';'",
"sentencia : \"PRINTLN\" ';'",
"sentencia : \"IDENT\" '(' argumentosLlamadaFuncion ')' ';'",
"sentencia : expresion '=' expresion ';'",
"expresion : '(' expresion ')'",
"expresion : expresion '+' expresion",
"expresion : expresion '-' expresion",
"expresion : expresion '*' expresion",
"expresion : expresion '/' expresion",
"expresion : expresion '>' expresion",
"expresion : expresion '<' expresion",
"expresion : expresion \"IGUAL\" expresion",
"expresion : expresion \"AND\" expresion",
"expresion : expresion \"OR\" expresion",
"expresion : expresion \"MAYORIGUAL\" expresion",
"expresion : expresion \"DISTINTO\" expresion",
"expresion : expresion \"MENORIGUAL\" expresion",
"expresion : \"CAST\" '<' tipo '>' '(' expresion ')'",
"expresion : \"LITERALINT\"",
"expresion : \"LITERALREAL\"",
"expresion : \"LITERALCHAR\"",
"expresion : \"IDENT\"",
"expresion : \"IDENT\" '(' argumentosLlamadaFuncion ')'",
"expresion : expresion '[' expresion ']'",
"expresion : expresion '.' \"IDENT\"",
"expresion : '!' expresion",
"argumentosLlamadaFuncion :",
"argumentosLlamadaFuncion : argumentoLlamadaFuncion",
"argumentoLlamadaFuncion : expresion",
"argumentoLlamadaFuncion : argumentoLlamadaFuncion ',' expresion",
};

//#line 158 "sintac.y"
/* No es necesario modificar esta secci�n ------------------ */

public Parser(Yylex lex, GestorErrores gestor, boolean debug) {
	this(debug);
	this.lex = lex;
	this.gestor = gestor;
}

// M�todos de acceso para el main -------------
public int parse() { return yyparse(); }
public AST getAST() { return raiz; }

// Funciones requeridas por Yacc --------------
void yyerror(String msg) {
	Token lastToken = (Token) yylval;
	gestor.error("Sint�ctico", "Token = " + lastToken.getToken() + ", lexema = \"" + lastToken.getLexeme() + "\". " + msg, lastToken.getStart());
}

int yylex() {
	try {
		int token = lex.yylex();
		yylval = new Token(token, lex.lexeme(), lex.line(), lex.column());
		return token;
	} catch (IOException e) {
		return -1;
	}
}

private Yylex lex;
private GestorErrores gestor;
private AST raiz;
//#line 562 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 27 "sintac.y"
{ raiz = new Programa(val_peek(0)); }
break;
case 2:
//#line 30 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 3:
//#line 31 "sintac.y"
{ yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0)); }
break;
case 4:
//#line 34 "sintac.y"
{ yyval = val_peek(0); }
break;
case 5:
//#line 35 "sintac.y"
{ yyval = val_peek(0); }
break;
case 6:
//#line 36 "sintac.y"
{ yyval = val_peek(0); }
break;
case 7:
//#line 40 "sintac.y"
{ yyval = new DefVariable(val_peek(3),val_peek(1),true); }
break;
case 8:
//#line 43 "sintac.y"
{ yyval = new DefVariable(val_peek(3),val_peek(1),false); }
break;
case 9:
//#line 47 "sintac.y"
{ yyval = new IntType(); }
break;
case 10:
//#line 48 "sintac.y"
{ yyval = new RealType(); }
break;
case 11:
//#line 49 "sintac.y"
{ yyval = new CharType(); }
break;
case 12:
//#line 50 "sintac.y"
{ yyval = new IdentType(val_peek(0)); }
break;
case 13:
//#line 51 "sintac.y"
{ yyval = new ArrayType(new LiteralInt(val_peek(2)), val_peek(0)); }
break;
case 14:
//#line 55 "sintac.y"
{ yyval = new DefEstructura(val_peek(4), val_peek(2)); }
break;
case 15:
//#line 59 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 16:
//#line 60 "sintac.y"
{ yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0)); }
break;
case 17:
//#line 63 "sintac.y"
{ yyval = new DefCampo(val_peek(3), val_peek(1)); }
break;
case 18:
//#line 67 "sintac.y"
{ yyval = new DefFuncion(val_peek(7), val_peek(5), val_peek(3), val_peek(1)); }
break;
case 19:
//#line 71 "sintac.y"
{ yyval = new Retorno(null); }
break;
case 20:
//#line 72 "sintac.y"
{ yyval = new Retorno(val_peek(0)); }
break;
case 21:
//#line 76 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 22:
//#line 77 "sintac.y"
{ yyval = val_peek(0); }
break;
case 23:
//#line 81 "sintac.y"
{yyval = new ArrayList(); ((List)yyval).add(val_peek(0));}
break;
case 24:
//#line 82 "sintac.y"
{ yyval = val_peek(2); ((List)val_peek(2)).add(val_peek(0)); }
break;
case 25:
//#line 86 "sintac.y"
{ yyval = new DefParametro(val_peek(2), val_peek(0)); }
break;
case 26:
//#line 90 "sintac.y"
{ yyval = new Cuerpo(val_peek(1), val_peek(0)); }
break;
case 27:
//#line 94 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 28:
//#line 95 "sintac.y"
{ yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0)); }
break;
case 29:
//#line 99 "sintac.y"
{ yyval = new ArrayList();}
break;
case 30:
//#line 100 "sintac.y"
{ yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0)); }
break;
case 31:
//#line 104 "sintac.y"
{ yyval = new While(val_peek(4), val_peek(1)); }
break;
case 32:
//#line 105 "sintac.y"
{ yyval = new If(val_peek(4), val_peek(1)); }
break;
case 33:
//#line 106 "sintac.y"
{ yyval = new IfElse(val_peek(8), val_peek(5), val_peek(1)); }
break;
case 34:
//#line 107 "sintac.y"
{ yyval = new Return(null).setPositions(val_peek(1)); }
break;
case 35:
//#line 108 "sintac.y"
{ yyval = new Return(null).setPositions(val_peek(2)); }
break;
case 36:
//#line 109 "sintac.y"
{ yyval = new Return(val_peek(1)); }
break;
case 37:
//#line 110 "sintac.y"
{ yyval = new Read(val_peek(1)); }
break;
case 38:
//#line 111 "sintac.y"
{ yyval = new Print(val_peek(1)); }
break;
case 39:
//#line 112 "sintac.y"
{ yyval = new Printsp(val_peek(1)); }
break;
case 40:
//#line 113 "sintac.y"
{ yyval = new Println(val_peek(1)); }
break;
case 41:
//#line 114 "sintac.y"
{ yyval = new Println(null).setPositions(val_peek(1)); }
break;
case 42:
//#line 115 "sintac.y"
{ yyval = new LlamadaFuncionSentencia(val_peek(4), val_peek(2));}
break;
case 43:
//#line 116 "sintac.y"
{ yyval = new Asigna(val_peek(3), val_peek(1)); }
break;
case 44:
//#line 120 "sintac.y"
{ yyval = val_peek(1); }
break;
case 45:
//#line 121 "sintac.y"
{ yyval = new ExpresionAritmetica(val_peek(2), "+", val_peek(0)); }
break;
case 46:
//#line 122 "sintac.y"
{ yyval = new ExpresionAritmetica(val_peek(2), "-", val_peek(0)); }
break;
case 47:
//#line 123 "sintac.y"
{ yyval = new ExpresionAritmetica(val_peek(2), "*", val_peek(0)); }
break;
case 48:
//#line 124 "sintac.y"
{ yyval = new ExpresionAritmetica(val_peek(2), "/", val_peek(0)); }
break;
case 49:
//#line 125 "sintac.y"
{ yyval = new ExpresionBooleana(val_peek(2), ">", val_peek(0)); }
break;
case 50:
//#line 126 "sintac.y"
{ yyval = new ExpresionBooleana(val_peek(2), "<", val_peek(0)); }
break;
case 51:
//#line 127 "sintac.y"
{ yyval = new ExpresionBooleana(val_peek(2), "IGUAL", val_peek(0)); }
break;
case 52:
//#line 128 "sintac.y"
{ yyval = new ExpresionBooleana(val_peek(2), "AND", val_peek(0)); }
break;
case 53:
//#line 129 "sintac.y"
{ yyval = new ExpresionBooleana(val_peek(2), "OR", val_peek(0)); }
break;
case 54:
//#line 130 "sintac.y"
{ yyval = new ExpresionBooleana(val_peek(2), "MAYORIGUAL", val_peek(0)); }
break;
case 55:
//#line 131 "sintac.y"
{ yyval = new ExpresionBooleana(val_peek(2), "DISTINTO", val_peek(0)); }
break;
case 56:
//#line 132 "sintac.y"
{ yyval = new ExpresionBooleana(val_peek(2), "MENORIGUAL", val_peek(0)); }
break;
case 57:
//#line 133 "sintac.y"
{ yyval = new Cast(val_peek(4), val_peek(1)); }
break;
case 58:
//#line 134 "sintac.y"
{ yyval = new LiteralInt(val_peek(0)); }
break;
case 59:
//#line 135 "sintac.y"
{ yyval = new LiteralReal(val_peek(0)); }
break;
case 60:
//#line 136 "sintac.y"
{ yyval = new LiteralChar(val_peek(0)); }
break;
case 61:
//#line 137 "sintac.y"
{ yyval = new Variable(val_peek(0)); }
break;
case 62:
//#line 138 "sintac.y"
{ yyval = new Invocacion(val_peek(3), val_peek(1)); }
break;
case 63:
//#line 139 "sintac.y"
{ yyval = new VarArray(val_peek(3), val_peek(1)); }
break;
case 64:
//#line 140 "sintac.y"
{ yyval = new Navega(val_peek(2), val_peek(0)); }
break;
case 65:
//#line 141 "sintac.y"
{ yyval = new Negacion(val_peek(0)); }
break;
case 66:
//#line 145 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 67:
//#line 146 "sintac.y"
{ yyval = val_peek(0); }
break;
case 68:
//#line 149 "sintac.y"
{yyval = new ArrayList(); ((List)yyval).add(val_peek(0));}
break;
case 69:
//#line 150 "sintac.y"
{ yyval = val_peek(2); ((List)val_peek(2)).add(val_peek(0)); }
break;
//#line 986 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
