// --- System configuration file for "ice" , ["Thu Feb 13 21:08:23 2003\n"] ---

#include <claire.h>
#include <Kernel.h>
#include <Core.h>
#include <Language.h>
#include <Reader.h>
#include <choco.h>
#include <ice.h>



void loadModules() 
{ //module definitions 
  Core.initModule("Core",mClaire.it,list::alloc(Kernel.emptySet,1,_oid_(Kernel.it)),
  "d:\\claire\\v3.2\\src\\meta",list::alloc(Kernel.emptySet,4,_string_("method"),
    _string_("object"),
    _string_("function"),
    _string_("types")));
  iClaire.initModule("iClaire",claire.it,list::alloc(Kernel.emptySet,1,_oid_(mClaire.it)),
  "",Kernel.nil);
  Language.initModule("Language",iClaire.it,list::alloc(Kernel.emptySet,2,_oid_(Kernel.it),_oid_(Core.it)),
  "d:\\claire\\v3.2\\src\\meta",list::alloc(Kernel.emptySet,4,_string_("pretty"),
    _string_("call"),
    _string_("control"),
    _string_("define")));
  Reader.initModule("Reader",iClaire.it,list::alloc(Kernel.emptySet,3,_oid_(Kernel.it),
    _oid_(Core.it),
    _oid_(Language.it)),
  "d:\\claire\\v3.2\\src\\meta",list::alloc(Kernel.emptySet,4,_string_("read"),
    _string_("syntax"),
    _string_("file"),
    _string_("inspect")));
  choco.initModule("choco",claire.it,list::alloc(Kernel._module,2,_oid_(Core.it),_oid_(Reader.it)),
  "C:\\Docume~1\\Yann\\Work\\Ptidej~2\\Choco\\v1.324",list::alloc(Kernel._string,14,_string_("chocutils"),
    _string_("model"),
    _string_("dom"),
    _string_("prop"),
    _string_("var"),
    _string_("const"),
    _string_("intconst1"),
    _string_("intconst2"),
    _string_("boolconst"),
    _string_("setconst"),
    _string_("search"),
    _string_("chocapi"),
    _string_("opapi"),
    _string_("compil")));
  ice.initModule("ice",choco.it,list::alloc(Kernel._module,3,_oid_(choco.it),
    _oid_(Core.it),
    _oid_(Reader.it)),
  "C:\\Docume~1\\Yann\\Work\\Ptidej~2\\Iceberg\\v0.95",list::alloc(Kernel._string,2,_string_("wcsp"),_string_("matching")));
  //module load 
  Core.metaLoad();
  Language.metaLoad();
  Reader.metaLoad();
  choco.metaLoad();
  ice.metaLoad();
  ClEnv->module_I = ice.it; 
  } 

void call_main() {default_main();}
