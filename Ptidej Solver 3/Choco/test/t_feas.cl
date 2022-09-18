// Non regression test on feasTestConstraint
// (bug reported by Michel Lemaitre on v0.37)

[fOrder(is:list[integer]) : boolean -> 
  if (length(is) != 3) error("fOrder should only be called on list of length 3"), 
                      //in awake@CSPLargeConstraint add is destructive -> use copy()
  if (is[1] < is[2]) (is[3] = 1) else (is[3] = 0)]

[cOrder(va:choco/IntVar, vb:choco/IntVar, vc:choco/IntVar) : choco/AbstractConstraint ->
  choco/feasTestConstraint(list(va, vb, vc), fOrder @ list)]

[testFeas()
 -> //[CHOCOTEST_VIEW] testing n-ary constraints given by a feasibility function on tuples,
    let pb := choco/makeProblem("bug", 10),
        x := choco/makeIntVar(pb, "x", 0, 0),
        y := choco/makeIntVar(pb, "y", 0, 1),
        z := choco/makeIntVar(pb, "z", 1, 1) in
     (choco/post(pb, cOrder(x, y, z)),
      try choco/propagate(pb) //choco/doAwake(x.constraints[1])
      catch contradiction
            error("No contradiction should occur"),
      "OK" )] // (o:1, xp1:0, xp2:1, xo:1, xb:0, xm:1) is a solution

