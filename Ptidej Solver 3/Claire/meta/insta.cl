//+-------------------------------------------------------------+
//| CLAIRE                                                      |
//| insta.cl                                                    |
//| Copyright (C) 1994 - 2000 Yves Caseau. All Rights Reserved  |
//| cf. copyright info in file object.cl: about()               |
//+-------------------------------------------------------------+

// ----------------------------------------------------------
// This is the file which defines the instantiation (the creation
// of new objects: classes and restrictions). It contains the
// key methods for storing the class hierarchy and building the
// definitions of functions & properties
// ----------------------------------------------------------

// *****************************************************************
// *  Contents:                                                    *
// *    Part 1: The basic instantiation methods                    *
// *    Part 2: create restrictions                                *
// *    Part 3: Lattice operations                                 *
// *    Part 4: Management of definition(p)                        *
// *    Part 5: Properties and Operations                          *
// *****************************************************************

@ :: operation(open = 1)

// *****************************************************************
// *    Part 1: The basic instantiation methods                    *
// *****************************************************************

// the minimal instantiation of an un_named object
new(self:class) : object
 -> (if (self.open <= 0) error("cannot instantiate ~S", self),
     let ob := make_object(self) in
       (if (self.open != 3) add!(instances, self, ob),
        complete!(ob) as object))

// ... with a name
new(self:class,%nom:symbol) : thing
 -> (if (self.open <= 0 | not(self inherit? thing))
        error("cannot create a named instance for ~S", self),
     if (get(%nom) % object)
       let %other := (get(%nom) as thing) in
          (if (%other.isa = self) %other
           else error("**** An object with same name ~A already exists\n",
                      %nom))
     else let ob := (make_object(self) as thing) in
       (put(name, ob, %nom),
        put(%nom, ob),
        if (self.open != 3) add!(instances, self, ob),
        complete!(ob) as thing))


// simpler version for the compiler (no checks)
instantiate! :: property()

instantiate!(self:class,%nom:symbol) : thing
 -> (if (get(%nom) % object)
        let %other := (get(%nom) as thing) in
          (if (%other.isa = self) %other
           else error("**** An object with same name ~A already exists\n",
                      %nom))
     else let ob := (make_object(self) as thing) in
       (put(name, ob, %nom),
        put(%nom, ob),
        if (self.open != 3) add!(instances, self, ob),
        ob))


// the instantiation body is a sequence of words from which the initialization
// of the object must be built.
complete!(self:object) : any
 -> (for s:slot in self.isa.slots
       let p := s.selector,
           s2 := s.srange,
           d := get(default, s) in
         (if known?(d)
             let v := slot_get(self, s.index, s.srange) in
               (if (s2 != any & s2 != integer & unknown?(v))
                   put(p, self, s.index, s.srange, d)
                else if (d = v)
                   (if p.multivalued? for y in d update+(p, self, y)
                    else update+(p, self, d)))),
     let m := (close @ owner(self)) in
       (case m (method funcall(m, self)), self))

// declares a class as ephemeral: the member set is not maintained
//
ephemeral(self:class) : any -> put(open, self, 3)

// declares a class as an abtract class (without instances)
abstract(c:class) : any
 -> (let n := c.open in
       (if (n = 3) error("ephemeral classes cannot be abstract")
        else (if c.instances
                 trace(3, "--- note: ~S already has some instances", c),
              if (n = 2) write(open, c, 0)
              else if (n = 1) write(open, c, -1))))

// declares a class as totally defined in the hierarchy: no new subclasses
// can be added.
final(c:class) : any
 -> (let n := c.open in
       (if (n = 3) error("ephemeral classes cannot be set as final")
        else if (n = 2) write(open, c, 1)
        else if (n = 0) write(open, c, -1)))


// *****************************************************************
// *    Part 2: create restrictions                                *
// *****************************************************************
// This method builds the "genealogy" and check for split delegation.
//
genealogy(self:class) : any
 -> (put(descendents, self, {} add self),
     let x := self.superclass in
       (for y in x.ancestors add!(descendents, y, self),
        add!(subclass, x, self),
        put(ancestors, self, (copy(x.ancestors) as list) add self)))

// how to create a new slot
//
add_slot(c:class,p:property,%type:any,%default:any) : slot
 -> (let s:slot := make_object(slot),
         s1:class := sort!(%type),
         c1 := class!(%type) in
       (add!(instances, slot, s),
        put(domain, s, list(c)),
        put(selector, s, p),
        if (c1 % {list, set})
           (if not(p.restrictions)  p.multivalued? := (if (c1 = set) true else list),
            if (%default = unknown)
               %default := (if (p.multivalued? = true) {} else nil))
        else if (%type = float & %default = unknown) %default := 0.0,
        if ((p.multivalued? = true & c1 != set) | (p.multivalued? = list & c1 != list))
            error("multivalued?(~S) is not consistent with range(~S)",p,s),
        add!(restrictions, p, s),
        put(slots, c, c.slots add s),
        put(prototype, c,
            c.prototype add (if (s1 = any | s1 = integer) %default else 0)),
        write(index, s, length(c.slots)),
        write(module!, s, current_module()),
        write(default, s, %default),
        put(range, s, %type),
        put(srange, s, s1),
        insert_definition(p, s),
        s))

// how to create a new method
//
add_method(p:property,%dom:list,%ran:any,%s:any,%f:any) : method
 -> (let m:method := make_object(method) in
       (add!(instances, method, m),
        put(selector, m, p),
        add!(restrictions, p, m),
        write(domain, m, %dom),
        write(range, m, %ran),
        write(module!, m, current_module()),
        put(functional, m, %f),
        put(status, m, %s),
        put(srange, m,
            list{ sort!(class!(%t)) | %t in %dom} add sort!(class!(%ran))),
        insert_definition(p, m),
        m))

// insertion in the definition tree
//
insert_definition(p:property,r:restriction) : any
 -> put(definition, p, initialize(r, class!(r.domain[1]), p.definition))

// used by the compiler
//
class!(self:symbol,c2:class) : class
 -> (let c:class := instantiate!(class, self),
         n := c2.open in
       (put(superclass, c, c2),
        put(slots, c, copy(c2.slots)),
        if (n = 3) write(open, c, 3)
        else if (n = 1 | n < 0)
           error("cannot create a subclass of ~S", c2),
        if (c != slot) put(prototype, c, copy(c2.prototype)),
        genealogy(c),
        if (get(evaluate, c) = unknown) put(evaluate, c, class.evaluate),
        if (c.code = 0) encode(c),
        write(dictionary, c, copy(c2.dictionary)),
        c))

// *****************************************************************
// *    Part 3: Lattice operations                                 *
// *****************************************************************

% :: operation()
meet :: operation()
inherit? :: operation()

// the membership membership for lattice_sets
//
%(self:any,ens:class) : boolean -> (ens % owner(self).ancestors)

// the smallest super_set of two sets
// there is always any, so it always returns a class
//
meet(self:class,ens:class) : class
 -> (let l1 := self.ancestors,
         l2 := ens.ancestors,
         m := (if (length(l1) < length(l2)) length(l1) else length(l2)) in
       (while (l1[m] != l2[m]) m :- 1, l1[m] as class))

// fast inclusion method for lattice_sets (lattice order). The argument is
// either a lattice_set or {}
//
inherit?(self:class,ens:class) : boolean
 -> (let l := self.ancestors,
         n := (ens.ancestors[0] as integer) in
       (n <= (l[0] as integer) & l[n] = ens))

// gives the type of any object. This is open_coded.
//
owner(self:any) : class -> owner(self)

// some useful methods
//
known?(self:any) : boolean -> (unknown != self)
unknown?(self:any) : boolean -> (unknown = self)

// _________________ membership in CLAIRE    _________________________
// this is the most basic membership method: finding if a given
// object (any kind) belongs to a given set (any kind).
// since it is important, it is defined by an optimized C function.
//
%(self:any,x:any) : boolean -> function!(belong_to)

// needed by the compiled code
//
check_in(self:any,y:any) : any
 -> (if (self % y) self
     else error("the value ~S does not belong to the range ~S", self, y))

// two utility functions
nbits(n:integer) : integer
 -> (if (n < 4) n else if (n < 7) 4 else if (n < 11) 5 else if (n < 21) 6
     else if (n < 36) 7 else error("There is a node with more than 35 children !"))

totalbits(c:class) : integer
 -> (let n := 0 in
       (for c2 in (c.ancestors but c) n :+ nbits(length(c2.subclass)), n))

// incremental encoding
encode(c:class) : any
 -> (let c2 := c.superclass,
         n := totalbits(c2),
         i := length(c2.subclass),
         m := nbits(i) in
       (if (c = void) nil
        else if (m = nbits(i - 1))
           write(code, c, c2.code + System/make_code(n, i, m))
        else recode(c2, n)))

// new partial encoding
recode(c:class,n:integer) : any
 -> (let i := 1,
         m := nbits(length(c.subclass)) in
       for c2 in c.subclass (nodecode(c2, c.code, n, m, i), i :+ 1))

nodecode(c:class,cx:integer,n:integer,m:integer,i:integer) : any
 -> (if ((n + m) > 29) error("Class hierarchy too large (use -DLARGE)"),
     write(code, c, cx + System/make_code(n, i, m)),
     if c.subclass recode(c, n + m))

code!(c:class) : any
 -> (for i in (0 .. 29) (if c.code[i] princ(1) else princ(0)))

// *********************************************************************
// *   Part 4: Management of definition(p)                             *
// *********************************************************************
join :: operation()

// insert a restriction with class-domain d into a property p
//
initialize(x:restriction,d:class,l:list) : list
 -> (let p := x.selector in
       (if (length(p.restrictions) = 5 & uniform(p))
           (for r in p.restrictions hashinsert(r),
            write(dictionary, p, true)),
        if p.dictionary
           (if uniform(x) hashinsert(x) else write(dictionary, p, false)),
        initialize(x, l)))

// only uniform properties can use the dictionary representation
uniform(x:restriction) : boolean
 -> (let l := x.domain,
         n := length(l) in
       forall(r in x.selector.restrictions |
         let l2 := r.domain in
           (l[1] % class & length(l2) = n &
            (forall(i in (2 .. n) | l[i] = l2[i])))))
uniform(p:property) : boolean -> uniform(p.restrictions[1])

// insert a restriction in a list with the good order
//
initialize(x:restriction,l:list) : list
 -> (let l1:list := nil in
       (for i in (1 .. length(l))
          let l2 := (l[i] as restriction).domain in
            (if tmatch?(x.domain, l2)
                (if tmatch?(l2, x.domain)
                    (l[i] := x, l1 := l, break(true))
                 else (l1 := nth+(l, i, x), break(true)))
             else if (not(tmatch?(l2, x.domain)) & x.domain join l2)
                trace(2, ("~S and ~S are conflicting"), l[1], x)),
        if l1 l1
        else l add x))

// definition of dictionary: standart hash-table
//
hashinsert(m:restriction) : any
 -> (let c := (domain!(m) as class) in
       for c2 in c.descendents hashinsert(c2, (m as method)))

hashinsert(c:class,x:method) : any
 -> (if not(c.dictionary) write(dictionary, c, hashlist(29)),
     hashinsert(c.dictionary, x))

hashinsert(l:list,x:method) : any
 -> (let p := x.selector,
         i := hash(l, p),
         m := length(l) in
       while true
         (if (l[i] = unknown | (l[i] as method).selector = p)
             (if (l[i] = unknown | domain!(x) <= domain!((l[i] as method)))
                 l[i] := x,
              break(l))
          else if (i = m)
             (if ((hashsize(l) * 3) > (length(l) * 2))
                 let myl2 := hashgrow(l, hashinsert) in
                   break(hashinsert(myl2, x))
              else i := 1)
          else i :+ 1))

hashget(c:class,p:property) : any
 -> (let l := c.dictionary,
         i := hash(l, p),
         m := length(l) in
       while true
         (if (l[i] = unknown) break(nil)
          else if ((l[i] as method).selector = p) break(l[i])
          else if (i = m) i := 1
          else i :+ 1))

// look if two signature have a non-empty intersection
//
join(x:list,y:list) : boolean
 -> (let n := length(x) in
       (n = length(y) &
        not((for i in (1 .. n)
               (if not(class!(x[i]) join class!(y[i])) break(true)))) &
        not((for i in (1 .. n) (if not(x[i] ^ y[i]) break(true))))))

// ---------------- RESTRICTION MATCHING ---------------------------
// find the correct restrictions to be applied on a given set
// This is also optimized because it is very useful
//
@(self:property,x:class) : any
 -> (if self.dictionary hashget(x, self)
     else let rx := some(r in self.definition | (x inherit? class!(r.domain[1]))) in
       (if known?(rx) rx else nil))


// finds a property through its full domain
//
@(self:property,lt:list) : any
 ->  let rx := some(r in self.definition | tmatch?(lt, r.domain)) in
       (if known?(rx) rx else nil)

// *********************************************************************
// *   Part 5:  Properties & operations                                *
// *********************************************************************


// we find here what is necessary for the minimal kernel of CLAIRE
// ==============================================================
main :: property()
size :: property(range = integer)
end_of_string :: property()
apply :: property()
extract_restriction :: property(open = 3)
show_value :: property()
test :: property()
set_of :: property()
on :: property()
show :: property()
finite? :: property()
source :: property()
loaded :: property()
objectname? :: property(open = 1)
defined_by :: property()
call_step :: property(open = 3)
spy :: property(open = 3)
= :: operation(precedence = 60)
!= :: operation(precedence = 60)
< :: operation(precedence = 60)
> :: operation(precedence = 60)
<= :: operation(precedence = 60)
>= :: operation(precedence = 60)
<< :: operation()
>> :: operation()
and :: operation()
or :: operation()
cons :: operation()
/+ :: operation()
add* :: operation()
+ :: operation(precedence = 20)
- :: operation(precedence = 20)
/ :: operation()
* :: operation()
^ :: operation(precedence = 9)
U :: operation(precedence = 50)
mod :: operation()
less? :: operation(precedence = 60)
.. :: operation(precedence = 30)
& :: operation()
min :: operation(precedence = 20)
max :: operation(precedence = 20)


// end of file
