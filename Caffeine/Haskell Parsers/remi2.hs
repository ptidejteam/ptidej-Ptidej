
-- monadic parser library

type Parser a = Trace -> [(a,Trace)]

returnP :: a -> Parser a
returnP x = \t -> [(x,t)]

failP :: Parser a
failP = \t -> []

thenP :: Parser a -> (a -> Parser b) -> Parser b
p `thenP` q = \t -> concat (map (\(x,t') -> q x t') (p t))

orP :: Parser a -> Parser a -> Parser a
p `orP` q = \t -> (p t) ++ (q t)

orP' :: Parser a -> (a -> Parser a) -> Parser a
p `orP'` q = \t -> (p t) ++ (concat (map (\(x,_) -> q x t) (p t)))

itemP :: Parser Event
itemP = \t -> case t of
              []         -> []
              (evt:evts) -> [(evt,evts)]

itemP' :: Parser Event
itemP' = \t -> case t of
              []         -> []
              (evt:evts) -> (evt,evts):(itemP' evts)

firstP :: Parser a -> Parser a
firstP p = \t -> case (p t) of
                 []    -> []
                 (x:_) -> [x]

manyP :: Parser a -> Parser [a]
manyP p = (p                `thenP` (\x  -> 
           (manyP p)        `thenP` (\xs ->
           returnP (x:xs)))) 
          `orP` 
          (returnP [])

manyP' :: Parser a -> Parser [a]
manyP' p = firstP (manyP p)

satP :: (a -> Bool) -> Parser a -> Parser a
satP f p = p `thenP` (\x -> if (f x) then returnP x else failP)


-- java events

type Class = String

type Reference = Int

type Instance = (Class,Reference)

data Event =
	  AssignField Int Instance Instance
	| Finalize    Int Instance
	deriving (Eq, Show)

type Trace = [Event]

trace1 :: Trace 
trace1 = [
	AssignField 1 ("A",1) ("B",2), 
	AssignField 2 ("A",1) ("B",3), 
	AssignField 3 ("A",1) ("B",4), 
	AssignField 4 ("A",1) ("B",2), 
	Finalize    5 ("B",2), 
	Finalize    6 ("A",1)
	]

trace2 :: Trace 
trace2 = [
	AssignField 1  ("A",1) ("B",2), 
	AssignField 2  ("A",5) ("B",7), 
	AssignField 3  ("A",1) ("B",3), 
	AssignField 4  ("A",1) ("B",4), 
	AssignField 5  ("A",1) ("B",2), 
	Finalize    6  ("B",2), 
	Finalize    7  ("A",1),
	AssignField 8  ("A",5) ("B",6), 
	Finalize    9  ("B",6), 
	Finalize    10 ("A",5)
	]

trace3 :: Trace 
trace3 = [
	AssignField 1  ("A",1) ("B",2), 
	AssignField 2  ("A",5) ("B",7), 
	AssignField 3  ("A",1) ("B",3), 
	AssignField 4  ("A",1) ("B",4), 
	AssignField 5  ("A",1) ("B",2), 
	Finalize    6  ("B",2), 
	Finalize    7  ("A",1),
	AssignField 8  ("A",5) ("B",6), 
	Finalize    9  ("A",5),
	Finalize    10 ("B",6) 
	]

nextAssignField :: [Instance] -> Parser Event
nextAssignField is = firstP (satP (isAssignField is) itemP')

isAssignField :: [Instance] -> Event -> Bool
isAssignField is (AssignField _ i _) = notElem i is
isAssignField is _                   = False

nextAssignFieldInstance :: Instance -> Parser Event
nextAssignFieldInstance i = firstP (satP (isAssignFieldInstance i) itemP')

isAssignFieldInstance :: Instance -> Event -> Bool
isAssignFieldInstance i (AssignField _ i' _) = i==i'
isAssignFieldInstance _ _                    = False

nextAssignFieldInstances :: Instance -> Parser [Event]
nextAssignFieldInstances i = manyP' (nextAssignFieldInstance i)

nextFinalizeInstance :: Instance -> Parser Event
nextFinalizeInstance i = firstP (satP (isFinalizeInstance i) itemP')

isFinalizeInstance :: Instance -> Event -> Bool
isFinalizeInstance i (Finalize _ i') = i==i'
isFinalizeInstance _ _               = False

assoc :: [Instance] -> Parser [Event]
assoc is =
	nextAssignField is `thenP` (\e1 ->
	nextAssignFieldInstances (getLeftHandSide e1) `thenP` (\e2s ->  
	nextFinalizeInstance (getRightHandSide (last (e1:e2s))) `thenP` (\e3 ->
	nextFinalizeInstance (getLeftHandSide e1) `thenP` (\e4 -> 
	returnP ((e1:e2s)++[e3,e4])))))

getLeftHandSide :: Event -> Instance
getLeftHandSide (AssignField _ lhs _) = lhs

getRightHandSide :: Event -> Instance
getRightHandSide (AssignField _ _ rhs) = rhs

assocs :: [Instance] -> Parser [Event]
assocs is = 
	      assoc is
	      `orP'`	
	      (\es -> 
	       assocs ((getInstance (last es)):is))

getInstance :: Event -> Instance
getInstance (Finalize _ i) = i

checkAssoc :: Trace -> Bool
checkAssoc t = 
	let ns = map (\(evts,_) -> map getNumber evts) (assocs [] t) in
	sort (foldr (++) [] ns) == [1..(length t)]
	-- each event as exactly one occurence 

getNumber :: Event -> Int
getNumber (AssignField n _ _) = n
getNumber (Finalize n _)      = n

sort []     = []
sort (x:xs) = 
	sort [y | y <- xs, y<x ] 
	++ [x] 
	++ sort [y | y <- xs, y>=x]
