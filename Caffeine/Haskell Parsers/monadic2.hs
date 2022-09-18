
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

isFinalize :: Event -> Bool
isFinalize (Finalize _ _) = True
isFinalize _              = False

isFinalize' :: Instance -> Event -> Bool
isFinalize' i (Finalize _ i') = i==i'
isFinalize' _ _               = False

getInstance :: Event -> Instance
getInstance (Finalize _ i) = i


-- compositions analysis

skip :: Parser ()
skip = do {_ <- item; return ()}

next :: (Event -> Bool) -> Parser Event
next f = sat f +++ do {() <- skip; next f}

nextAssignField :: Parser Event
nextAssignField = next isAssignField

nextAssignField' :: Instance -> Parser Event
nextAssignField' i = next (isAssignField' i)

nextFinalize :: Parser Event
nextFinalize = next isFinalize

nextFinalize' :: Instance -> Parser Event
nextFinalize' i = next (isFinalize' i)

compose :: Parser [Event]
compose = 
	do {e1  <- nextAssignField; 
	    e2s <- many (nextAssignField' (getLHS e1));
	    e3  <- nextFinalize' (getRHS (last (e1:e2s)));
	    e4  <- nextFinalize' (getLHS e1);
	    return (e1:e2s++[e3,e4])}

isNewAssignField :: [Instance] -> Event -> Bool
isNewAssignField is (AssignField _ i _) = notElem i is
isNewAssignField is _                   = False

nextNewAssignField :: [Instance] -> Parser Event
nextNewAssignField is = next (isNewAssignField is)

composes :: [Instance] -> Parser [Event]
composes is = 
	do {e1  <- nextNewAssignField is; 
	    do {e2s <- many (nextAssignField' (getLHS e1));
	        e3  <- nextFinalize' (getRHS (last (e1:e2s)));
	        e4  <- nextFinalize' (getLHS e1);
	        return (e1:e2s++[e3,e4])
               }
	    `mplus`
            composes ((getLHS e1):is)
           } 

composes' :: Trace -> [([Event],Trace)]
composes' t = papply (composes []) t

checkComposes :: Trace -> Bool
checkComposes t = 
	let ns = map (\(evts,_) -> map getNumber evts) (composes' t) in
	sort (foldr (++) [] ns) == [1..(length t)]
	-- each event as exactly one occurence 

sort []     = []
sort (x:xs) = sort [y | y <- xs, y<x] ++ [x] ++ sort [y | y <- xs, y>=x]

strongCompose :: Parser [Event]
strongCompose = 
	do {e1  <- nextAssignField; 
	    e2s <- many (nextAssignField' (getLHS e1));
	    e3s <- nextFinalizes' (map getRHS (e1:e2s));
	    e4  <- nextFinalize' (getLHS e1);
	    return (e1:e2s++e3s++[e4])}

nextFinalizes' :: [Instance] -> Parser [Event]
nextFinalizes' [] = return []
nextFinalizes' is = 
	do {e <- nextFinalize;
	    if (elem (getInstance e) is) 
		then do {es <- nextFinalizes' (filter ((getInstance e) /=) is);
			 return (e:es)}  
		else nextFinalizes' is}

strongComposes :: [Instance] -> Parser [Event]
strongComposes is = 
	do {e1 <- nextNewAssignField is; 
	    do {e2s <- many (nextAssignField' (getLHS e1));
	        e3s <- nextFinalizes' (map getRHS (e1:e2s));
	        e4  <- nextFinalize' (getLHS e1);
	        return (e1:e2s++e3s++[e4])}
	    `mplus`
            strongComposes ((getLHS e1):is)} 

-- ecrire les checks et generer les contre exemples !!!

unmatchedEvents :: [([Event],Trace)] -> Trace -> Trace
unmatchedEvents res t = 
	filter (\e -> notElem e (concat (map fst res))) t

test1 :: Trace -> Trace
test1 t = unmatchedEvents (composes' t) t

-- intersection vide vs non vide



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

trace11 :: Trace 
trace11 = [
	AssignField 1 ("A",1) ("B",2), 
	AssignField 2 ("A",1) ("B",3), 
	AssignField 3 ("A",1) ("B",4), 
	AssignField 4 ("A",1) ("B",2), 
	Finalize    5 ("B",2), 
	Finalize    5 ("B",4), 
	Finalize    5 ("B",3), 
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

----------------------------------------------------------------------

-- Example 12 (Simple composition)
traceExample12 :: Trace 
traceExample12 = [
	AssignField 1  ("A",1) ("B",2), 
	Finalize    2  ("B",2), 
	Finalize    3  ("A",1)
	]

analyze12 :: [Event]
analyze12 = unmatchedEvents (papply compose traceExample12) traceExample12

-- Example 13 (Simple interlaced composition)
traceExample13 :: Trace 
traceExample13 = [
	AssignField 1  ("A",1) ("B",2), 
	Finalize    2  ("B",2), 
	AssignField 3  ("A",3) ("B",4), 
	Finalize    4  ("B",4), 
	Finalize    5  ("A",1),
	Finalize    6  ("A",3)
	]

analyze13 :: [Event]
analyze13 = unmatchedEvents (papply (composes []) traceExample13) traceExample13

-- Example 14 (Simple counter-example of composition)
traceExample14 :: Trace 
traceExample14 = [
	AssignField 1  ("A",1) ("B",2), 
	Finalize    2  ("A",1),
	Finalize    3  ("B",2)
	]

analyze14 :: [Event]
analyze14 = unmatchedEvents (papply compose traceExample14) traceExample14

-- Example 15 (Simple counter-example of interlaced composition)
traceExample15 :: Trace 
traceExample15 = [
	AssignField 1  ("A",1) ("B",2), 
	Finalize    2  ("B",2), 
	AssignField 3  ("A",3) ("B",4), 
	Finalize    4  ("A",3),
	Finalize    5  ("A",1),
	Finalize    6  ("B",4)
	]

analyze15 :: [Event]
analyze15 = unmatchedEvents (papply (composes []) traceExample15) traceExample15

{-

pour le papier.

- modelisation d'une trace d'execution : (ou presenter directement un
exemple de trace : trace2, trace3)

type Class = String

type Reference = Int

type Instance = (Class,Reference)

data Event =
	     AssignField Int Instance Instance
	   | Finalize    Int Instance
	   deriving (Eq, Show)

type Trace = [Event]

avec accessors and predicates ...

- parler des pb potentiels de generation des events finalize (faire du
gc a chaque instant). Pour le moment il faut plus voir notre approche
comme une definition formelle. pour qu'elle soit operationnelle ... ?
si on ne s'interesse pas a la duree de vie, on peut faire des requetes
sur les protocoles.

- utilisation des : monadic parser combinators library de Hutton et
Meijer, pour analyser une trace on changer legerement la definition
des types :

Parser a = P (Trace -> [(a,Trace)])

operations de base :

item : le prochain evt

sequencing, la notation : 
do {result1 <- parser1; result2 <- parser2; ... ; return (f result1 result2 ...)}

le or (parsers en parallele) : parser1 `mplus` parser2

et ajout d'un combinateur qui rend le premier evt de la trace qui
verifie un predicat

next :: (Event -> Bool) -> Parser Event
 

- analyses :

compose :: Parser [Event]
compose = 
	do {e1  <- nextAssignField; 
	    e2s <- many (nextAssignField' (getLHS e1));
	    e3  <- nextFinalize' (getRHS (last (e1:e2s)));
	    e4  <- nextFinalize' (getLHS e1);
	    return (e1:e2s++[e3,e4])}

composes :: [Instance] -> Parser [Event]
composes is = 
	do {e1  <- nextNewAssignField is; 
	    do {e2s <- many (nextAssignField' (getLHS e1));
	        e3  <- nextFinalize' (getRHS (last (e1:e2s)));
	        e4  <- nextFinalize' (getLHS e1);
	        return (e1:e2s++[e3,e4])
               }
	    `mplus`
            composes ((getLHS e1):is)
           } 

- donner un/des example(s) de trace(s) et du resultat analyse. par
exemple, la sous trace composees des evennements qui n'ont pas permis
de detecter des occurences du pattern. autre possibilite, le partage
ou non des evts (partition de la trace)

-}

