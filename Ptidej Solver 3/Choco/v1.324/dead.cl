choco/VarEvent <: PropagationEvent(
    choco/modifiedVar:AbstractVar,            // each event concerns a unique IntVar/SetVar of the problem
    nextConst:list<integer> = list<integer>()) // index pointers coding a chained sublist of v.constraints
                                               // those constraints that should be waken upon handling the event
choco/Instantiation <: VarEvent(
    choco/cause:integer = 0)  // index of the constraint that caused the event

choco/BoundUpdate <: VarEvent(
    choco/cause:integer = 0,      // index of the constraint that caused the event
    choco/idxInQueue:integer = 0) // index in the queue of all pending events

choco/IncInf <: BoundUpdate(
    choco/modifiedVar:IntVar)

choco/DecSup <: BoundUpdate(
    choco/modifiedVar:IntVar)

choco/IncKer <: BoundUpdate(
    choco/modifiedVar:SetVar)
[self_print(e:IncKer) : void
 -> printf("KER(~S):~S[c:~S][i:~S]",e.modifiedVar,getKernel(e.modifiedVar),e.cause,e.idxInQueue)]

choco/DecEnv <: BoundUpdate(
    choco/modifiedVar:SetVar)
[self_print(e:DecEnv) : void
 -> printf("ENV(~S):~S[c:~S][i:~S]",e.modifiedVar,getEnveloppe(e.modifiedVar),e.cause,e.idxInQueue)]

choco/InstInt <: Instantiation(
    choco/modifiedVar:IntVar)

choco/InstSet <: Instantiation(
    choco/modifiedVar:IntVar)
[self_print(e:InstSet) : void
 -> let v := e.modifiedVar in printf("INST SET(~S):~S[c:~S]",v,getKernel(v),e.cause)]

