
-- trace analysis for classes relationship
-- remi douence, 03/07/2002

-- java events

type Class = String

type Reference = Int

type Instance = (Class,Reference)

data Event =
	  AssignField Int Instance Instance
	| Finalize    Int Instance
	deriving (Eq, Show)

type Trace = [Event]


-- event accessors and predicates

getNumber :: Event -> Int
getNumber (AssignField n _ _) = n
getNumber (Finalize n _)      = n

isAssignField :: Event -> Bool
isAssignField (AssignField _ _ _) = True
isAssignField _                   = False

isAssignField' :: Instance -> Event -> Bool
isAssignField' i (AssignField _ i' _) = i==i'
isAssignField' _ _                    = False

getLHS :: Event -> Instance
getLHS (AssignField _ lhs _) = lhs

getRHS :: Event -> Instance
getRHS (AssignField _ _ rhs) = rhs

isFinalize' :: Instance -> Event -> Bool
isFinalize' i (Finalize _ i') = i==i'
isFinalize' _ _               = False

getInstance :: Event -> Instance
getInstance (Finalize _ i) = i


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

itemP :: Parser Event
itemP = \t -> case t of
              []         -> []
              (evt:evts) -> [(evt,evts)]

firstP :: Parser a -> Parser a
firstP p = \t -> case (p t) of
                 []    -> []
                 (x:_) -> [x]

satP :: (a -> Bool) -> Parser a -> Parser a
satP f p = p `thenP` (\x -> if (f x) then returnP x else failP)

many1P :: Parser a -> Parser [a]
many1P p = p                `thenP` \x  -> 
           manyP p          `thenP` \xs ->
           returnP (x:xs) 

manyP :: Parser a -> Parser [a]
manyP p = firstP (many1P p `orP` returnP [])


-- composition analysis

skipP :: Parser ()
skipP = itemP `thenP` \_ -> returnP ()

nextP :: (Event -> Bool) -> Parser Event
nextP f = 

	firstP
	(satP f itemP
	`orP`	  
	(skipP `thenP` \() -> nextP f)) 

nextAssignFieldP :: Parser Event
nextAssignFieldP = nextP isAssignField

nextAssignFieldP' :: Instance -> Parser Event
nextAssignFieldP' i = nextP (isAssignField' i)

nextFinalize' :: Instance -> Parser Event
nextFinalize' i = nextP (isFinalize' i)

composeP :: Parser [Event]
composeP = 
	nextAssignFieldP 							`thenP` \e1 ->
	manyP (nextAssignFieldP' (getLHS e1))		`thenP` \e2s -> 
	nextFinalize' (getRHS (last (e1:e2s)))		`thenP` \e3 ->
	nextFinalize' (getLHS e1)					`thenP` \e4 ->
	returnP (e1:e2s++[e3,e4])

isNewAssignField :: [Instance] -> Event -> Bool
isNewAssignField is (AssignField _ i _) = notElem i is
isNewAssignField is _                   = False

nextNewAssignFieldP :: [Instance] -> Parser Event
nextNewAssignFieldP is = nextP (isNewAssignField is)

composesP :: [Instance] -> Parser [Event]
composesP is = 
	nextNewAssignFieldP is 						`thenP` \e1 ->
	(manyP (nextAssignFieldP' (getLHS e1))		`thenP` \e2s -> 
	nextFinalize' (getRHS (last (e1:e2s)))		`thenP` \e3 ->
	nextFinalize' (getLHS e1)					`thenP` \e4 ->
	returnP (e1:e2s++[e3,e4])) `orP` composesP ((getLHS e1):is)
	
checkComposes :: Trace -> Bool
checkComposes t = 
	let ns = map (\(evts,_) -> map getNumber evts) (composesP [] t) in
	sort (foldr (++) [] ns) == [1..(length t)]
	-- each event as exactly one occurence 

sort []     = []
sort (x:xs) = 
	sort [y | y <- xs, y<x ] 
	++ [x] 
	++ sort [y | y <- xs, y>=x]


-- traces examples

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

