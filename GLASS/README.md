# GLASS

Core of the GLASS (Galois Lattice Analyses for Software Systems) project.
GLASS aims to leverage Galois lattices to detect features in Java code via static analyses.
In short, we are able to use any 3rd party library (via the interfaces defined in `glass.ast`) to
build a small meta-model that is then used to build a reverse-inheritance relationship.
This relationship is then used to build a lattice, which is then processed to finally extract
the desired features. More details on the methodology can be found in the paper cited at the end.

Following the detection of features, we aim to refactor 'ad-hoc' features, and features that are 'partial'
(as described by H. Mili et al.). To this end, we also aim to use Galois lattices to build an 'improved'
class hierarchy.


## Acknowledgement

This project is built upon the works of H. Mili et al. in `H. Mili, I. Benzarti, A. Elkharraz, G. Elboussaidi, Y. -G. Guéhéneuc and P. Valtchev,
"Discovering Reusable Functional Features in Legacy Object-Oriented Systems," in IEEE Transactions on Software Engineering,
vol. 49, no. 7, pp. 3827-3856, July 2023.` and reuses much of their code.