S->E
E->E+T
E->T
T->T*F
T->F
F->(E)
F->i



P->D
P->S
S->S S

### declare variables

D->D D|proc id;D S|T id;
T->X C|record D
X->integer|real
C->[num]C|ε

### assignment

S->id=E;|L=E;
E->E+E|E*E|-E|(E)|id|digit|L
L->id[E]|L[E]

### control

S->if B then S1|if B then S1 else S2|while B do S1
B->B or B | B and B|not B|(B)|E relop E|true|false
relop-><|<=|==|!=|>|>=

### process

S->call id(Elist)
Elist->Elist,E
Elist->E
