
module ParseLib
   (Parser, item, papply, (+++), sat, many, many1, module Monad) where

import Monad

infixr 5 +++

--- The parser monad ---------------------------------------------------------

newtype Parser a   = P (Trace -> [(a,Trace)])

instance Functor Parser where
   -- map         :: (a -> b) -> (Parser a -> Parser b)
   fmap f (P p)    = P (\inp -> [(f v, out) | (v,out) <- p inp])

instance Monad Parser where
   -- return      :: a -> Parser a
   return v        = P (\inp -> [(v,inp)])

   -- >>=         :: Parser a -> (a -> Parser b) -> Parser b
   (P p) >>= f     = P (\inp -> concat [papply (f v) out | (v,out) <- p inp])

instance MonadPlus Parser where
   -- mzero            :: Parser a
   mzero                = P (\inp -> [])

   -- mplus            :: Parser a -> Parser a -> Parser a
   (P p) `mplus` (P q)  = P (\inp -> (p inp ++ q inp))

--- Other primitive parser combinators ---------------------------------------

item              :: Parser Event
item               = P (\inp -> case inp of
                                   []     -> []
                                   (x:xs) -> [(x,xs)])

force             :: Parser a -> Parser a
force (P p)        = P (\inp -> let x = p inp in
                                (fst (head x), snd (head x)) : tail x)

first             :: Parser a -> Parser a
first (P p)        = P (\inp -> case p inp of
                                   []     -> []
                                   (x:xs) -> [x])

papply            :: Parser a -> Trace -> [(a,Trace)]
papply (P p) inp   = p inp

--- Derived combinators ------------------------------------------------------

(+++)             :: Parser a -> Parser a -> Parser a
p +++ q            = first (p `mplus` q)

sat               :: (Event -> Bool) -> Parser Event
sat p              = do {x <- item; if p x then return x else mzero}

many              :: Parser a -> Parser [a]
many p             = force (many1 p +++ return [])

many1             :: Parser a -> Parser [a]
many1 p            = do {x <- p; xs <- many p; return (x:xs)}

-- java events

type Class = String

type Reference = Int

type Instance = (Class,Reference)

data Event =
	  AssignField Int Instance Instance
	| Finalize    Int Instance
	deriving (Eq, Show)

type Trace = [Event]

type Subtrace = [Event]

type Relation = [Event]

-- event accessors and predicates

getNumber :: Event -> Int
getNumber (AssignField n _ _) = n
getNumber (Finalize n _)      = n

isAssignField :: Event -> Bool
isAssignField (AssignField _ _ _) = True
isAssignField _                   = False

getWhole :: Event -> Instance
getWhole (AssignField _ whole _) = whole

getPart :: Event -> Instance
getPart (AssignField _ _ part) = part

isFinalize' :: Instance -> Event -> Bool
isFinalize' i (Finalize _ i') = i==i'
isFinalize' _ _               = False

-- compositions analysis

parse :: Parser Relation -> Trace -> [Relation]
parse p t = map fst (papply p t)

skip :: Parser ()
skip = do {_ <- item; return ()}

next :: (Event -> Bool) -> Parser Event
next f = sat f +++ do {() <- skip; next f}

nextAssignField :: Parser Event
nextAssignField = next isAssignField

nextFinalize' :: Instance -> Parser Event
nextFinalize' i = next (isFinalize' i)

	checkLt :: Parser [Event]
	checkLt = 
		do {e1  <- nextAssignField; 
		    e2  <- nextFinalize' (getPart e1);
		    e3  <- nextFinalize' (getWhole e1);
		    return [e1,e2,e3]}

matchedEvents :: [[Event]] -> Set Event
matchedEvents res = foldr unionSet emptySet res

unmatchedEvents :: [[Event]] -> Trace -> Trace
unmatchedEvents res t = filter (\e -> notElem e (matchedEvents res)) t

trace12 :: Trace 
trace12 = [
	AssignField 1 ("A",1) ("B",2), 
	Finalize    2 ("B",2), 
	Finalize    3 ("A",1)
	]

	checkLt12 :: Subtrace
	checkLt12 = unmatchedEvents (parse checkLt trace12) trace12

trace14 :: Trace 
trace14 = [
	AssignField 1 ("A",1) ("B",2), 
	Finalize    2 ("A",1), 
	Finalize    3 ("B",2)
	]

	checkLt14 :: Subtrace
	checkLt14 = unmatchedEvents (parse checkLt trace14) trace14

	checkLts :: Parser [Trace]
	checkLts = 
		do {e1  <- nextAssignField; 
		    do {e2  <- nextFinalize' (getPart e1);
		        e3  <- nextFinalize' (getWhole e1);
		        return [e1,e2,e3]
	               }
		    `mplus`
	            checkLts
	           } 

trace13 :: Trace 
trace13 = [
	AssignField 1 ("A",1) ("B",2), 
	Finalize    2 ("B",2), 
	AssignField 3 ("A",3) ("B",4), 
	Finalize    4 ("B",4),
	Finalize    5 ("A",1),
	Finalize    6 ("A",3)
	]

	checkLts13 :: Subtrace
	checkLts13 = unmatchedEvents (parse checkLts trace13) trace13

trace15 :: Trace 
trace15 = [
	AssignField 1 ("A",1) ("B",2), 
	Finalize    2 ("B",2), 
	AssignField 3 ("A",3) ("B",4), 
	Finalize    4 ("A",3),
	Finalize    5 ("A",1),
	Finalize    6 ("B",4)
	]

	checkLts15 :: Subtrace
	checkLts15 = unmatchedEvents (parse checkLts trace15) trace15

	sharedEvents :: [Relation] -> Set Event
	sharedEvents res = foldr unionSet 
	                         emptySet 
	                         [interSet r1 r2 | r1 <- res, r2 <- res, r1 /= r2]
	
	checkExs :: [Relation] -> [Relation]
	checkExs res = filter (\es -> null (interSet es (sharedEvents res))) res
	
	checkCompose :: Trace -> [Relation]
	checkCompose t = checkExs (parse checkLts t)
	
	trace16 :: Trace 
	trace16 = [
		AssignField 1 ("A",1) ("B",2), 
		AssignField 2 ("A",3) ("B",2), 
		Finalize    3 ("B",2),
		Finalize    4 ("A",1),
		Finalize    5 ("A",3)
		]
	
	checkCompose16 :: [Relation]
	checkCompose16 = checkCompose trace16

-- set

type Set a = [a]

emptySet :: Set a
emptySet = []

isEmptySet :: Set a -> Bool
isEmptySet = null

addSet :: Eq a => Set a -> a -> Set a
addSet s e | elem e s  = s
           | otherwise = e:s

anyElemSet :: Set a -> a
anyElemSet = head

otherElemsSet :: Set a -> Set a
otherElemsSet = tail

unionSet :: Eq a => Set  a -> Set a -> Set a 
unionSet s1 s2 
	| isEmptySet s1 = s2
	| otherwise     = addSet (unionSet (otherElemsSet s1) s2) 
			  (anyElemSet s1) 

interSet :: Eq a => Set a -> Set a -> Set a
interSet s1 s2 
	| isEmptySet s1           = emptySet
	| elem (anyElemSet s1) s2 = addSet (interSet (otherElemsSet s1) s2) 
                                    (anyElemSet s1) 
	| otherwise               = interSet (otherElemsSet s1) s2
